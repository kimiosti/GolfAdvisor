package com.example.golfadvisor.ui.screens.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.golfadvisor.R
import com.example.golfadvisor.ui.navigation.GolfAdvisorRoute

@Composable
fun LoginScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val paddingTop = LocalConfiguration.current.screenHeightDp / 5

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier.padding(top = paddingTop.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(text = stringResource(R.string.login_username_label)) }
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.login_password_label)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                onClick = {
                    /* TODO - implement login logic */
                    navController.navigate(GolfAdvisorRoute.HomeScreen)
                }
            ) {
                Text(stringResource(R.string.login_button_content))
            }
        }
        Column(
            modifier = Modifier.padding(top = 36.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.clickable {
                    navController.navigate(GolfAdvisorRoute.RegisterScreen)
                }
            ) {
                Text(
                    text = stringResource(R.string.login_still_no_account),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                )
                Text(text = " ", fontSize = MaterialTheme.typography.bodySmall.fontSize)
                Text(
                    text = stringResource(R.string.login_register),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = stringResource(R.string.login_alternative),
                fontSize = MaterialTheme.typography.bodySmall.fontSize
            )
            Text(
                modifier = Modifier.clickable { navController.navigate(GolfAdvisorRoute.HomeScreen) },
                text = stringResource(R.string.login_continue_as_guest),
                fontSize = MaterialTheme.typography.bodySmall.fontSize
            )
        }
    }
}