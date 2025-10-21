package com.example.golfadvisor.data.repository

import com.example.golfadvisor.data.daos.FavoriteDao
import com.example.golfadvisor.data.daos.UserDao
import com.example.golfadvisor.data.database.entities.Favorite
import com.example.golfadvisor.data.database.entities.GolfClub
import com.example.golfadvisor.data.database.entities.User
import com.example.golfadvisor.data.enums.getFavoritesBadge

class UserFavoritesRepository(
    private val favoriteDao: FavoriteDao,
    private val userDao: UserDao
) {
    suspend fun getUserFavorites(username: String): List<GolfClub> {
        return favoriteDao.getUserFavorites(username)
    }

    suspend fun getUserInfo(username: String): User? {
        val user = userDao.findByUsername(username)
        return if (user.isEmpty()) null else user[0]
    }

    suspend fun toggleFavorite(username: String, clubName: String) {
        val favorites = getUserFavorites(username)
        var favoriteCount = favorites.size

        var isPresent = false
        favorites.forEach {
            if (it.clubName == clubName) {
                isPresent = true
            }
        }

        if (isPresent) {
            favoriteDao.deleteFavorite(Favorite(username, clubName))
            favoriteCount--
        } else {
            favoriteDao.addFavorite(Favorite(username, clubName))
            favoriteCount++
        }

        val user = userDao.findByUsername(username)[0]
        userDao.updateUserData(
            User(
                username = user.username,
                password = user.password,
                name = user.name,
                surname = user.surname,
                scoringAverage = user.scoringAverage,
                scoringBadge = user.scoringBadge,
                reviewsBadge = user.reviewsBadge,
                favoritesBadge = getFavoritesBadge(favoriteCount)
            )
        )
    }
}