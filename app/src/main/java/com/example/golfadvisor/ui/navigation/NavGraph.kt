package com.example.golfadvisor.ui.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.golfadvisor.ui.screens.add_review.AddReviewScreen
import com.example.golfadvisor.ui.screens.add_review.AddReviewViewModel
import com.example.golfadvisor.ui.screens.change_password.ChangePasswordScreen
import com.example.golfadvisor.ui.screens.change_password.ChangePasswordViewModel
import com.example.golfadvisor.ui.screens.change_user_data.ChangeUserDataScreen
import com.example.golfadvisor.ui.screens.change_user_data.ChangeUserDataViewModel
import com.example.golfadvisor.ui.screens.club_detail.ClubDetailScreen
import com.example.golfadvisor.ui.screens.club_detail.ClubDetailViewModel
import com.example.golfadvisor.ui.screens.club_reviews.ClubReviewsScreen
import com.example.golfadvisor.ui.screens.club_reviews.ClubReviewsViewModel
import com.example.golfadvisor.ui.screens.commons.LoginViewModel
import com.example.golfadvisor.ui.screens.commons.ThemeViewModel
import com.example.golfadvisor.ui.screens.home.HomeScreen
import com.example.golfadvisor.ui.screens.home.HomeViewModel
import com.example.golfadvisor.ui.screens.login.LoginScreen
import com.example.golfadvisor.ui.screens.registration.RegistrationScreen
import com.example.golfadvisor.ui.screens.registration.RegistrationViewModel
import com.example.golfadvisor.ui.screens.review_detail.ReviewDetailScreen
import com.example.golfadvisor.ui.screens.review_detail.ReviewDetailViewModel
import com.example.golfadvisor.ui.screens.scoring_trend.ScoringTrendScreen
import com.example.golfadvisor.ui.screens.scoring_trend.ScoringTrendViewModel
import com.example.golfadvisor.ui.screens.search.SearchScreen
import com.example.golfadvisor.ui.screens.search.SearchViewModel
import com.example.golfadvisor.ui.screens.settings.SettingsScreen
import com.example.golfadvisor.ui.screens.user_favorites.UserFavoritesScreen
import com.example.golfadvisor.ui.screens.user_favorites.UserFavoritesViewModel
import com.example.golfadvisor.ui.screens.user_profile.UserProfileScreen
import com.example.golfadvisor.ui.screens.user_profile.UserProfileViewModel
import com.example.golfadvisor.ui.screens.user_reviews.UserReviewsScreen
import com.example.golfadvisor.ui.screens.user_reviews.UserReviewsViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.compose.koinViewModel

