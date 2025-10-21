package com.example.golfadvisor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.golfadvisor.data.enums.Theme
import com.example.golfadvisor.ui.navigation.GolfAdvisorNavGraph
import com.example.golfadvisor.ui.screens.commons.composables.action_button.ActionButton
import com.example.golfadvisor.ui.screens.commons.LoginViewModel
import com.example.golfadvisor.ui.screens.commons.ThemeViewModel
import com.example.golfadvisor.ui.screens.commons.composables.TopBar
import com.example.golfadvisor.ui.screens.commons.composables.action_button.ActionButtonViewModel
import com.example.golfadvisor.ui.theme.GolfAdvisorTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val loginViewModel = koinViewModel<LoginViewModel>()
            val themeViewModel = koinViewModel<ThemeViewModel>()
            val actionButtonViewModel = koinViewModel<ActionButtonViewModel>()
            GolfAdvisorTheme(
                darkTheme = when(themeViewModel.state.theme) {
                    Theme.Light -> false
                    Theme.Dark -> true
                    Theme.System -> isSystemInDarkTheme()
                }
            ) {
                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()

                val loginState by loginViewModel.state.collectAsStateWithLifecycle()

                Scaffold(
                    topBar = {
                        TopBar(
                            navController = navController,
                            isLogged = loginState.isLogged,
                            username = loginState.username
                        )
                    },
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    floatingActionButton = {
                        ActionButton(
                            navController = navController,
                            isLogged = loginState.isLogged,
                            username = loginState.username,
                            actionButtonViewModel = actionButtonViewModel,
                            snackbarHostState = snackbarHostState,
                            scope = scope
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    GolfAdvisorNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        loginViewModel = loginViewModel,
                        themeViewModel = themeViewModel,
                        snackbarHostState = snackbarHostState,
                        coroutineScope = scope
                    )
                }
            }
        }
    }
}