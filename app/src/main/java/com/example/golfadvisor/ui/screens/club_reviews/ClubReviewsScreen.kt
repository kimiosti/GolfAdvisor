package com.example.golfadvisor.ui.screens.club_reviews

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.golfadvisor.R
import com.example.golfadvisor.ui.screens.commons.composables.ShownInfo
import com.example.golfadvisor.ui.screens.commons.composables.SingleReviewCard
import com.example.golfadvisor.ui.screens.commons.composables.RoundedImagePreview

@Composable
fun ClubReviewsScreen(
    navController: NavHostController,
    clubReviewsViewModel: ClubReviewsViewModel,
    clubName: String,
    modifier: Modifier = Modifier
) {
    val clubReviewsState by clubReviewsViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        clubReviewsViewModel.actions.checkReviews(clubName)
    }

    LazyColumn(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val clubInfo = clubReviewsState.clubInfo
        val reviews = clubReviewsState.reviews
        if (clubInfo == null) {
            item {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = stringResource(R.string.club_reviews_invalid_data_icon_description),
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(R.string.club_reviews_invalid_data_error_message),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            item {
                RoundedImagePreview(
                    contentDescription = stringResource(R.string.club_reviews_club_image_description),
                    dpSize = 200
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = clubInfo.clubName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { navController.navigateUp() }
                ) {
                    Text(stringResource(R.string.club_reviews_back_to_info_button_content))
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (reviews.isNullOrEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.club_reviews_no_reviews_text),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(reviews) {
                    Spacer(modifier = Modifier.height(12.dp))
                    SingleReviewCard(
                        navController = navController,
                        review = it,
                        shownInfo = ShownInfo.User
                    )
                }
            }
        }
    }
}

