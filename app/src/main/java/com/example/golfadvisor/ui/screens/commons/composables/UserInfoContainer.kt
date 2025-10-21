package com.example.golfadvisor.ui.screens.commons.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.golfadvisor.R
import com.example.golfadvisor.data.database.entities.User
import java.util.Locale

@Composable
fun UserInfoContainer(user: User) {
    RoundedImagePreview(
        contentDescription = stringResource(R.string.user_profile_picture_description),
        dpSize = 200
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = user.username,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(12.dp))
    if (user.name != null && user.surname != null) {
        Text(
            text = "${user.name} ${user.surname}",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
    if (user.scoringAverage != null) {
        Text(
            text = stringResource(R.string.user_profile_scoring_average_label)
                    + ": "
                    + String.format(Locale.getDefault(), "%.2f", user.scoringAverage),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}