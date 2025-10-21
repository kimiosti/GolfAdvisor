package com.example.golfadvisor.data.repository

import com.example.golfadvisor.data.daos.CourseDao
import com.example.golfadvisor.data.daos.ReviewDao

class SearchRepository(private val golfClubDao: CourseDao, private val reviewDao: ReviewDao) {
    suspend fun searchForCourses(searchString: String): List<GolfClubWithRating> {
        val clubs = golfClubDao.searchClubs(searchString)
        val data = ArrayList<GolfClubWithRating>()

        clubs.forEach { club ->
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
        }

        return data
    }
}