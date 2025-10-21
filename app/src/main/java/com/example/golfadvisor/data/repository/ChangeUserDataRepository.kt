package com.example.golfadvisor.data.repository

import com.example.golfadvisor.data.daos.UserDao
import com.example.golfadvisor.data.database.entities.User

class ChangeUserDataRepository(private val userDao: UserDao) {
    suspend fun loadUserData(username: String): User? {
        val users = userDao.findByUsername(username)
        return if (users.isEmpty()) null else users[0]
    }

    suspend fun updateUserData(user: User) {
        userDao.updateUserData(user)
    }
}