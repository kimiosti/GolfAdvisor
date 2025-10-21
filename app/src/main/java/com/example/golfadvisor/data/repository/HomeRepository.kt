package com.example.golfadvisor.data.repository

import com.example.golfadvisor.data.daos.CourseDao
import com.example.golfadvisor.data.daos.ReviewDao
import com.example.golfadvisor.data.database.entities.GolfClub

data class GolfClubWithRating(val golfClub: GolfClub, val averageRating: Float)

class HomeRepository(private val courseDao: CourseDao, private val reviewDao: ReviewDao) {
    suspend fun getAllClubsAndRatings(): List<GolfClubWithRating> {
        val clubs = courseDao.getAll()
        val data = ArrayList<GolfClubWithRating>()
        clubs.forEach({ club ->
            val ratings = reviewDao.getAllReviewsForClub(club.clubName)
            var avg = 0.0f
            if (ratings.isEmpty()) {
                avg = -1.0f
            } else {
                ratings.forEach({ rating ->
                    avg += rating.rating
                })
            }
            avg /= ratings.size
            data.add(GolfClubWithRating(club, avg))
        })

        return data
    }
}