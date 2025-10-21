package com.example.golfadvisor.ui.screens.change_user_data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golfadvisor.data.database.entities.User
import com.example.golfadvisor.data.repository.ChangeUserDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ChangeUserDataState(
    val name: String,
    val surname: String
)

interface ChangeUserDataAction {
    fun loadUserData(username: String)
    fun updateUserData(username: String, name: String, surname: String)
}

class ChangeUserDataViewModel(private val changeUserDataRepository: ChangeUserDataRepository): ViewModel() {
    private val _state = MutableStateFlow(ChangeUserDataState(name = "", surname = ""))
    val state = _state.asStateFlow()

    val actions = object : ChangeUserDataAction {
        override fun loadUserData(username: String) {
            viewModelScope.launch {
                val user = changeUserDataRepository.loadUserData(username)
                _state.value = ChangeUserDataState(
                    name = user?.name ?: "",
                    surname = user?.surname ?: ""
                )
            }
        }

        override fun updateUserData(username: String, name: String, surname: String) {
            viewModelScope.launch {
                val user = changeUserDataRepository.loadUserData(username)

                if (user != null) {
                    changeUserDataRepository.updateUserData(User(
                        user.username,
                        user.password,
                        if (name == "") null else name,
                        if (surname == "") null else surname,
                        user.scoringAverage,
                        user.scoringBadge,
                        user.reviewsBadge,
                        user.favoritesBadge
                    ))
                }
            }
        }
    }
}