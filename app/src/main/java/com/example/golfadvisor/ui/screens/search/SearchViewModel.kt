package com.example.golfadvisor.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golfadvisor.data.repository.GolfClubWithRating
import com.example.golfadvisor.data.repository.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SearchState(val foundClubs: List<GolfClubWithRating>)

class SearchViewModel(private val searchRepository: SearchRepository): ViewModel() {
    private val _state = MutableStateFlow(SearchState(ArrayList()))
    val state = _state.asStateFlow()

    init {
        this.searchCourses("")
    }

    fun searchCourses(searchText: String) {
        viewModelScope.launch {
            val data = searchRepository.searchForCourses(searchText)
            _state.value = SearchState(data)
        }
    }
}