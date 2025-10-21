package com.example.golfadvisor.ui.screens.club_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golfadvisor.data.repository.ClubDetailRepository
import com.example.golfadvisor.data.repository.GolfClubWithRating
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ClubDetailState(val club: GolfClubWithRating?)

interface ClubDetailActions {
    fun loadClubInformation(clubName: String)
}

class ClubDetailViewModel(private val clubDetailRepository: ClubDetailRepository): ViewModel() {
    private val _state = MutableStateFlow(ClubDetailState(null))
    val state = _state.asStateFlow()

    val actions = object: ClubDetailActions {
        override fun loadClubInformation(clubName: String) {
            viewModelScope.launch {
                    val data = clubDetailRepository.getClubInformation(clubName = clubName)
                    _state.value = ClubDetailState(data)
                }
            }
    }
}