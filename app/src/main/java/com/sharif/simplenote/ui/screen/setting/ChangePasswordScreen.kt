package com.sharif.simplenote.ui.screen.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sharif.simplenote.ui.components.CustomButton
import com.sharif.simplenote.ui.components.InputField
import com.sharif.simplenote.ui.components.NavBar
import com.sharif.simplenote.ui.components.StatusBar
import com.sharif.simplenote.ui.navigation.NavItem
import com.sharif.simplenote.ui.theme.NeutralWhite
import com.sharif.simplenote.viewModel.SettingViewModel

@Composable
fun ChangePasswordScreen(
    navController: NavHostController,
    viewModel: SettingViewModel = hiltViewModel()
) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var oldPasswordError by remember { mutableStateOf(false) }
    var newPasswordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }
    
    val changePasswordState by viewModel.changePasswordState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeutralWhite)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StatusBar()
        
        NavBar(
            modifier = Modifier,
            showTitle = true,
            title = "Change Password",
            onBackClick = {
                navController.navigate(NavItem.Setting.route) {
                    popUpTo(NavItem.ChangePassword.route) { inclusive = true }
                }
            },
            borderBottom = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputField(
                label = true,
                labelText = "Current Password",
                value = oldPassword,
                onValueChange = { 
                    oldPassword = it
                    oldPasswordError = false
                },
                placeholderText = "Enter current password",
                isPassword = true,
                state = if (oldPasswordError) com.sharif.simplenote.ui.components.InputFieldState.Error else com.sharif.simplenote.ui.components.InputFieldState.Default
            )

            Spacer(modifier = Modifier.height(24.dp))

            InputField(
                label = true,
                labelText = "New Password",
                value = newPassword,
                onValueChange = { 
                    newPassword = it
                    newPasswordError = false
                },
                placeholderText = "Enter new password",
                isPassword = true,
                state = if (newPasswordError) com.sharif.simplenote.ui.components.InputFieldState.Error else com.sharif.simplenote.ui.components.InputFieldState.Default
            )

            Spacer(modifier = Modifier.height(24.dp))

            InputField(
                label = true,
                labelText = "Confirm New Password",
                value = confirmPassword,
                onValueChange = { 
                    confirmPassword = it
                    confirmPasswordError = false
                },
                placeholderText = "Confirm new password",
                isPassword = true,
                state = if (confirmPasswordError) com.sharif.simplenote.ui.components.InputFieldState.Error else com.sharif.simplenote.ui.components.InputFieldState.Default
            )

            Spacer(modifier = Modifier.height(40.dp))

            CustomButton(
                text = "Change Password",
                type = com.sharif.simplenote.ui.components.ButtonType.Primary,
                onClick = {
                    var hasError = false
                    
                    if (oldPassword.isEmpty()) {
                        oldPasswordError = true
                        hasError = true
                    }
                    
                    if (newPassword.isEmpty() || newPassword.length < 6) {
                        newPasswordError = true
                        hasError = true
                    }
                    
                    if (confirmPassword.isEmpty() || newPassword != confirmPassword) {
                        confirmPasswordError = true
                        hasError = true
                    }
                    
                    if (!hasError) {
                        viewModel.changePassword(oldPassword, newPassword)
                    }
                },
                enabled = !changePasswordState.isLoading,
                size = com.sharif.simplenote.ui.components.ButtonSize.Block
            )
        }
    }

    LaunchedEffect(changePasswordState.isSuccess) {
        if (changePasswordState.isSuccess) {
            navController.navigate(NavItem.Setting.route) {
                popUpTo(NavItem.ChangePassword.route) { inclusive = true }
            }
        }
    }
}