package com.example.golfadvisor.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.golfadvisor.data.database.entities.User

@Dao
interface UserDao {
    @Insert
    suspend fun registerUser(user: User)

    @Upsert
    suspend fun updateUserData(user: User)

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun findByUsername(username: String): List<User>

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun checkLogin(username: String, password: String): List<User>
}