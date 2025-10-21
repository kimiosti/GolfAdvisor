package com.example.golfadvisor.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golfadvisor.data.repository.GolfClubWithRating
import com.example.golfadvisor.data.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeState(val coursesWithRating: List<GolfClubWithRating>)

interface HomeAction {
    fun refresh()
}

class HomeViewModel(private val homeRepository: HomeRepository): ViewModel() {
    private val _state = MutableStateFlow(HomeState(ArrayList()))
    val state = _state.asStateFlow()

    val actions = object : HomeAction {
        override fun refresh() {
            viewModelScope.launch {
                val data = homeRepository.getAllClubsAndRatings()
                _state.value = HomeState(data)
            }
        }
    }
}