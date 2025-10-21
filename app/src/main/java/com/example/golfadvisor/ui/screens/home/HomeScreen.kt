package com.example.golfadvisor.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.golfadvisor.R
import com.example.golfadvisor.ui.navigation.GolfAdvisorRoute
import com.example.golfadvisor.ui.screens.commons.LoginViewModel
import com.example.golfadvisor.ui.screens.commons.composables.SingleCourseCard

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier
) {
    val data by homeViewModel.state.collectAsStateWithLifecycle()
    val loginState by loginViewModel.state.collectAsStateWithLifecycle()

    var showLoginDisclaimer by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        homeViewModel.actions.refresh()
    }

    LaunchedEffect(loginState) {
        showLoginDisclaimer = !loginState.isLogged
    }

    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp).padding(top = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showLoginDisclaimer) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.home_not_logged),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.home_log_register_now),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            navController.navigate(GolfAdvisorRoute.LoginScreen)
                        }
                    )
                }
            }
        }

        items(items = data.coursesWithRating) {
            SingleCourseCard(
                navController = navController,
                name = it.golfClub.clubName,
                address = it.golfClub.address,
                averageRating = it.averageRating
            )
        }
    }
}