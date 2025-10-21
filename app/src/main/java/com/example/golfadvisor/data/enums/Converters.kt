package com.example.golfadvisor.data.enums

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun toScoringBadge(value: String): ScoringBadge = ScoringBadge.valueOf(value)

    @TypeConverter
    fun fromScoringBadge(badge: ScoringBadge): String = badge.name

    @TypeConverter
    fun toReviewsBadge(value: String): ReviewsBadge = ReviewsBadge.valueOf(value)

    @TypeConverter
    fun fromReviewsBadge(badge: ReviewsBadge): String = badge.name

    @TypeConverter
    fun toFavoritesBadge(value: String): FavoritesBadge = FavoritesBadge.valueOf(value)

    @TypeConverter
    fun fromFavoritesBadge(badge: FavoritesBadge): String = badge.name

    @TypeConverter
    fun toAccessType(value: String): AccessType = AccessType.valueOf(value)

    @TypeConverter
    fun fromAccessType(type: AccessType): String = type.name
}