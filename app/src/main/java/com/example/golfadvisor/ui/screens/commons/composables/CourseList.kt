package com.example.golfadvisor.ui.screens.commons.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.golfadvisor.R
import com.example.golfadvisor.ui.navigation.GolfAdvisorRoute
import java.util.Locale

@Composable
fun SingleCourseCard(
    navController: NavHostController,
    name: String,
    address: String, averageRating: Float,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = {
                navController.navigate(GolfAdvisorRoute.ClubDetailScreen(name))
            }),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundedImagePreview(
                contentDescription = stringResource(R.string.home_club_cover_description),
                dpSize = 100
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = address,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                RatingBar(
                    rating = averageRating,
                    showRating = true,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun RatingBar(
    rating: Float,
    showRating: Boolean,
    modifier: Modifier = Modifier,
    starColor: Color = Color(0xFFFFC107)
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (rating < 0) {
            Text(
                text = stringResource(R.string.no_reviews_text),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            repeat(5) { index ->
                val starState = when {
                    rating >= index + 1 -> Icons.Filled.Star
                    rating >= index + 0.5f -> Icons.AutoMirrored.Filled.StarHalf
                    else -> Icons.Filled.StarBorder
                }
                Icon(
                    imageVector = starState,
                    contentDescription = when {
                        rating >= index + 1 -> stringResource(R.string.home_full_star_description)
                        rating >= index + 0.5f -> stringResource(R.string.home_half_star_description)
                        else -> stringResource(R.string.home_empty_star_description)
                    },
                    tint = starColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.size(2.dp))
            }
            if (showRating) {
                Text(
                    text = String.format(Locale.getDefault(), "%.2f", rating),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}