package com.example.golfadvisor.ui.screens.change_user_data

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.golfadvisor.R
import com.example.golfadvisor.ui.navigation.GolfAdvisorRoute
import com.example.golfadvisor.ui.screens.commons.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ChangeUserDataScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    changeUserDataViewModel: ChangeUserDataViewModel,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier
) {
    val loginState by loginViewModel.state.collectAsStateWithLifecycle()
    val changeUserDataState by changeUserDataViewModel.state.collectAsStateWithLifecycle()

    var name by rememberSaveable { mutableStateOf("") }
    var surname by rememberSaveable { mutableStateOf("") }

    val userDataChangedMessage = stringResource(R.string.change_user_data_success_snackbar_message)

    LaunchedEffect(Unit) {
        changeUserDataViewModel.actions.loadUserData(loginState.username)
    }

    LaunchedEffect(loginState) {
        if (!loginState.isLogged) {
            navController.navigate(GolfAdvisorRoute.LoginScreen) {
                popUpTo<GolfAdvisorRoute.ChangeUserDataScreen> {
                    inclusive = true
                }
            }
        }
    }

    LaunchedEffect(changeUserDataState) {
        name = changeUserDataState.name
        surname = changeUserDataState.surname
    }

    if (loginState.isLogged) {
        Column(
            modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.change_user_data_screen_info_text),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.change_user_data_name_label)) },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = surname,
                onValueChange = { surname = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.change_user_data_surname_label)) },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    changeUserDataViewModel.actions.updateUserData(
                        loginState.username,
                        name,
                        surname
                    )
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = userDataChangedMessage,
                            withDismissAction = true,
                            duration = SnackbarDuration.Short
                        )
                    }
                    navController.navigateUp()
                }
            ) {
                Text(stringResource(R.string.change_user_data_confirm_button_text))
            }
        }
    }
}