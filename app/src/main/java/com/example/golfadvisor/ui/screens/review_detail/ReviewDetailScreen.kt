package com.example.golfadvisor.ui.screens.review_detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.golfadvisor.ui.screens.commons.composables.RatingBar
import com.example.golfadvisor.ui.screens.commons.composables.RoundedImagePreview
import java.util.Locale

@Composable
fun ReviewDetailScreen(
    navController: NavHostController,
    reviewDetailViewModel: ReviewDetailViewModel,
    reviewId: Int,
    modifier: Modifier = Modifier
) {
    val reviewDetailState by reviewDetailViewModel.state.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()

    val review = reviewDetailState.review

    LaunchedEffect(Unit) { reviewDetailViewModel.actions.checkReviews(reviewId) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 12.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (review == null) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = stringResource(R.string.review_detail_error_icon_description),
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.review_detail_error_message),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        } else {
            RoundedImagePreview(
                dpSize = 200,
                contentDescription = stringResource(R.string.review_detail_club_cover_image_description)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = review.clubName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate(GolfAdvisorRoute.ClubDetailScreen(review.clubName))
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            RatingBar(
                rating = review.rating.toFloat(),
                showRating = false
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                ),
                modifier = Modifier
                    .clickable {
                    navController.navigate(GolfAdvisorRoute.UserProfileScreen(review.username))
                }
            ) {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RoundedImagePreview(
                        dpSize = 40,
                        contentDescription = stringResource(
                            R.string.review_detail_author_profile_pic_description
                        )
                    )
                    Text(
                        text = review.username,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.Start
            ) {
                LabelTextRow(
                    label = stringResource(R.string.review_detail_access_type_label),
                    text = stringResource(review.accessType.screenNameResourceId)
                )
                if (review.price != null) {
                    LabelTextRow(
                        label = stringResource(R.string.review_detail_price_label),
                        text = String.format(Locale.getDefault(), format = "%.2f", review.price)
                    )
                }
                if (review.score != null) {
                    LabelTextRow(
                        label = stringResource(R.string.review_detail_score_label),
                        text = String.format(Locale.getDefault(), "%d", review.score.toInt())
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = review.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = review.text,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun LabelTextRow(
    label: String,
    text: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}