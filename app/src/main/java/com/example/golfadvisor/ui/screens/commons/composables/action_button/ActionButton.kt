package com.example.golfadvisor.ui.screens.commons.composables.action_button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.example.golfadvisor.R
import com.example.golfadvisor.ui.navigation.GolfAdvisorRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ActionButton(
    navController: NavHostController,
    isLogged: Boolean,
    username: String,
    actionButtonViewModel: ActionButtonViewModel,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
) {
    val actionButtonState by actionButtonViewModel.state.collectAsStateWithLifecycle()
    val backStackEntry by navController.currentBackStackEntryAsState()

    val favoriteAddedMessage = stringResource(R.string.club_detail_favorite_added)
    val favoriteRemovedMessage = stringResource(R.string.club_detail_favorite_removed)
    val snackbarUndoMessage = stringResource(R.string.club_detail_snackbar_undo_message)

    if (
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.ClubReviewsScreen>() == true
    ) {
        val route = backStackEntry?.toRoute<GolfAdvisorRoute.ClubReviewsScreen>()
        if (route != null) {
            FloatingActionButton(
                onClick = {
                    if (isLogged) {
                        navController.navigate(GolfAdvisorRoute.AddReviewScreen(
                            clubName = route.clubName
                        ))
                    } else {
                        navController.navigate(GolfAdvisorRoute.LoginScreen)
                    }
                },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.fab_add_review_icon_description)
                )
            }
        }
    }

    if (
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.ClubDetailScreen>() == true
        && isLogged
    ) {
        val route = backStackEntry?.toRoute<GolfAdvisorRoute.ClubDetailScreen>()
        if (route != null) {
            actionButtonViewModel.actions.checkFavorite(username, route.clubName)
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = if (actionButtonState.isCourseFavorite)
                                favoriteRemovedMessage
                                else favoriteAddedMessage,
                            actionLabel = snackbarUndoMessage,
                            duration = SnackbarDuration.Short,
                            withDismissAction = true
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            actionButtonViewModel.actions.toggleFavorite(
                                username,
                                route.clubName
                            )
                        }
                    }

                    actionButtonViewModel.actions.toggleFavorite(username, route.clubName)
                },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ) {
                Icon(
                    imageVector = if (actionButtonState.isCourseFavorite)
                        Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(
                        if (actionButtonState.isCourseFavorite)
                            R.string.fab_remove_favorite_icon_description
                            else R.string.fab_add_favorite_icon_description
                    )
                )
            }
        }
    }

    if (
        backStackEntry?.destination?.hasRoute<GolfAdvisorRoute.HomeScreen>() == true
    ) {
        FloatingActionButton(
            onClick = { navController.navigate(GolfAdvisorRoute.SearchClubScreen) },
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.fab_search_icon_description)
            )
        }
    }
}