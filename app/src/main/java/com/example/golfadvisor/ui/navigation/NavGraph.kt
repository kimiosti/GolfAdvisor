package com.example.golfadvisor.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.golfadvisor.ui.screens.login.LoginScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    logged: Boolean = false,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = if (logged) { GolfAdvisorRoute.HomeScreen } else { GolfAdvisorRoute.LoginScreen },
        modifier = modifier,
    ) {
        composable<GolfAdvisorRoute.LoginScreen> {
            LoginScreen(navController = navController)
        }

        composable<GolfAdvisorRoute.RegisterScreen> {

        }

        composable<GolfAdvisorRoute.HomeScreen> {

        }

        composable<GolfAdvisorRoute.CourseDetailScreen> {

        }

        composable<GolfAdvisorRoute.AddReviewScreen> {

        }

        composable<GolfAdvisorRoute.CourseReviewsScreen> {

        }

        composable<GolfAdvisorRoute.SingleReviewScreen> {

        }

        composable<GolfAdvisorRoute.VisitProfileScreen> {

        }

        composable<GolfAdvisorRoute.YourProfileScreen> {

        }

        composable<GolfAdvisorRoute.SettingsScreen> {

        }
    }
}