@Composable
fun GolfAdvisorNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
    themeViewModel: ThemeViewModel,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    NavHost(
        navController = navController,
        startDestination = GolfAdvisorRoute.HomeScreen,
        modifier = modifier
    ) {
        this.composable<GolfAdvisorRoute.LoginScreen> {
            LoginScreen(
                navController = navController,
                loginViewModel = loginViewModel
            )
        }

        this.composable<GolfAdvisorRoute.RegistrationScreen> {
            val registrationViewModel = koinViewModel<RegistrationViewModel>()
            RegistrationScreen(
                navController = navController,
                loginViewModel = loginViewModel,
                registrationViewModel = registrationViewModel
            )
        }

        this.composable<GolfAdvisorRoute.HomeScreen> {
            val homeViewModel = koinViewModel<HomeViewModel>()
            HomeScreen(
                navController = navController,
                homeViewModel = homeViewModel,
                loginViewModel = loginViewModel
            )
        }

        this.composable<GolfAdvisorRoute.SearchClubScreen> {
            val searchViewModel = koinViewModel<SearchViewModel>()
            SearchScreen(
                navController = navController,
                searchViewModel = searchViewModel
            )
        }

        this.composable<GolfAdvisorRoute.ClubDetailScreen> { backstackEntry ->
            val route = backstackEntry.toRoute<GolfAdvisorRoute.ClubDetailScreen>()
            val clubDetailViewModel = koinViewModel<ClubDetailViewModel>()
            ClubDetailScreen(
                navController = navController,
                clubDetailViewModel = clubDetailViewModel,
                isLogged = loginViewModel.state.value.isLogged,
                clubName = route.clubName
            )
        }

        this.composable<GolfAdvisorRoute.ClubReviewsScreen> { backstackEntry ->
            val route = backstackEntry.toRoute<GolfAdvisorRoute.ClubReviewsScreen>()
            val clubReviewsViewModel = koinViewModel<ClubReviewsViewModel>()
            ClubReviewsScreen(
                navController = navController,
                clubReviewsViewModel = clubReviewsViewModel,
                clubName = route.clubName
            )
        }

        this.composable<GolfAdvisorRoute.ReviewDetailScreen> { backstackEntry ->
            val route = backstackEntry.toRoute<GolfAdvisorRoute.ReviewDetailScreen>()
            val reviewDetailViewModel = koinViewModel<ReviewDetailViewModel>()
            ReviewDetailScreen(
                navController = navController,
                reviewDetailViewModel = reviewDetailViewModel,
                reviewId = route.reviewID
            )
        }

        this.composable<GolfAdvisorRoute.UserProfileScreen> { backstackEntry ->
            val route = backstackEntry.toRoute<GolfAdvisorRoute.UserProfileScreen>()
            val userProfileViewModel = koinViewModel<UserProfileViewModel>()
            UserProfileScreen(
                navController = navController,
                userProfileViewModel = userProfileViewModel,
                loginViewModel = loginViewModel,
                username = route.username
            )
        }

        this.composable<GolfAdvisorRoute.UserFavoritesScreen> { backstackEntry ->
            val route = backstackEntry.toRoute<GolfAdvisorRoute.UserFavoritesScreen>()
            val userFavoritesViewModel = koinViewModel<UserFavoritesViewModel>()
            UserFavoritesScreen(
                navController = navController,
                userFavoritesViewModel = userFavoritesViewModel,
                loginViewModel = loginViewModel,
                snackbarHostState = snackbarHostState,
                coroutineScope = coroutineScope,
                username = route.username
            )
        }

        this.composable<GolfAdvisorRoute.UserScoringTrendScreen> { backstackEntry ->
            val route = backstackEntry.toRoute<GolfAdvisorRoute.UserScoringTrendScreen>()
            val scoringTrendViewModel = koinViewModel<ScoringTrendViewModel>()
            ScoringTrendScreen(
                scoringTrendViewModel = scoringTrendViewModel,
                username = route.username
            )
        }

        this.composable<GolfAdvisorRoute.UserReviewsScreen> { backstackEntry ->
            val route = backstackEntry.toRoute<GolfAdvisorRoute.UserReviewsScreen>()
            val userReviewsViewModel = koinViewModel<UserReviewsViewModel>()
            UserReviewsScreen(
                navController = navController,
                userReviewsViewModel = userReviewsViewModel,
                loginViewModel = loginViewModel,
                username = route.username
            )
        }

        this.composable<GolfAdvisorRoute.AddReviewScreen> { backstackEntry ->
            val route = backstackEntry.toRoute<GolfAdvisorRoute.AddReviewScreen>()
            val addReviewViewModel = koinViewModel<AddReviewViewModel>()
            AddReviewScreen(
                navController = navController,
                addReviewViewModel = addReviewViewModel,
                isLogged = loginViewModel.state.value.isLogged,
                username = loginViewModel.state.value.username,
                clubName = route.clubName
            )
        }

        this.composable<GolfAdvisorRoute.SettingsScreen> {
            SettingsScreen(
                navController = navController,
                themeViewModel = themeViewModel,
                loginViewModel = loginViewModel
            )
        }

        this.composable<GolfAdvisorRoute.ChangeUserDataScreen> {
            val changeUserDataViewModel = koinViewModel<ChangeUserDataViewModel>()
            ChangeUserDataScreen(
                navController = navController,
                loginViewModel = loginViewModel,
                changeUserDataViewModel = changeUserDataViewModel
            )
        }

        this.composable<GolfAdvisorRoute.ChangePasswordScreen> {
            val changePasswordViewModel = koinViewModel<ChangePasswordViewModel>()
            ChangePasswordScreen(
                navController = navController,
                loginViewModel = loginViewModel,
                changePasswordViewModel = changePasswordViewModel
            )
        }
    }
}