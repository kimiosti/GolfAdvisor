package com.example.golfadvisor

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.golfadvisor.data.database.GolfAdvisorDatabase
import com.example.golfadvisor.data.repository.AddReviewRepository
import com.example.golfadvisor.data.repository.ChangePasswordRepository
import com.example.golfadvisor.data.repository.ChangeUserDataRepository
import com.example.golfadvisor.data.repository.UserFavoritesRepository
import com.example.golfadvisor.data.repository.ClubDetailRepository
import com.example.golfadvisor.data.repository.ClubReviewsRepository
import com.example.golfadvisor.data.repository.HomeRepository
import com.example.golfadvisor.data.repository.LoginRepository
import com.example.golfadvisor.data.repository.RegistrationRepository
import com.example.golfadvisor.data.repository.ReviewDetailRepository
import com.example.golfadvisor.data.repository.ScoringTrendRepository
import com.example.golfadvisor.data.repository.SearchRepository
import com.example.golfadvisor.data.repository.ThemeRepository
import com.example.golfadvisor.data.repository.UserProfileRepository
import com.example.golfadvisor.data.repository.UserReviewsRepository
import com.example.golfadvisor.ui.screens.add_review.AddReviewViewModel
import com.example.golfadvisor.ui.screens.change_password.ChangePasswordViewModel
import com.example.golfadvisor.ui.screens.change_user_data.ChangeUserDataViewModel
import com.example.golfadvisor.ui.screens.club_detail.ClubDetailViewModel
import com.example.golfadvisor.ui.screens.club_reviews.ClubReviewsViewModel
import com.example.golfadvisor.ui.screens.commons.LoginViewModel
import com.example.golfadvisor.ui.screens.commons.ThemeViewModel
import com.example.golfadvisor.ui.screens.commons.composables.action_button.ActionButtonViewModel
import com.example.golfadvisor.ui.screens.home.HomeViewModel
import com.example.golfadvisor.ui.screens.registration.RegistrationViewModel
import com.example.golfadvisor.ui.screens.review_detail.ReviewDetailViewModel
import com.example.golfadvisor.ui.screens.scoring_trend.ScoringTrendViewModel
import com.example.golfadvisor.ui.screens.search.SearchViewModel
import com.example.golfadvisor.ui.screens.user_favorites.UserFavoritesViewModel
import com.example.golfadvisor.ui.screens.user_profile.UserProfileViewModel
import com.example.golfadvisor.ui.screens.user_reviews.UserReviewsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val Context.dataStore by preferencesDataStore("golf-advisor")

val appModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = GolfAdvisorDatabase::class.java,
            name = "golf-advisor"
        ).build()
    }

    single { get<Context>().dataStore }

    single { LoginRepository(get(), get<GolfAdvisorDatabase>().userDao()) }

    single { ThemeRepository(get()) }

    single { RegistrationRepository(get<GolfAdvisorDatabase>().userDao()) }

    single {
        HomeRepository(
            get<GolfAdvisorDatabase>().golfClubDao(),
            get<GolfAdvisorDatabase>().reviewDao()
        )
    }

    single {
        SearchRepository(
            get<GolfAdvisorDatabase>().golfClubDao(),
            get<GolfAdvisorDatabase>().reviewDao()
        )
    }

    single {
        ClubDetailRepository(
            get<GolfAdvisorDatabase>().golfClubDao(),
            get<GolfAdvisorDatabase>().reviewDao()
        )
    }

    single {
        UserFavoritesRepository(
            get<GolfAdvisorDatabase>().favoriteDao(),
            get<GolfAdvisorDatabase>().userDao()
        )
    }

    single {
        ClubReviewsRepository(
            get<GolfAdvisorDatabase>().golfClubDao(),
            get<GolfAdvisorDatabase>().reviewDao()
        )
    }

    single {
        UserProfileRepository(
            get<GolfAdvisorDatabase>().userDao()
        )
    }

    single {
        UserReviewsRepository(
            get<GolfAdvisorDatabase>().reviewDao(),
            get<GolfAdvisorDatabase>().userDao()
        )
    }

    single {
        AddReviewRepository(
            get<GolfAdvisorDatabase>().reviewDao(),
            get<GolfAdvisorDatabase>().userDao()
        )
    }

    single {
        ReviewDetailRepository(
            get<GolfAdvisorDatabase>().reviewDao()
        )
    }

    single {
        ScoringTrendRepository(
            get<GolfAdvisorDatabase>().reviewDao(),
            get<GolfAdvisorDatabase>().userDao()
        )
    }

    single {
        ChangeUserDataRepository(
            get<GolfAdvisorDatabase>().userDao()
        )
    }

    single {
        ChangePasswordRepository(
            get<GolfAdvisorDatabase>().userDao()
        )
    }

    viewModel { LoginViewModel(get()) }

    viewModel { ThemeViewModel(get()) }

    viewModel { RegistrationViewModel(get()) }

    viewModel { HomeViewModel(get()) }

    viewModel { SearchViewModel(get()) }

    viewModel { ClubDetailViewModel(get()) }

    viewModel { ActionButtonViewModel(get()) }

    viewModel { ClubReviewsViewModel(get()) }

    viewModel { UserProfileViewModel(get()) }

    viewModel { UserReviewsViewModel(get()) }

    viewModel { UserFavoritesViewModel(get()) }

    viewModel { AddReviewViewModel(get()) }

    viewModel { ReviewDetailViewModel(get()) }

    viewModel { ScoringTrendViewModel(get()) }

    viewModel { ChangeUserDataViewModel(get()) }

    viewModel { ChangePasswordViewModel(get()) }
}