package com.example.golfadvisor.ui.screens.user_favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
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
import com.example.golfadvisor.ui.screens.commons.composables.RoundedImagePreview
import com.example.golfadvisor.ui.screens.commons.composables.UserInfoContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun UserFavoritesScreen(
    navController: NavHostController,
    userFavoritesViewModel: UserFavoritesViewModel,
    loginViewModel: LoginViewModel,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    username: String,
    modifier: Modifier = Modifier
) {
    val userFavoritesState by userFavoritesViewModel.state.collectAsStateWithLifecycle()
    val loginState by loginViewModel.state.collectAsStateWithLifecycle()

    val user = userFavoritesState.userInfo

    LaunchedEffect(Unit) { userFavoritesViewModel.actions.checkFavorites(username) }

    LazyColumn(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (user == null) {
            item {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = stringResource(R.string.user_favorites_error_icon_description)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(R.string.user_favorites_error_info_text),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            item {
                UserInfoContainer(user = user)
            }
            if (userFavoritesState.favorites.isEmpty()) {
                item {
                    Text(
                        text = if (loginState.isLogged && loginState.username == user.username)
                            stringResource(R.string.your_favorites_no_favorites_text)
                            else stringResource(R.string.user_favorites_no_favorites_text),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(items = userFavoritesState.favorites) {
                    val snackbarMessage = stringResource(R.string.user_favorites_removed_snackbar)
                    val snackbarUndoLabel = stringResource(R.string.user_favorites_removed_snackbar_undo)

                    SingleClubCard(
                        navController = navController,
                        clubName = it.clubName,
                        clubAddress = it.address,
                        isButtonShown = loginState.isLogged && loginState.username == username
                    ) {
                        userFavoritesViewModel.actions.toggleFavorite(
                            user.username,
                            it.clubName
                        )
                        coroutineScope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = snackbarMessage,
                                actionLabel = snackbarUndoLabel,
                                duration = SnackbarDuration.Short,
                                withDismissAction = true
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                userFavoritesViewModel.actions.toggleFavorite(
                                    user.username,
                                    it.clubName
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            if (loginState.isLogged && loginState.username == user.username) {
                item {
                    Text(
                        text = stringResource(R.string.user_favorites_find_favorites_text),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            navController.navigate(GolfAdvisorRoute.SearchClubScreen)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SingleClubCard(
    navController: NavHostController,
    clubName: String,
    clubAddress: String,
    isButtonShown: Boolean,
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(GolfAdvisorRoute.ClubDetailScreen(clubName))
            },
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
                dpSize = 100,
                contentDescription = stringResource(R.string.user_favorites_club_cover_description)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1F)
            ) {
                Text(
                    text = clubName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = clubAddress,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.weight(1F))
                if (isButtonShown) {
                    Button(
                        onClick = onButtonClick,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(
                            text = stringResource(R.string.user_favorites_remove_club_button_content)
                        )
                    }
                }
            }
        }
    }
}