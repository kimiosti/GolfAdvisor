package com.example.golfadvisor.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.golfadvisor.data.daos.CourseDao
import com.example.golfadvisor.data.daos.FavoriteDao
import com.example.golfadvisor.data.daos.ReviewDao
import com.example.golfadvisor.data.daos.UserDao
import com.example.golfadvisor.data.database.entities.Favorite
import com.example.golfadvisor.data.database.entities.GolfClub
import com.example.golfadvisor.data.database.entities.Review
import com.example.golfadvisor.data.database.entities.User
import com.example.golfadvisor.data.enums.Converters

@Database(
    entities = [
        GolfClub::class,
        User::class,
        Review::class,
        Favorite::class
    ],
    version = 6
)
@TypeConverters(Converters::class)
abstract class GolfAdvisorDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun golfClubDao(): CourseDao
    abstract fun reviewDao(): ReviewDao
    abstract fun favoriteDao(): FavoriteDao
}