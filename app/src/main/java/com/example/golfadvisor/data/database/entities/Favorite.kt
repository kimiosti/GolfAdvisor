package com.example.golfadvisor.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "favorites",
    primaryKeys = ["user", "club"]
)
class Favorite (
    val user: String,
    val club: String
)