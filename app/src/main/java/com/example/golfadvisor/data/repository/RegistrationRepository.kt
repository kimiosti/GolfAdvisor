package com.example.golfadvisor.data.repository

import com.example.golfadvisor.data.daos.UserDao
import com.example.golfadvisor.data.database.entities.User
import com.example.golfadvisor.data.enums.getFavoritesBadge
import com.example.golfadvisor.data.enums.getReviewsBadge
import com.example.golfadvisor.data.enums.getScoringBadge

class RegistrationRepository(private val userDao: UserDao) {
    suspend fun isUsernameAvailable(username: String): Boolean {
        val users = userDao.findByUsername(username)
        return users.isEmpty()
    }

    suspend fun registerUser(
        username: String,
        password: String,
        name: String,
        surname: String
    ): Boolean {
        try {
            userDao.registerUser(User(
                username,
                password,
                if (name != "") name else null,
                if (surname != "") surname else null,
                scoringBadge = getScoringBadge(scoringAverage = null),
                reviewsBadge = getReviewsBadge(reviewsCount = 0),
                favoritesBadge = getFavoritesBadge(favoritesCount = 0)
            ))
            return true
        } catch (_: Exception) {
            return false
        }
    }
}