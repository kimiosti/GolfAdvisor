package com.example.golfadvisor.data.repository

import com.example.golfadvisor.data.daos.CourseDao
import com.example.golfadvisor.data.daos.ReviewDao

class ClubDetailRepository(
    private val golfClubDao: CourseDao,
    private val reviewDao: ReviewDao
) {
    suspend fun getClubInformation(clubName: String): GolfClubWithRating? {
        val data = golfClubDao.getClubByName(clubName)
        if (data.isEmpty()) {
            return null
        } else {
            val club = data[0]
            val reviews = reviewDao.getAllReviewsForClub(clubName = club.clubName)
            var avg = 0.0f

            if (reviews.isEmpty()) {
                avg = -1.0f
            } else {
                reviews.forEach {
                    avg += it.rating
                }
                avg /= reviews.size
            }

            return GolfClubWithRating(club, avg)
        }
    }
}