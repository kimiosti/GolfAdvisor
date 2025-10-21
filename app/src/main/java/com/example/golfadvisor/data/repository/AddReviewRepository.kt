package com.example.golfadvisor.data.repository

import com.example.golfadvisor.data.daos.ReviewDao
import com.example.golfadvisor.data.daos.UserDao
import com.example.golfadvisor.data.database.entities.Review
import com.example.golfadvisor.data.database.entities.User
import com.example.golfadvisor.data.enums.getReviewsBadge
import com.example.golfadvisor.data.enums.getScoringBadge

class AddReviewRepository(private val reviewDao: ReviewDao, private val userDao: UserDao) {
    suspend fun addReview(review: Review) {
        reviewDao.addReview(review)
    }

    suspend fun computeScoringAverage(username: String): Float? {
        val reviews = reviewDao.getAllReviewsWithScore(username)
        var score = 0f
        reviews.forEach {
            score += (it.score!! * it.accessType.scoreMultiplier) //score is guaranteed to be non-null by the query
        }
        return if(reviews.isEmpty()) null else score / reviews.size
    }

    suspend fun updateUserState(username: String, scoringAverage: Float?) {
        val reviews = reviewDao.getAllReviewsByUser(username)
        val users = userDao.findByUsername(username)

        if (users.isNotEmpty()) {
            val user = users[0]
            userDao.updateUserData(User(
                username = username,
                password = user.password,
                name = user.name,
                surname = user.surname,
                scoringAverage = scoringAverage,
                scoringBadge = getScoringBadge(scoringAverage),
                reviewsBadge = getReviewsBadge(reviews.size),
                favoritesBadge = user.favoritesBadge
            ))
        }
    }
}