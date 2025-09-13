package com.sharif.simplenote.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sharif.simplenote.R
import com.sharif.simplenote.ui.components.ButtonSize
import com.sharif.simplenote.ui.components.ButtonType
import com.sharif.simplenote.ui.components.CustomButton
import com.sharif.simplenote.ui.components.InputField
import com.sharif.simplenote.ui.components.NavBar
import com.sharif.simplenote.ui.components.RightActionType
import com.sharif.simplenote.ui.components.ScreenTitle
import com.sharif.simplenote.ui.components.StatusBar
import com.sharif.simplenote.ui.theme.AppTypography
import com.sharif.simplenote.ui.theme.NeutralWhite
import com.sharif.simplenote.ui.theme.PrimaryBase

@Composable
fun RegisterScreen(navController: NavController? = null) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var retypePassword by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeutralWhite)
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // Status Bar
        StatusBar()

        // Nav Bar
        NavBar(
            backButtonText = stringResource(R.string.register_navbar_back),
            showTitle = false,
            onBackClick = {}
        )
        // Content
        Column {
            Spacer(modifier = Modifier.height(10.dp))

            //Screen Title
            ScreenTitle(
                title = stringResource(R.string.register_title),
                description = stringResource(R.string.register_description)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Input Fields And Actions
            Column {
                // Input Fields
                Column {
                    // First Name field
                    InputField(
                        label = true,
                        labelText = stringResource(R.string.register_firstname_label),
                        value = firstName,
                        onValueChange = { firstName = it },
                        placeholderText = stringResource(R.string.register_firstname_placeholder)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Last Name field
                    InputField(
                        label = true,
                        labelText = stringResource(R.string.register_lastname_label),
                        value = lastName,
                        onValueChange = { lastName = it },
                        placeholderText = stringResource(R.string.register_lastname_placeholder)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Username field
                    InputField(
                        label = true,
                        labelText = stringResource(R.string.register_username_label),
                        value = username,
                        onValueChange = { username = it },
                        placeholderText = stringResource(R.string.register_username_placeholder),
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Email field
                    InputField(
                        label = true,
                        labelText = stringResource(R.string.register_email_label),
                        value = email,
                        onValueChange = { email = it },
                        placeholderText = stringResource(R.string.register_email_placeholder)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Password field
                    InputField(
                        labelText = stringResource(R.string.register_password_label),
                        label = true,
                        value = password,
                        onValueChange = { password = it },
                        placeholderText = stringResource(R.string.register_password_placeholder),
                        isPassword = true
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Retype Password field
                    InputField(
                        labelText = stringResource(R.string.register_password_retype_label),
                        label = true,
                        value = retypePassword,
                        onValueChange = { retypePassword = it },
                        placeholderText = stringResource(R.string.register_password_placeholder),
                        isPassword = true
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Actions
                Column {
                    // Register button
                    CustomButton(
                        text = stringResource(R.string.register_button_register),
                        type = ButtonType.Primary,
                        onClick = {
                            // Validate and register
                        },
                        enabled = true,
                        icon = true,
                        iconPosition = "right",
                        size = ButtonSize.Block
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Already have an account text
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.register_button_login),
                            style = AppTypography.textBaseMedium,
                            color = PrimaryBase,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.clickable {
                                navController?.popBackStack()
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    RegisterScreen()
}