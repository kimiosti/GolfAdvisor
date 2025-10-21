package com.example.golfadvisor.ui.screens.commons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golfadvisor.data.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class LoginState(
    val username: String,
    val isLogged: Boolean,
    val loginFailed: Boolean
)

interface LoginActions {
    fun login(username: String)
    fun checkLogin(username: String, password: String)
    fun logout()
}

class LoginViewModel(val repository: LoginRepository): ViewModel() {
    private val _state = MutableStateFlow(LoginState("", isLogged = false, loginFailed = false))
    val state = _state.asStateFlow()

    val actions = object: LoginActions {
        override fun login(username: String) {
            _state.value = LoginState(username = username, isLogged = true, loginFailed = false)
            viewModelScope.launch { repository.login(username) }
        }

        override fun checkLogin(username: String, password: String) {
            viewModelScope.launch {
                val isLoginValid = repository.checkLogin(username, password)
                if (isLoginValid) {
                    login(username)
                } else {
                    _state.value = LoginState(
                        username = _state.value.username,
                        isLogged = false,
                        loginFailed = true
                    )
                }
            }
        }

        override fun logout() {
            _state.value = LoginState(
                username = "",
                isLogged = false,
                loginFailed = false
            )
            viewModelScope.launch { repository.logout() }
        }
    }

    init {
        viewModelScope.launch {
            _state.value = LoginState(
                username = repository.username.first(),
                isLogged = repository.isLogged.first(),
                loginFailed = false
            )
        }
    }
}