package com.example.golfadvisor.ui.screens.user_favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golfadvisor.data.database.entities.GolfClub
import com.example.golfadvisor.data.database.entities.User
import com.example.golfadvisor.data.repository.UserFavoritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UserFavoritesState(
    val userInfo: User?,
    val favorites: List<GolfClub>
)

interface UserFavoritesAction {
    fun checkFavorites(username: String)
    fun toggleFavorite(username: String, clubName: String)
}

class UserFavoritesViewModel(
    private val userFavoritesRepository: UserFavoritesRepository
): ViewModel() {
    private val _state = MutableStateFlow(UserFavoritesState(null, listOf()))
    val state = _state.asStateFlow()

    val actions = object : UserFavoritesAction {
        override fun checkFavorites(username: String) {
            viewModelScope.launch {
                val user = userFavoritesRepository.getUserInfo(username)
                val favorites = userFavoritesRepository.getUserFavorites(username)
                _state.value = UserFavoritesState(user, favorites)
            }
        }

        override fun toggleFavorite(username: String, clubName: String) {
            viewModelScope.launch {
                userFavoritesRepository.toggleFavorite(username, clubName)
                checkFavorites(username)
            }
        }
    }
}