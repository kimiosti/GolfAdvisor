package com.example.golfadvisor.ui.screens.commons.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.example.golfadvisor.R
import com.example.golfadvisor.ui.navigation.GolfAdvisorRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController, isLogged: Boolean, username: String) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val title = when {
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.LoginScreen>() == true ->
            stringResource(R.string.login_screen_title)
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.RegistrationScreen>() == true ->
            stringResource(R.string.registration_screen_title)
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.SearchClubScreen>() == true ->
            stringResource(R.string.search_club_screen_title)
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.ClubDetailScreen>() == true ->
            stringResource(R.string.club_detail_screen_title)
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.ClubReviewsScreen>() == true ->
            stringResource(R.string.club_reviews_screen_title)
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.ReviewDetailScreen>() == true ->
            stringResource(R.string.review_detail_screen_title)
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.UserProfileScreen>() == true ->
            stringResource(R.string.user_profile_screen_title)
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.UserFavoritesScreen>() == true ->
            stringResource(R.string.user_favorites_screen_title)
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.UserScoringTrendScreen>() == true ->
            stringResource(R.string.user_scoring_trend_screen_title)
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.UserReviewsScreen>() == true ->
            stringResource(R.string.user_reviews_screen_title)
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.AddReviewScreen>() == true ->
            stringResource(R.string.add_review_screen_title)
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.SettingsScreen>() == true ->
            stringResource(R.string.settings_screen_title)
        else -> stringResource(R.string.home_screen_title)
    }

    CenterAlignedTopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton(
                    onClick = { navController.navigateUp() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(R.string.go_back_arrow_description)
                    )
                }
            }
        },
        actions = {
            if(
                (backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.UserProfileScreen>() != true
                        || backStackEntry?.toRoute<GolfAdvisorRoute.UserProfileScreen>()?.username != username)
                && backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.LoginScreen>() != true
                && backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.RegistrationScreen>() != true
                && (backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.UserReviewsScreen>() != true
                        || backStackEntry?.toRoute<GolfAdvisorRoute.UserReviewsScreen>()?.username != username)
                && (backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.UserFavoritesScreen>() != true
                        || backStackEntry?.toRoute<GolfAdvisorRoute.UserFavoritesScreen>()?.username != username)
                && (backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.UserScoringTrendScreen>() != true
                        || backStackEntry?.toRoute<GolfAdvisorRoute.UserScoringTrendScreen>()?.username != username)
            ) {
                IconButton(
                    onClick = {
                        if (isLogged) {
                            navController.navigate(GolfAdvisorRoute.UserProfileScreen(username))
                        } else {
                            navController.navigate(GolfAdvisorRoute.LoginScreen)
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (isLogged) Icons.Default.Person
                                else Icons.Outlined.Person,
                        contentDescription = if (isLogged)
                            stringResource(R.string.your_profile_icon_description)
                            else stringResource(R.string.login_icon_description)
                    )
                }
            }

            if (backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.SettingsScreen>() != true) {
                IconButton(onClick = {
                    navController.navigate(GolfAdvisorRoute.SettingsScreen)
                }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = stringResource(R.string.settings_icon_description)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}