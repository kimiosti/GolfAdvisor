package com.example.golfadvisor.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.PersonOff
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.golfadvisor.R
import com.example.golfadvisor.data.enums.Theme
import com.example.golfadvisor.ui.navigation.GolfAdvisorRoute
import com.example.golfadvisor.ui.screens.commons.LoginViewModel
import com.example.golfadvisor.ui.screens.commons.ThemeViewModel

@Composable
fun SettingsScreen(
    navController: NavHostController,
    themeViewModel: ThemeViewModel,
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier
) {
    val loginState by loginViewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 24.dp)
    ) {
        Text(
            text = stringResource(R.string.settings_screen_theme_section_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = modifier.selectableGroup(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Theme.entries.forEach {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).selectable(
                        selected = themeViewModel.state.theme == it,
                        onClick = { themeViewModel.actions.setTheme(it) },
                        role = Role.RadioButton
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = themeViewModel.state.theme == it,
                        onClick = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Text(
                        text = stringResource(it.screenNameResourceId),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }

        if (loginState.isLogged) {
            Spacer(modifier = Modifier.height(24.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.settings_screen_profile_section_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OptionRow(
                    icon = Icons.Default.Key,
                    iconDescription = stringResource(R.string.settings_screen_change_password),
                    text = stringResource(R.string.settings_screen_change_password)
                ) {
                    navController.navigate(GolfAdvisorRoute.ChangePasswordScreen)
                }

                OptionRow(
                    icon = Icons.Default.Edit,
                    iconDescription = stringResource(R.string.settings_screen_change_user_data),
                    text = stringResource(R.string.settings_screen_change_user_data)
                ) {
                    navController.navigate(GolfAdvisorRoute.ChangeUserDataScreen)
                }

                OptionRow(
                    icon = Icons.Default.PersonOff,
                    iconDescription = stringResource(R.string.settings_screen_sign_out),
                    text = stringResource(R.string.settings_screen_sign_out)
                ) {
                    loginViewModel.actions.logout()
                    navController.navigate(GolfAdvisorRoute.HomeScreen) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OptionRow(
    icon: ImageVector,
    iconDescription: String,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconDescription,
            modifier = Modifier.size(25.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}