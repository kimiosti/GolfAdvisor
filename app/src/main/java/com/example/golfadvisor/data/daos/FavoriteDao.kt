package com.example.golfadvisor.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.golfadvisor.data.database.entities.Favorite
import com.example.golfadvisor.data.database.entities.GolfClub

@Dao
interface FavoriteDao {
    @Insert
    suspend fun addFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("""
        SELECT golf_clubs.*
        FROM golf_clubs, favorites
        WHERE favorites.user = :username
        AND favorites.club = golf_clubs.club_name
    """)
    suspend fun getUserFavorites(username: String): List<GolfClub>
}