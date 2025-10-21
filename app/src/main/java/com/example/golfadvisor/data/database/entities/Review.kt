package com.example.golfadvisor.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.golfadvisor.data.enums.AccessType

@Entity(
    tableName = "reviews"
)
data class Review(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "review_id") val reviewID: Int = 0,
    val username: String,
    @ColumnInfo(name = "club_name") val clubName: String,
    val title: String,
    val text: String,
    @ColumnInfo(name = "access_type") val accessType: AccessType,
    val price: Float? = null,
    val score: Float? = null,
    val rating: Int
)