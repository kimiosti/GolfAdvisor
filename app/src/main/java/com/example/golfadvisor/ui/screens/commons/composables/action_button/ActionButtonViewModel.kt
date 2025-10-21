package com.example.golfadvisor.ui.screens.commons.composables.action_button

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golfadvisor.data.repository.UserFavoritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ActionButtonState(
    val isCourseFavorite: Boolean
)

interface ActionButtonActions {
    fun checkFavorite(username: String, clubName: String)
    fun toggleFavorite(username: String, clubName: String)
}

class ActionButtonViewModel(
    private val actionButtonRepository: UserFavoritesRepository
): ViewModel() {
    private val _state = MutableStateFlow(ActionButtonState(false))
    val state = _state.asStateFlow()

    val actions = object : ActionButtonActions {
        override fun checkFavorite(username: String, clubName: String) {
            viewModelScope.launch {
                val favorites = actionButtonRepository.getUserFavorites(username)
                var res = false
                favorites.forEach {
                    if (it.clubName == clubName) {
                        res = true
                    }
                }
                _state.value = ActionButtonState(res)
            }
        }

        override fun toggleFavorite(username: String, clubName: String) {
            viewModelScope.launch {
                actionButtonRepository.toggleFavorite(username, clubName)
                checkFavorite(username, clubName)
            }
        }
    }
}