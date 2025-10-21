package com.example.golfadvisor.ui.screens.review_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golfadvisor.data.database.entities.Review
import com.example.golfadvisor.data.repository.ReviewDetailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ReviewDetailState(
    val review: Review?
)

interface ReviewDetailAction {
    fun checkReviews(reviewId: Int)
}

class ReviewDetailViewModel(private val reviewDetailRepository: ReviewDetailRepository): ViewModel() {
    private val _state = MutableStateFlow(ReviewDetailState(null))
    val state = _state.asStateFlow()

    val actions = object : ReviewDetailAction {
        override fun checkReviews(reviewId: Int) {
            viewModelScope.launch {
                val review = reviewDetailRepository.getReview(reviewId)
                _state.value = ReviewDetailState(review)
            }
        }
    }
}