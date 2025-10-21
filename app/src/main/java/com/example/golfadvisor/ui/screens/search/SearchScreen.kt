package com.example.golfadvisor.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.golfadvisor.R
import com.example.golfadvisor.ui.screens.commons.composables.SingleCourseCard

@Composable
fun SearchScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    modifier: Modifier = Modifier
) {
    val data by searchViewModel.state.collectAsStateWithLifecycle()
    var searchString by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
       OutlinedTextField(
           value = searchString,
           onValueChange = {
               searchString = it
               searchViewModel.searchCourses(searchString)
           },
           modifier = Modifier.fillMaxWidth(),
           label = { Text(stringResource(R.string.search_screen_bar_label)) },
           singleLine = true
       )

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(top=12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = data.foundClubs) {
                SingleCourseCard(
                    navController = navController,
                    name = it.golfClub.clubName,
                    address = it.golfClub.address,
                    averageRating = it.averageRating
                )
            }
        }
    }
}