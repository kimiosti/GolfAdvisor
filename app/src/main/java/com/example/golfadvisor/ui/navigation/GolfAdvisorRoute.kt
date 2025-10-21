package com.example.golfadvisor.ui.navigation

import kotlinx.serialization.Serializable

sealed interface GolfAdvisorRoute {
    @Serializable data object LoginScreen: GolfAdvisorRoute

    @Serializable data object RegistrationScreen: GolfAdvisorRoute

    @Serializable data object HomeScreen: GolfAdvisorRoute

    @Serializable data object SearchClubScreen: GolfAdvisorRoute

    @Serializable data class ClubDetailScreen(val clubName: String): GolfAdvisorRoute

    @Serializable data class ClubReviewsScreen(val clubName: String): GolfAdvisorRoute

    @Serializable data class ReviewDetailScreen(val reviewID: Int): GolfAdvisorRoute

    @Serializable data class UserProfileScreen(val username: String): GolfAdvisorRoute

    @Serializable data class UserFavoritesScreen(val username: String): GolfAdvisorRoute

    @Serializable data class UserScoringTrendScreen(val username: String): GolfAdvisorRoute

    @Serializable data class UserReviewsScreen(val username: String): GolfAdvisorRoute

    @Serializable data class AddReviewScreen(val clubName: String): GolfAdvisorRoute

    @Serializable data object SettingsScreen: GolfAdvisorRoute

    @Serializable data object ChangeUserDataScreen: GolfAdvisorRoute

    @Serializable data object ChangePasswordScreen: GolfAdvisorRoute
}