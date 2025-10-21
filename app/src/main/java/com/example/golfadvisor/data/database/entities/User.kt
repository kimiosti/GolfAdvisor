package com.example.golfadvisor.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.golfadvisor.data.enums.FavoritesBadge
import com.example.golfadvisor.data.enums.ReviewsBadge
import com.example.golfadvisor.data.enums.ScoringBadge

@Entity(
    tableName = "users"
)
data class User(
    @PrimaryKey val username: String,
    val password: String,
    val name: String? = null,
    val surname: String? = null,
    @ColumnInfo(name = "scoring_average") val scoringAverage: Float? = null,
    @ColumnInfo(name = "scoring_badge") val scoringBadge: ScoringBadge,
    @ColumnInfo(name = "reviews_badge") val reviewsBadge: ReviewsBadge,
    @ColumnInfo(name = "favorites_badge") val favoritesBadge: FavoritesBadge
)