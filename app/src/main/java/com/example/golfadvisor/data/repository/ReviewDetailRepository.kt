package com.example.golfadvisor.data.repository

import com.example.golfadvisor.data.daos.ReviewDao
import com.example.golfadvisor.data.database.entities.Review

class ReviewDetailRepository(private val reviewDao: ReviewDao) {
    suspend fun getReview(reviewId: Int): Review? {
        val reviews = reviewDao.getReviewsById(reviewId)
        return if (reviews.isEmpty()) null else reviews[0]
    }
}