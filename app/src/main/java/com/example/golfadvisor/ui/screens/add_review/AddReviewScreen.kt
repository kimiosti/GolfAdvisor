package com.example.golfadvisor.ui.screens.add_review

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.golfadvisor.R
import com.example.golfadvisor.data.enums.AccessType
import com.example.golfadvisor.ui.navigation.GolfAdvisorRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReviewScreen(
    navController: NavHostController,
    addReviewViewModel: AddReviewViewModel,
    isLogged: Boolean,
    username: String,
    clubName: String,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    var title by rememberSaveable { mutableStateOf("") }
    var text by rememberSaveable { mutableStateOf("") }
    var rating by rememberSaveable { mutableIntStateOf(-1) }
    var accessType by rememberSaveable { mutableStateOf<AccessType?>(null) }
    var price by rememberSaveable { mutableStateOf("") }
    var score by rememberSaveable { mutableStateOf("") }

    var isDropdownExpanded by rememberSaveable { mutableStateOf(false) }
    val dropdownOptions: List<AccessType?> = listOf(null) + AccessType.entries.toTypedArray()

    var missingTitle by rememberSaveable { mutableStateOf(false) }
    var missingText by rememberSaveable { mutableStateOf(false) }
    var missingRating by rememberSaveable { mutableStateOf(false) }
    var missingAccessType by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(isLogged) {
        if (!isLogged) {
            navController.navigate(GolfAdvisorRoute.LoginScreen) {
                popUpTo<GolfAdvisorRoute.AddReviewScreen> {
                    inclusive = true
                }
            }
        }
    }

    if (isLogged) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (missingTitle || missingText || missingRating || missingAccessType)
                    stringResource(R.string.add_review_missing_fields_text)
                else stringResource(R.string.add_review_default_text),
                color = if (missingTitle || missingText || missingRating || missingAccessType)
                    MaterialTheme.colorScheme.error
                else Color.Unspecified
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = clubName,
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.add_review_club_name_label)) },
                singleLine = true,
                readOnly = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.add_review_rating_label),
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (missingRating) MaterialTheme.colorScheme.error else Color.Unspecified
                )
                Spacer(modifier = Modifier.width(8.dp))
                repeat((1..5).count()) {
                    Icon(
                        imageVector = if (rating < it + 1) Icons.Default.StarBorder else Icons.Default.Star,
                        contentDescription = stringResource(R.string.add_review_rating_star_description),
                        tint = Color(color = 0xFFFFC107),
                        modifier = Modifier.size(30.dp).clickable {
                            missingRating = false
                            rating = it + 1
                        }
                    )
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = {
                    missingTitle = false
                    title = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.add_review_title_label)) },
                trailingIcon = {
                    if (missingTitle) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = stringResource(
                                R.string.add_review_missing_title_description
                            )
                        )
                    }
                },
                isError = missingTitle,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = text,
                onValueChange = {
                    missingText = false
                    text = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.add_review_text_label)) },
                trailingIcon = {
                    if (missingText) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = stringResource(
                                R.string.add_review_missing_text_description
                            )
                        )
                    }
                },
                isError = missingText
            )

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = isDropdownExpanded,
                onExpandedChange = { isDropdownExpanded = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = stringResource(
                        id = accessType?.screenNameResourceId
                            ?: R.string.add_review_access_type_placeholder
                    ),
                    onValueChange = { },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    readOnly = true,
                    isError = missingAccessType,
                    label = { Text(stringResource(R.string.add_review_access_type_label)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = isDropdownExpanded
                    ) }
                )

                ExposedDropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false }
                ) {
                    dropdownOptions.forEach {
                        DropdownMenuItem(
                            text = { Text(stringResource(
                                id = it?.screenNameResourceId
                                    ?: R.string.add_review_access_type_default_value
                            )) },
                            onClick = {
                                missingAccessType = false
                                isDropdownExpanded = false
                                if (it != AccessType.HalfRound && it != AccessType.FullRound) {
                                    score = ""
                                }
                                accessType = it
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.add_review_price_label)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = score,
                onValueChange = { score = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.add_review_score_label)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                readOnly = accessType != AccessType.HalfRound && accessType != AccessType.FullRound
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.add_review_required_fields_info),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (title == "" || text == "" || rating < 0 || accessType == null) {
                        missingTitle = title == ""
                        missingText = text == ""
                        missingRating = rating < 0
                        missingAccessType = accessType == null
                    } else {
                        addReviewViewModel.actions.addReview(
                            username = username,
                            clubName = clubName,
                            title = title,
                            text = text,
                            rating = rating,
                            accessType = accessType!!,
                            price = price,
                            score = score
                        )
                        navController.navigateUp()
                    }
                }
            ) {
                Text(stringResource(R.string.add_review_add_review_button))
            }
        }
    }
}