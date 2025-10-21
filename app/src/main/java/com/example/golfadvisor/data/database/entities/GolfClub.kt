package com.example.golfadvisor.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Entity(
    tableName = "golf_clubs"
)
data class GolfClub(
    @PrimaryKey @ColumnInfo(name = "club_name") val clubName: String,
    val address: String,
    val phone: String,
    val email: String,
    val latitude: Float?,
    val longitude: Float?
)