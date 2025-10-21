package com.example.golfadvisor.data.repository

import com.example.golfadvisor.data.daos.UserDao
import com.example.golfadvisor.data.database.entities.User

class UserProfileRepository(private val userDao: UserDao) {
    suspend fun getUserProfile(username: String): User? {
        val users = userDao.findByUsername(username)
        return if (users.isEmpty()) null else users[0]
    }
}