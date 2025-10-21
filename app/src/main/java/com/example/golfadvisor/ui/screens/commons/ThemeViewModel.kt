package com.example.golfadvisor.ui.screens.commons

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golfadvisor.data.enums.Theme
import com.example.golfadvisor.data.repository.ThemeRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class ThemeState(val theme: Theme)

interface ThemeActions {
    fun setTheme(theme: Theme)
}

class ThemeViewModel(repository: ThemeRepository): ViewModel() {
    var state by mutableStateOf(ThemeState(Theme.System))
        private set

    val actions = object: ThemeActions {
        override fun setTheme(theme: Theme) {
            state = ThemeState(theme)
            viewModelScope.launch { repository.setTheme(state.theme) }
        }
    }

    init {
        viewModelScope.launch { state = ThemeState(repository.theme.first()) }
    }
}