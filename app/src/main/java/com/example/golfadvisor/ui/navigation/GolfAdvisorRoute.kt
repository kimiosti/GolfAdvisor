package com.example.golfadvisor.ui.navigation

import kotlinx.serialization.Serializable

sealed interface GolfAdvisorRoute {

    @Serializable
    data object LoginScreen: GolfAdvisorRoute

    @Serializable
    data object RegisterScreen: GolfAdvisorRoute

    @Serializable
    data object HomeScreen: GolfAdvisorRoute

    @Serializable
    data class CourseDetailScreen(val courseName: String): GolfAdvisorRoute

    @Serializable
    data class AddReviewScreen(val courseName: String): GolfAdvisorRoute

    @Serializable
    data class CourseReviewsScreen(val courseName: String): GolfAdvisorRoute

    @Serializable
    data class SingleReviewScreen(val reviewID: Int): GolfAdvisorRoute

    @Serializable
    data class VisitProfileScreen(val username: String): GolfAdvisorRoute

    @Serializable
    data object YourProfileScreen: GolfAdvisorRoute

    @Serializable
    data object SettingsScreen: GolfAdvisorRoute
}