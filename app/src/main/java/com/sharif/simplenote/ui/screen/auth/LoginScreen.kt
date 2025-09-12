package com.sharif.simplenote.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sharif.simplenote.R
import com.sharif.simplenote.ui.components.ButtonSize
import com.sharif.simplenote.ui.components.ButtonType
import com.sharif.simplenote.ui.components.CustomButton
import com.sharif.simplenote.ui.components.InputField
import com.sharif.simplenote.ui.components.InputFieldState
import com.sharif.simplenote.ui.components.ScreenTitle
import com.sharif.simplenote.ui.theme.AppTypography
import com.sharif.simplenote.ui.theme.NeutralBaseGrey
import com.sharif.simplenote.ui.theme.NeutralDarkGrey
import com.sharif.simplenote.ui.theme.NeutralLightGrey
import com.sharif.simplenote.ui.theme.NeutralWhite
import com.sharif.simplenote.ui.theme.PrimaryBase
import com.sharif.simplenote.ui.theme.interFamily

@Composable
fun LoginScreen(navController: NavController? = null) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeutralWhite)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header section
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            ScreenTitle(
                title = stringResource(R.string.login_title),
                description = stringResource(R.string.login_description)
            )

            Spacer(modifier = Modifier.height(50.dp))
        }

        // Input Fields and Actions
        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top
        ) {
            // Input Fields
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top
            ) {
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
            Column(
                modifier = Modifier
                    .weight(2f),
                verticalArrangement = Arrangement.Top
            ) {
                // Login button
                CustomButton(
                    text = stringResource(R.string.login_button_login),
                    type = ButtonType.Primary,
                    onClick = {
                        // Validate and login
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
                        //navigate to register
                    },
                    enabled = true,
                    size = ButtonSize.Block,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LoginScreen()
}