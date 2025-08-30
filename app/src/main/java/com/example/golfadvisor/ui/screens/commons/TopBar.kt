package com.example.golfadvisor.ui.screens.commons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.golfadvisor.R
import com.example.golfadvisor.ui.navigation.GolfAdvisorRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController, modifier: Modifier = Modifier) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val title = when {
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.LoginScreen>() == true
            -> stringResource(R.string.login_screen_title)
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.RegisterScreen>() == true
            -> stringResource(R.string.register_screen_title)
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.CourseDetailScreen>() == true
            -> stringResource(R.string.course_detail_screen_title)
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.AddReviewScreen>() == true
            -> stringResource(R.string.add_review_screen_title)
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.CourseReviewsScreen>() == true
            -> stringResource(R.string.course_reviews_screen_title)
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.SingleReviewScreen>() == true
            -> stringResource(R.string.single_review_screen_title)
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.VisitProfileScreen>() == true
            -> stringResource(R.string.visit_profile_screen_title)
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.YourProfileScreen>() == true
            -> stringResource(R.string.your_profile_screen_title)
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.SettingsScreen>() == true
            -> stringResource(R.string.settings_screen_title)
        else -> stringResource(R.string.home_screen_title)
    }

    val canGoBack = navController.previousBackStackEntry != null
            && backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.LoginScreen>() != true
            && backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.HomeScreen>() != true

    val showProfileIcon = backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.LoginScreen>() != true
            && backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.RegisterScreen>() != true

    CenterAlignedTopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (canGoBack) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.AutoMirrored.Default.ArrowBack, stringResource(R.string.go_back_icon_alt))
                }
            }
        },
        actions = {
            if (showProfileIcon) {
                IconButton(onClick = {
                    navController.navigate(GolfAdvisorRoute.YourProfileScreen)

                    /* TODO - complete implementation checking login: if not logged, redirect to login screen */
                }) {
                    Icon(Icons.Outlined.Person, stringResource(R.string.your_profile_alt))
                }
            }
            IconButton(onClick = { navController.navigate(GolfAdvisorRoute.SettingsScreen) }) {
                Icon(Icons.Outlined.Settings, stringResource(R.string.settings_alt))
            }
        },
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}