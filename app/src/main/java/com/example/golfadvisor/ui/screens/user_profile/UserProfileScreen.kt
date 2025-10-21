package com.example.golfadvisor.ui.screens.user_profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.golfadvisor.R
import com.example.golfadvisor.ui.navigation.GolfAdvisorRoute
import com.example.golfadvisor.ui.screens.commons.LoginViewModel
import com.example.golfadvisor.ui.screens.commons.composables.SingleBadgeCard
import com.example.golfadvisor.ui.screens.commons.composables.UserInfoContainer

@Composable
fun UserProfileScreen(
    navController: NavHostController,
    userProfileViewModel: UserProfileViewModel,
    loginViewModel: LoginViewModel,
    username: String,
    modifier: Modifier = Modifier
) {
    val loginState by loginViewModel.state.collectAsStateWithLifecycle()
    val userProfileState by userProfileViewModel.state.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) { userProfileViewModel.actions.checkProfileInfo(username) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 12.dp)
            .verticalScroll(scrollState)
    ) {
        val user = userProfileState.user
        if (user == null) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = stringResource(R.string.user_profile_error_icon_description),
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.user_profile_error_info_text),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        } else {
            UserInfoContainer(user)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                SingleBadgeCard(
                    badgeName = stringResource(user.scoringBadge.nameResourceId),
                    badgeDescription = stringResource(user.scoringBadge.descriptionResourceId),
                    buttonContent = stringResource(R.string.user_profile_scoring_badge_button),
                    onButtonClick = {
                        navController.navigate(GolfAdvisorRoute.UserScoringTrendScreen(user.username))
                    }
                )
                SingleBadgeCard(
                    badgeName = stringResource(user.reviewsBadge.titleResourceId),
                    badgeDescription = stringResource(user.reviewsBadge.descriptionResourceId),
                    buttonContent = stringResource(R.string.user_profile_reviews_badge_button),
                    onButtonClick = {
                        navController.navigate(GolfAdvisorRoute.UserReviewsScreen(user.username))
                    }
                )
                SingleBadgeCard(
                    badgeName = stringResource(user.favoritesBadge.titleResourceId),
                    badgeDescription = stringResource(user.favoritesBadge.descriptionResourceId),
                    buttonContent = stringResource(R.string.user_profile_favorites_badge_button),
                    onButtonClick = {
                        navController.navigate(GolfAdvisorRoute.UserFavoritesScreen(user.username))
                    }
                )
            }

            if (loginState.isLogged && loginState.username == user.username) {
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = {
                    loginViewModel.actions.logout()
                    navController.navigate(GolfAdvisorRoute.HomeScreen) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                    }
                }) {
                    Text(text = stringResource(R.string.user_profile_logout_button_content))
                }
            }
        }
    }
}