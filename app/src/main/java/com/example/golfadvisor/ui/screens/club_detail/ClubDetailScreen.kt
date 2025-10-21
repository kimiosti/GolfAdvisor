package com.example.golfadvisor.ui.screens.club_detail

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import java.net.URLEncoder

@Composable
fun ClubDetailScreen(
    navController: NavHostController,
    clubDetailViewModel: ClubDetailViewModel,
    isLogged: Boolean,
    clubName: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val phoneIntentFailMessage = stringResource(R.string.club_detail_phone_intent_fail)
    val emailIntentFailMessage = stringResource(R.string.club_detail_email_intent_fail)
    val mapIntentFailMessage = stringResource(R.string.club_detail_address_intent_fail)

    val clubDetail by clubDetailViewModel.state.collectAsStateWithLifecycle()
    val club = clubDetail.club

    LaunchedEffect(Unit) {
        clubDetailViewModel.actions.loadClubInformation(clubName)
    }

    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (club != null) {
            RoundedImagePreview(
                contentDescription = stringResource(R.string.club_detail_cover_description),
                dpSize = 200
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = club.golfClub.clubName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            RatingBar(rating = club.averageRating, showRating = true)
            if (club.averageRating >= 0) {
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = {
                    navController.navigate(GolfAdvisorRoute.ClubReviewsScreen(club.golfClub.clubName))
                }) {
                    Text(text = stringResource(R.string.club_detail_see_all_reviews_button_content))
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 28.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("tel:${club.golfClub.phone}")
                        }
                        if (intent.resolveActivity(context.packageManager) != null) {
                            context.startActivity(intent)
                        } else {
                            Toast.makeText(
                                context,
                                phoneIntentFailMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = stringResource(R.string.club_detail_phone_icon_description),
                        modifier = Modifier.size(25.dp)
                    )
                    Text(
                        text = club.golfClub.phone,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("mailto:${club.golfClub.email}")
                        }
                        if (intent.resolveActivity(context.packageManager) != null) {
                            context.startActivity(intent)
                        } else {
                            Toast.makeText(
                                context,
                                emailIntentFailMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = stringResource(R.string.club_detail_email_icon_description),
                        modifier = Modifier.size(25.dp)
                    )
                    Text(
                        text = club.golfClub.email,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(
                                "geo:0,0?" +
                                        "q=${club.golfClub.latitude},${club.golfClub.longitude}" +
                                        "(${URLEncoder.encode(club.golfClub.clubName, "UTF-8")})"
                            )
                        }
                        if (intent.resolveActivity(context.packageManager) != null) {
                            context.startActivity(intent)
                        } else {
                            Toast.makeText(
                                context,
                                mapIntentFailMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = stringResource(R.string.club_detail_address_icon_description),
                        modifier = Modifier.size(25.dp)
                    )
                    Text(
                        text = club.golfClub.address,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    if (isLogged) {
                        navController.navigate(GolfAdvisorRoute.AddReviewScreen(club.golfClub.clubName))
                    } else {
                        navController.navigate(GolfAdvisorRoute.LoginScreen)
                    }
                }
            ) {
                Text(stringResource(R.string.club_detail_add_review_button_content))
            }
        } else {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = stringResource(R.string.club_detail_invalid_name),
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.club_detail_invalid_name),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}