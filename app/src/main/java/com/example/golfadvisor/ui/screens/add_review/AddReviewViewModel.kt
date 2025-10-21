package com.example.golfadvisor.ui.screens.add_review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golfadvisor.data.database.entities.Review
import com.example.golfadvisor.data.enums.AccessType
import com.example.golfadvisor.data.repository.AddReviewRepository
import kotlinx.coroutines.launch

interface AddReviewAction {
    fun addReview(
        username: String,
        clubName: String,
        title: String,
        text: String,
        rating: Int,
        accessType: AccessType,
        price: String,
        score: String
    )
}

class AddReviewViewModel(private val addReviewRepository: AddReviewRepository): ViewModel() {
    val actions = object : AddReviewAction {
        override fun addReview(
            username: String,
            clubName: String,
            title: String,
            text: String,
            rating: Int,
            accessType: AccessType,
            price: String,
            score: String
        ) {
            viewModelScope.launch {
                val review = Review(
                    username = username,
                    clubName = clubName,
                    title = title,
                    text = text,
                    rating = rating,
                    accessType = accessType,
                    price = price.toFloatOrNull(),
                    score = score.toFloatOrNull()
                )
                addReviewRepository.addReview(review)

                val scoringAverage = addReviewRepository.computeScoringAverage(username)
                addReviewRepository.updateUserState(username, scoringAverage)
            }
        }
    }
}