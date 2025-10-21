package com.example.golfadvisor.data.repository

import com.example.golfadvisor.data.daos.CourseDao
import com.example.golfadvisor.data.daos.ReviewDao
import com.example.golfadvisor.data.database.entities.GolfClub
import com.example.golfadvisor.data.database.entities.Review

class ClubReviewsRepository(
    private val clubDao: CourseDao,
    private val reviewDao: ReviewDao
) {
    suspend fun getClubInformation(clubName: String): GolfClub =
        clubDao.getClubByName(clubName)[0]

    suspend fun getReviewsForClub(clubName: String): List<Review> =
        reviewDao.getAllReviewsForClub(clubName)
}