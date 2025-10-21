package com.example.golfadvisor.ui.screens.user_reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golfadvisor.data.database.entities.Review
import com.example.golfadvisor.data.database.entities.User
import com.example.golfadvisor.data.repository.UserReviewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UserReviewsState(
    val userInfo: User?,
    val reviews: List<Review>
)

interface UserReviewsAction {
    fun checkReviews(username: String)
}

class UserReviewsViewModel(private val userReviewsRepository: UserReviewsRepository): ViewModel() {
    private val _state = MutableStateFlow(UserReviewsState(null, listOf()))
    val state = _state.asStateFlow()

    val actions = object : UserReviewsAction {
        override fun checkReviews(username: String) {
            viewModelScope.launch {
                val reviews = userReviewsRepository.getReviewsByUser(username)
                val userInfo = userReviewsRepository.getUserInfo(username)
                _state.value = UserReviewsState(userInfo, reviews)
            }
        }
    }
}