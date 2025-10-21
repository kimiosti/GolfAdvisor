package com.example.golfadvisor.ui.screens.user_reviews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.golfadvisor.R
import com.example.golfadvisor.ui.navigation.GolfAdvisorRoute
import com.example.golfadvisor.ui.screens.commons.LoginViewModel
import com.example.golfadvisor.ui.screens.commons.composables.ShownInfo
import com.example.golfadvisor.ui.screens.commons.composables.SingleReviewCard
import com.example.golfadvisor.ui.screens.commons.composables.UserInfoContainer

@Composable
fun UserReviewsScreen(
    navController: NavHostController,
    userReviewsViewModel: UserReviewsViewModel,
    loginViewModel: LoginViewModel,
    username: String,
    modifier: Modifier = Modifier
) {
    val userReviewsState by userReviewsViewModel.state.collectAsStateWithLifecycle()
    val loginState by loginViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { userReviewsViewModel.actions.checkReviews(username) }

    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp).padding(top = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val userInfo = userReviewsState.userInfo
        if (userInfo == null) {
            item {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = stringResource(R.string.user_reviews_error_icon_description)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(R.string.user_reviews_error_info_text),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            item {
                UserInfoContainer(user = userInfo)
            }
            if (userReviewsState.reviews.isEmpty()) {
                if (loginState.isLogged && loginState.username == userInfo.username) {
                    item {
                        Text(
                            text = stringResource(R.string.your_reviews_no_reviews_text),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        AddReviewText(navController = navController)
                    }
                } else {
                    item {
                        Text(
                            text = stringResource(R.string.user_reviews_no_reviews_text),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                items(items = userReviewsState.reviews) {
                    SingleReviewCard(
                        navController = navController,
                        review = it,
                        shownInfo = ShownInfo.Club
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
                if (loginState.isLogged && loginState.username == userInfo.username) {
                    item {
                        AddReviewText(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun AddReviewText(navController: NavHostController) {
    Text(
        text = stringResource(R.string.user_reviews_add_review_text),
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.clickable {
            navController.navigate(GolfAdvisorRoute.SearchClubScreen)
        }
    )
}