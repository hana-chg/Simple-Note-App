package com.sharif.simplenote.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sharif.simplenote.R
import com.sharif.simplenote.ui.components.ButtonSize
import com.sharif.simplenote.ui.components.ButtonType
import com.sharif.simplenote.ui.components.CustomButton
import com.sharif.simplenote.ui.components.InputField
import com.sharif.simplenote.ui.components.InputFieldState
import com.sharif.simplenote.ui.components.ScreenTitle
import com.sharif.simplenote.ui.components.StatusBar
import com.sharif.simplenote.ui.navigation.NavItem
import com.sharif.simplenote.ui.theme.AppTypography
import com.sharif.simplenote.ui.theme.NeutralDarkGrey
import com.sharif.simplenote.ui.theme.NeutralLightGrey
import com.sharif.simplenote.ui.theme.NeutralWhite
import com.sharif.simplenote.viewModel.AuthViewModel

@Composable
fun LoginScreen(navController: NavHostController, viewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val emailError by remember { mutableStateOf(false) }
    val passwordError by remember { mutableStateOf(false) }
    val loginState by viewModel.loginState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeutralWhite)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        StatusBar()
        // Content
        Column {
            Spacer(modifier = Modifier.height(32.dp))

            // Screen Title
            ScreenTitle(
                title = stringResource(R.string.login_title),
                description = stringResource(R.string.login_description)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Input Fields and Actions
            Column {
                // Input Fields
                Column {
                    // Email field
                    InputField(
                        label = true,
                        labelText = stringResource(R.string.login_email_label),
                        value = email,
                        onValueChange = { email = it },
                        placeholderText = stringResource(R.string.login_email_placeholder),
                        state = if (emailError) InputFieldState.Error else InputFieldState.Default
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Password field
                    InputField(
                        labelText = stringResource(R.string.login_password_label),
                        label = true,
                        value = password,
                        onValueChange = { password = it },
                        placeholderText = stringResource(R.string.login_password_placeholder),
                        isPassword = true,
                        state = if (passwordError) InputFieldState.Error else InputFieldState.Default
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))


                // Actions
                Column {
                    // Login button
                    CustomButton(
                        text = stringResource(R.string.login_button_login),
                        type = ButtonType.Primary,
                        onClick = {
                             viewModel.login(email, password)
                        },
                        enabled = true,
                        icon = true,
                        iconPosition = "right",
                        size = ButtonSize.Block
                    )

                    // Or with lines on both sides
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Left line
                        HorizontalDivider(
                            modifier = Modifier
                                .weight(1f)
                                .height(1.dp),
                            color = NeutralLightGrey
                        )

                        // Or text
                        Text(
                            text = "Or",
                            style = AppTypography.text2xsMedium,
                            color = NeutralDarkGrey,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        // Right line
                        HorizontalDivider(
                            modifier = Modifier
                                .weight(1f)
                                .height(1.dp),
                            color = NeutralLightGrey
                        )
                    }

                    //Register Button
                    CustomButton(
                        text = stringResource(R.string.login_button_register),
                        type = ButtonType.Transparent,
                        onClick = {
                            navController.navigate(NavItem.Register.route)
                        },
                        enabled = true,
                        size = ButtonSize.Block,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                }
            }
        }
    }

    LaunchedEffect (loginState.isSuccess) {
        if (loginState.isSuccess) {
            navController.navigate(NavItem.Home.route) {
                popUpTo(NavItem.Login.route) { inclusive = true }
            }
        }
    }
}
