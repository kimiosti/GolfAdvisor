package com.example.golfadvisor.ui.screens.club_reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golfadvisor.data.database.entities.GolfClub
import com.example.golfadvisor.data.database.entities.Review
import com.example.golfadvisor.data.repository.ClubReviewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ClubReviewsState(
    val clubInfo: GolfClub?,
    val reviews: List<Review>?
)

interface ClubReviewsActions {
    fun checkReviews(clubName: String)
}

class ClubReviewsViewModel(private val clubReviewsRepository: ClubReviewsRepository): ViewModel() {
    private val _state = MutableStateFlow(ClubReviewsState(null, null))
    val state = _state.asStateFlow()

    val actions = object : ClubReviewsActions {
        override fun checkReviews(clubName: String) {
            viewModelScope.launch {
                val clubInfo = clubReviewsRepository.getClubInformation(clubName)
                val reviews = clubReviewsRepository.getReviewsForClub(clubName)

                _state.value = ClubReviewsState(clubInfo, reviews)
            }
        }
    }
}