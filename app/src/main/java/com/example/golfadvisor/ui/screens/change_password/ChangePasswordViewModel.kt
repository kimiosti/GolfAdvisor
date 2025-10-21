package com.example.golfadvisor.ui.screens.change_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golfadvisor.data.database.entities.User
import com.example.golfadvisor.data.repository.ChangePasswordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ChangePasswordState(
    val loginFailed: Boolean,
    val passwordChanged: Boolean
)

interface ChangePasswordAction {
    fun updateUserInfo(username: String, oldPassword: String, newPassword: String)
}

class ChangePasswordViewModel(private val changePasswordRepository: ChangePasswordRepository): ViewModel() {
    private val _state = MutableStateFlow(ChangePasswordState(loginFailed = false, passwordChanged = false))
    val state = _state.asStateFlow()

    val actions = object : ChangePasswordAction {
        override fun updateUserInfo(username: String, oldPassword: String, newPassword: String) {
            viewModelScope.launch {
                val loginResult = changePasswordRepository.checkLogin(username, oldPassword)
                if (loginResult) {
                    val user = changePasswordRepository.getUserInfo(username)!!
                    changePasswordRepository.updateUserInfo(User(
                        username = user.username,
                        password = newPassword,
                        name = user.name,
                        surname = user.surname,
                        scoringAverage = user.scoringAverage,
                        scoringBadge = user.scoringBadge,
                        reviewsBadge = user.reviewsBadge,
                        favoritesBadge = user.favoritesBadge
                    )) // user is certainly non-null because of the previous login check
                    _state.value = ChangePasswordState(loginFailed = false, passwordChanged = true)
                } else {
                    _state.value = ChangePasswordState(loginFailed = true, passwordChanged = false)
                }
            }
        }
    }
}