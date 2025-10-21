package com.example.golfadvisor.data.enums

import com.example.golfadvisor.R

enum class ScoringBadge(val nameResourceId: Int, val descriptionResourceId: Int) {
    Novice(R.string.novice_badge_name, R.string.novice_badge_description),
    Amateur(R.string.amateur_badge_name, R.string.amateur_badge_description),
    Average(R.string.average_badge_name, R.string.average_badge_description),
    Top(R.string.top_badge_name, R.string.top_badge_description),
    Scratch(R.string.scratch_badge_name, R.string.scratch_badge_description),
    Tour(R.string.tour_badge_name, R.string.tour_badge_description)
}

fun getScoringBadge(scoringAverage: Float?): ScoringBadge {
    return when {
        scoringAverage == null -> ScoringBadge.Novice
        scoringAverage < 69 -> ScoringBadge.Tour
        scoringAverage < 75 -> ScoringBadge.Scratch
        scoringAverage < 80 -> ScoringBadge.Top
        scoringAverage < 90 -> ScoringBadge.Average
        scoringAverage < 100 -> ScoringBadge.Amateur
        else -> ScoringBadge.Novice
    }
}

enum class ReviewsBadge(val titleResourceId: Int, val descriptionResourceId: Int) {
    Silent(R.string.silent_badge_name, R.string.silent_badge_description),
    Reviewer(R.string.reviewer_badge_name, R.string.reviewer_badge_description),
    Analyst(R.string.analyst_badge_name, R.string.analyst_badge_description),
    Reporter(R.string.reporter_badge_name, R.string.reporter_badge_description)
}

fun getReviewsBadge(reviewsCount: Int): ReviewsBadge {
    return when {
        reviewsCount < 5 -> ReviewsBadge.Silent
        reviewsCount < 10 -> ReviewsBadge.Reviewer
        reviewsCount < 20 -> ReviewsBadge.Analyst
        else -> ReviewsBadge.Reporter
    }
}

enum class FavoritesBadge(val titleResourceId: Int, val descriptionResourceId: Int) {
    Local(R.string.local_badge_name, R.string.local_badge_description),
    Visitor(R.string.visitor_badge_name, R.string.visitor_badge_description),
    Traveler(R.string.traveler_badge_name, R.string.traveler_badge_description),
    Explorer(R.string.explorer_badge_name, R.string.explorer_badge_description)
}

fun getFavoritesBadge(favoritesCount: Int): FavoritesBadge {
    return when {
        favoritesCount < 5 -> FavoritesBadge.Local
        favoritesCount < 10 -> FavoritesBadge.Visitor
        favoritesCount < 20 -> FavoritesBadge.Traveler
        else -> FavoritesBadge.Explorer
    }
}