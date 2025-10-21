package com.example.golfadvisor.data.repository

import com.example.golfadvisor.data.daos.ReviewDao
import com.example.golfadvisor.data.daos.UserDao
import com.example.golfadvisor.data.database.entities.Review
import com.example.golfadvisor.data.database.entities.User

class ScoringTrendRepository(private val reviewDao: ReviewDao, private val userDao: UserDao) {
    suspend fun getLatestReviewsWithScore(username: String, num: Int): List<Review> {
        return reviewDao.getLatestReviewsWithScore(username, num)
    }

    suspend fun getUserInfo(username: String): User? {
        val users = userDao.findByUsername(username)
        return if (users.isEmpty()) null else users[0]
    }
}