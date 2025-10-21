package com.example.golfadvisor.data.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.golfadvisor.data.database.entities.GolfClub

@Dao
interface CourseDao {
    @Query("SELECT * FROM golf_clubs")
    suspend fun getAll(): List<GolfClub>

    @Query("SELECT * FROM golf_clubs WHERE club_name LIKE '%' || :searchString || '%'")
    suspend fun searchClubs(searchString: String): List<GolfClub>

    @Query("SELECT * FROM golf_clubs WHERE club_name = :clubName")
    suspend fun getClubByName(clubName: String): List<GolfClub>
}