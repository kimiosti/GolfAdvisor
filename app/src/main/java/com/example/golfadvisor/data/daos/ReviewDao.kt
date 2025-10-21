package com.example.golfadvisor.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.golfadvisor.data.database.entities.Review

@Dao
interface ReviewDao {
    @Insert
    suspend fun addReview(review: Review)

    @Query("SELECT * FROM reviews WHERE review_id = :reviewId")
    suspend fun getReviewsById(reviewId: Int): List<Review>

    @Query("SELECT * FROM reviews WHERE username = :username")
    suspend fun getAllReviewsByUser(username: String): List<Review>

    @Query("SELECT * FROM reviews WHERE club_name = :clubName")
    suspend fun getAllReviewsForClub(clubName: String): List<Review>

    @Query("SELECT * FROM reviews WHERE username = :username AND score is not null")
    suspend fun getAllReviewsWithScore(username: String): List<Review>

    @Query("SELECT * FROM reviews WHERE username = :username AND score is not null ORDER BY review_id DESC LIMIT :num")
    suspend fun getLatestReviewsWithScore(username: String, num: Int): List<Review>
}