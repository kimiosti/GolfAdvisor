package com.example.golfadvisor.ui.screens.registration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.golfadvisor.R
import com.example.golfadvisor.ui.navigation.GolfAdvisorRoute
import com.example.golfadvisor.ui.screens.commons.LoginViewModel

@Composable
fun RegistrationScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    registrationViewModel: RegistrationViewModel,
    modifier: Modifier = Modifier
) {
    val registrationState by registrationViewModel.state.collectAsStateWithLifecycle()
    val loginState by loginViewModel.state.collectAsStateWithLifecycle()
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var repeatPassword by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var surname by rememberSaveable { mutableStateOf("") }

    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var isRepeatPasswordVisible by rememberSaveable { mutableStateOf(false) }

    var invalidUsername by rememberSaveable { mutableStateOf(false) }
    var invalidPassword by rememberSaveable { mutableStateOf(false) }
    var missingUsername by rememberSaveable { mutableStateOf(false) }
    var missingPassword by rememberSaveable { mutableStateOf(false) }
    var missingRepeatPassword by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(registrationState) {
        if (registrationState.isRegistrationOk == true) {
            loginViewModel.actions.login(username)
        } else {
            invalidUsername = !registrationState.isUsernameValid
        }
    }

    LaunchedEffect(loginState) {
        if (loginState.isLogged) {
            navController.navigateUp()
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = when {
                missingUsername || missingPassword || missingRepeatPassword
                    -> stringResource(R.string.registration_missing_fields)
                invalidPassword -> stringResource(R.string.registration_password_mismatch)
                invalidUsername -> stringResource(R.string.registration_username_taken)
                else -> stringResource(R.string.registration_insert_credentials)
            },
            style = MaterialTheme.typography.bodyMedium,
            color = if (invalidUsername || invalidPassword || missingUsername
                || missingPassword || missingRepeatPassword) MaterialTheme.colorScheme.error
                else Color.Unspecified
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = {
                missingUsername = false
                registrationViewModel.actions.checkUsername(username)
                username = it
            },
            label = { Text(stringResource(R.string.registration_username_label)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = invalidUsername || missingUsername,
            trailingIcon = {
                if (invalidUsername || missingUsername) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = if (invalidUsername)
                            stringResource(R.string.registration_username_taken)
                            else stringResource(R.string.registration_missing_username_description)
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                invalidPassword = false
                missingPassword = false
                password = it
            },
            label = { Text(stringResource(R.string.registration_password_label)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = invalidPassword || missingPassword,
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible)
                            Icons.Default.VisibilityOff
                        else Icons.Default.Visibility,
                        contentDescription = if (isPasswordVisible)
                            stringResource(R.string.registration_hide_password)
                        else stringResource(R.string.registration_show_password)
                    )
                }
           },
            visualTransformation = if (isPasswordVisible)
                VisualTransformation.None
                else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = repeatPassword,
            onValueChange = {
                invalidPassword = false
                missingRepeatPassword = false
                repeatPassword = it
            },
            label = { Text(stringResource(R.string.registration_repeat_password_label)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = invalidPassword || missingRepeatPassword,
            trailingIcon = {
                IconButton(onClick = { isRepeatPasswordVisible = !isRepeatPasswordVisible }) {
                    Icon(
                        imageVector = if (isRepeatPasswordVisible)
                            Icons.Default.VisibilityOff
                            else Icons.Default.Visibility,
                        contentDescription = if (isRepeatPasswordVisible)
                            stringResource(R.string.registration_hide_password)
                            else stringResource(R.string.registration_show_password)
                    )
                }
            },
            visualTransformation = if (isRepeatPasswordVisible)
                VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.registration_name_label)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = surname,
            onValueChange = { surname = it },
            label = { Text(stringResource(R.string.registration_surname_label)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.registration_required_fields_info),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (username == "" || password == "" || repeatPassword == "") {
                    missingUsername = username == ""
                    missingPassword = password == ""
                    missingRepeatPassword = repeatPassword == ""
                } else if (password != repeatPassword) {
                    invalidPassword = true
                } else {
                    registrationViewModel.actions.registerUser(
                        username,
                        password,
                        name,
                        surname
                    )
                }
            }
        ) {
            Text(stringResource(R.string.registration_button_content))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.registration_already_account),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = stringResource(R.string.registration_login_now),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate(GolfAdvisorRoute.LoginScreen) {
                        popUpTo<GolfAdvisorRoute.RegistrationScreen> {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
