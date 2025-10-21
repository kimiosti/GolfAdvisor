package com.example.golfadvisor.data.repository

import com.example.golfadvisor.data.daos.ReviewDao
import com.example.golfadvisor.data.daos.UserDao
import com.example.golfadvisor.data.database.entities.Review
import com.example.golfadvisor.data.database.entities.User

class UserReviewsRepository(private val reviewDao: ReviewDao, private val userDao: UserDao) {
    suspend fun getReviewsByUser(username: String): List<Review> {
        return reviewDao.getAllReviewsByUser(username)
    }

    suspend fun getUserInfo(username: String): User? {
        val data = userDao.findByUsername(username)
        return if (data.isEmpty()) null else data[0]
    }
}