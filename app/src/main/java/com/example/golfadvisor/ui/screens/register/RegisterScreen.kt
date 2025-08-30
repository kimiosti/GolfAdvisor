package com.example.golfadvisor.ui.screens.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.golfadvisor.R
import com.example.golfadvisor.ui.navigation.GolfAdvisorRoute

@Composable
fun RegisterScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var repeatPassword by rememberSaveable { mutableStateOf("") }
    var isPasswordValid by rememberSaveable { mutableStateOf(true) }
    var isRequiredFieldMissing by rememberSaveable { mutableStateOf(false) }
    var name by rememberSaveable { mutableStateOf("") }
    var surname by rememberSaveable { mutableStateOf("") }

    fun validatePassword() {
        isPasswordValid = password == repeatPassword
    }

    Column(
        modifier = modifier.fillMaxSize().padding(top = 64.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                isRequiredFieldMissing = false
            },
            label = { Text(stringResource(R.string.register_username_label)) },
            supportingText = { Text(stringResource(R.string.register_required_field_support)) },
            isError = isRequiredFieldMissing,
            trailingIcon = {
                if (isRequiredFieldMissing) {
                    Icon(Icons.Default.Warning, stringResource(R.string.register_missing_field_icon_alt))
                }
            }
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                if (repeatPassword != "") {
                    validatePassword()
                }
            },
            label = { Text(stringResource(R.string.register_password_label)) },
            supportingText = { Text(stringResource(R.string.register_required_field_support)) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = isRequiredFieldMissing,
            trailingIcon = {
                if (isRequiredFieldMissing) {
                    Icon(Icons.Default.Warning, stringResource(R.string.register_missing_field_icon_alt))
                }
            }
        )

        OutlinedTextField(
            value = repeatPassword,
            label = { Text(stringResource(R.string.register_repeat_password_label)) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = {
                repeatPassword = it
                validatePassword()
            },
            supportingText = {
                if (isPasswordValid) {
                    Text(stringResource(R.string.register_required_field_support))
                } else {
                    Text(stringResource(R.string.register_password_must_be_same))
                }
            },
            isError = !isPasswordValid || isRequiredFieldMissing,
            trailingIcon = {
                if (!isPasswordValid || isRequiredFieldMissing) {
                    Icon(
                        Icons.Default.Warning,
                        if (isRequiredFieldMissing)
                            stringResource(R.string.register_missing_field_icon_alt)
                            else stringResource(R.string.register_password_warning_icon_alt)
                    )
                }
            }
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.register_name_label)) }
        )

        OutlinedTextField(
            value = surname,
            onValueChange = { surname = it },
            label = { Text(stringResource(R.string.register_surname_label)) }
        )

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            onClick = {
                /* TODO - handle registration */
                isRequiredFieldMissing = username == "" || password == "" || repeatPassword == ""

                if (!isRequiredFieldMissing && isPasswordValid) {
                    navController.navigate(GolfAdvisorRoute.HomeScreen) {
                        navController.popBackStack(
                            route = GolfAdvisorRoute.RegisterScreen,
                            inclusive = true
                        )
                    }
                }
            }
        ) {
            Text(stringResource(R.string.register_button_content))
        }
    }
}