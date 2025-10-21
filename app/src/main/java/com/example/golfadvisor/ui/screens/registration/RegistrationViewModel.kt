package com.example.golfadvisor.ui.screens.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golfadvisor.data.repository.RegistrationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegistrationState(
    var isUsernameValid: Boolean,
    var isRegistrationOk: Boolean? = null
)

interface RegistrationActions {
    fun checkUsername(username: String)
    fun registerUser(
        username: String,
        password: String,
        name: String,
        surname: String
    )
}

class RegistrationViewModel(val registrationRepository: RegistrationRepository): ViewModel() {
    private val _state = MutableStateFlow(
        RegistrationState(isUsernameValid = true)
    )
    val state = _state.asStateFlow()

    val actions = object: RegistrationActions {
        override fun checkUsername(username: String) {
            viewModelScope.launch {
                val isUsernameAvailable = registrationRepository.isUsernameAvailable(username)
                _state.value = RegistrationState(
                    isUsernameValid = isUsernameAvailable,
                    isRegistrationOk = _state.value.isRegistrationOk
                )
            }
        }

        override fun registerUser(
            username: String,
            password: String,
            name: String,
            surname: String
        ) {
            viewModelScope.launch {
                val isRegistrationValid = registrationRepository.registerUser(
                    username,
                    password,
                    name,
                    surname
                )
                _state.value = RegistrationState(
                    isUsernameValid = _state.value.isUsernameValid,
                    isRegistrationValid
                )
            }
        }
    }
}