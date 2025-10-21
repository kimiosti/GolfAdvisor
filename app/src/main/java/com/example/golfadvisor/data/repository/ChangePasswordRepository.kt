package com.example.golfadvisor.data.repository

import com.example.golfadvisor.data.daos.UserDao
import com.example.golfadvisor.data.database.entities.User

class ChangePasswordRepository(private val userDao: UserDao) {
    suspend fun checkLogin(username: String, password: String): Boolean {
        val users = userDao.checkLogin(username, password)
        return users.isNotEmpty()
    }

    suspend fun getUserInfo(username: String): User? {
        val users = userDao.findByUsername(username)
        return if (users.isEmpty()) null else users[0]
    }

    suspend fun updateUserInfo(user: User) {
        userDao.updateUserData(user)
    }
}