package com.example.golfadvisor.ui.screens.change_password

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.golfadvisor.R
import com.example.golfadvisor.ui.navigation.GolfAdvisorRoute
import com.example.golfadvisor.ui.screens.commons.LoginViewModel

@Composable
fun ChangePasswordScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    changePasswordViewModel: ChangePasswordViewModel,
    modifier: Modifier = Modifier
) {
    val loginState by loginViewModel.state.collectAsStateWithLifecycle()
    val changePasswordState by changePasswordViewModel.state.collectAsStateWithLifecycle()

    var oldPassword by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var repeatNewPassword by rememberSaveable { mutableStateOf("") }

    var isOldPasswordInvalid by rememberSaveable { mutableStateOf(false) }
    var isNewPasswordEqualToOld by rememberSaveable { mutableStateOf(false) }
    var areNewPasswordsDifferent by rememberSaveable { mutableStateOf(false) }
    var isNewPasswordEmpty by rememberSaveable { mutableStateOf(false) }

    var isOldPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var isNewPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var isRepeatNewPasswordVisible by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(loginState) {
        if (!loginState.isLogged) {
            navController.navigate(GolfAdvisorRoute.LoginScreen) {
                popUpTo<GolfAdvisorRoute.ChangePasswordScreen> {
                    inclusive = true
                }
            }
        }
    }

    LaunchedEffect(changePasswordState) {
        if (changePasswordState.passwordChanged) {
            navController.navigateUp()
        } else {
            isOldPasswordInvalid = changePasswordState.loginFailed
        }
    }

    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = when {
                isOldPasswordInvalid -> stringResource(R.string.change_password_screen_login_failed)
                isNewPasswordEqualToOld -> stringResource(R.string.change_password_screen_same_password)
                areNewPasswordsDifferent -> stringResource(R.string.change_password_screen_password_not_matching)
                isNewPasswordEmpty -> stringResource(R.string.change_password_screen_no_new_password)
                else -> stringResource(R.string.change_password_screen_info_text)
            },
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = if (isOldPasswordInvalid || isNewPasswordEqualToOld || areNewPasswordsDifferent || isNewPasswordEmpty)
                MaterialTheme.colorScheme.error
                else Color.Unspecified
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = oldPassword,
            onValueChange = {
                isOldPasswordInvalid = false
                oldPassword = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.change_password_screen_old_password_label)) },
            trailingIcon = {
                IconButton(onClick = { isOldPasswordVisible = !isOldPasswordVisible }) {
                    Icon(
                        imageVector = if (isOldPasswordVisible) Icons.Default.VisibilityOff
                            else Icons.Default.Visibility,
                        contentDescription = if (isOldPasswordVisible)
                            stringResource(R.string.change_password_screen_hide_old_password)
                            else stringResource(R.string.change_password_screen_show_old_password)
                    )
                }
            },
            isError = isOldPasswordInvalid,
            visualTransformation = if (isOldPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = newPassword,
            onValueChange = {
                isNewPasswordEqualToOld = false
                areNewPasswordsDifferent = false
                isNewPasswordEmpty = false
                newPassword = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.change_password_screen_new_password_label)) },
            trailingIcon = {
                IconButton(onClick = { isNewPasswordVisible = !isNewPasswordVisible }) {
                    Icon(
                        imageVector = if (isNewPasswordVisible) Icons.Default.VisibilityOff
                            else Icons.Default.Visibility,
                        contentDescription = if (isNewPasswordVisible)
                            stringResource(R.string.change_password_screen_hide_new_password)
                            else stringResource(R.string.change_password_screen_show_new_password)
                    )
                }
            },
            isError = isNewPasswordEqualToOld || areNewPasswordsDifferent || isNewPasswordEmpty,
            visualTransformation = if (isNewPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = repeatNewPassword,
            onValueChange = {
                isNewPasswordEqualToOld = false
                areNewPasswordsDifferent = false
                isNewPasswordEmpty = false
                repeatNewPassword = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.change_password_screen_repeat_new_password_label)) },
            trailingIcon = {
                IconButton(onClick = { isRepeatNewPasswordVisible = !isRepeatNewPasswordVisible }) {
                    Icon(
                        imageVector = if (isRepeatNewPasswordVisible) Icons.Default.VisibilityOff
                        else Icons.Default.Visibility,
                        contentDescription = if (isRepeatNewPasswordVisible)
                            stringResource(R.string.change_password_screen_hide_repeated_new_password)
                        else stringResource(R.string.change_password_screen_show_repeated_new_password)
                    )
                }
            },
            isError = isNewPasswordEqualToOld || areNewPasswordsDifferent || isNewPasswordEmpty,
            visualTransformation = if (isRepeatNewPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            areNewPasswordsDifferent = false
            isNewPasswordEmpty = false
            isNewPasswordEqualToOld = false
            isOldPasswordInvalid = false

            if (newPassword != repeatNewPassword) {
                areNewPasswordsDifferent = true
            } else if (newPassword == "") {
                isNewPasswordEmpty = true
            } else if (newPassword == oldPassword) {
                isNewPasswordEqualToOld = true
            } else {
                changePasswordViewModel.actions.updateUserInfo(
                    username = loginState.username,
                    oldPassword = oldPassword,
                    newPassword = newPassword
                )
            }
        }) {
            Text(stringResource(R.string.change_password_screen_confirm_button_content))
        }
    }
}