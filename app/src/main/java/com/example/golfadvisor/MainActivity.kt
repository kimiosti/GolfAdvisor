package com.example.golfadvisor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.golfadvisor.ui.navigation.NavGraph
import com.example.golfadvisor.ui.screens.commons.TopBar
import com.example.golfadvisor.ui.theme.GolfAdvisorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GolfAdvisorTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = { TopBar(navController) },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        logged = false, /* TODO - add check for login on startup */
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}