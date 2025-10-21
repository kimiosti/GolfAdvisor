package com.example.golfadvisor.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.golfadvisor.data.daos.UserDao
import kotlinx.coroutines.flow.map

class LoginRepository(
    private val dataStore: DataStore<Preferences>,
    private val userDao: UserDao
) {
    companion object {
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val IS_LOGGED_KEY = stringPreferencesKey("is_logged")
    }

    val username = dataStore.data.map { preferences ->
        preferences[USERNAME_KEY] ?: ""
    }
    val isLogged = dataStore.data.map { preferences ->
        when {
            preferences[IS_LOGGED_KEY] == "true" -> true
            else -> false
        }
    }

    suspend fun login(username: String) {
        dataStore.edit { it[USERNAME_KEY] = username }
        dataStore.edit { it[IS_LOGGED_KEY] = "true" }
    }

    suspend fun checkLogin(username: String, password: String): Boolean {
        val users = userDao.checkLogin(username, password)
        return users.size == 1
    }

    suspend fun logout() {
        dataStore.edit { it[USERNAME_KEY] = "" }
        dataStore.edit { it[IS_LOGGED_KEY] = "false" }
    }
}