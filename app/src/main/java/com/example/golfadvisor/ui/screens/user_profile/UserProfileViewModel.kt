package com.example.golfadvisor.ui.screens.user_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golfadvisor.data.database.entities.User
import com.example.golfadvisor.data.repository.UserProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UserProfileState(
    val user: User?
)

interface UserProfileActions {
    fun checkProfileInfo(username: String)
}

class UserProfileViewModel(private val userProfileRepository: UserProfileRepository): ViewModel() {
    private val _state = MutableStateFlow(UserProfileState(null))
    val state = _state.asStateFlow()

    val actions = object : UserProfileActions {
        override fun checkProfileInfo(username: String) {
            viewModelScope.launch {
                val user = userProfileRepository.getUserProfile(username)
                _state.value = UserProfileState(user)
            }
        }
    }
}