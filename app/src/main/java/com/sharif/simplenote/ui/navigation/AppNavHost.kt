package com.sharif.simplenote.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sharif.simplenote.ui.screen.auth.LoginScreen
import com.sharif.simplenote.ui.screen.auth.RegisterScreen
import com.sharif.simplenote.ui.screen.notes.HomeScreen
import com.sharif.simplenote.ui.screen.notes.NoteEditScreen
import com.sharif.simplenote.ui.screen.setting.SettingScreen
import com.sharif.simplenote.ui.screen.setting.ChangePasswordScreen
import com.sharif.simplenote.viewModel.AuthViewModel

@Composable
fun AppNavHost(navController: NavHostController) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val startDestination =
        if (authViewModel.isLoggedIn()) NavItem.Home.route else NavItem.Login.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(NavItem.Login.route) {
            LoginScreen(navController = navController, viewModel = hiltViewModel())
        }
        composable(NavItem.Register.route) {
            RegisterScreen(navController = navController, viewModel = hiltViewModel())
        }
        composable(NavItem.Home.route) {
            HomeScreen(navController = navController, viewModel = hiltViewModel())
        }
        composable(NavItem.NoteEdit.route) { backStackEntry ->
            NoteEditScreen(
                navController = navController,
                noteId = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0,
                viewModel = hiltViewModel()
            )
        }
        composable(NavItem.Setting.route) {
            SettingScreen(navController = navController, viewModel = hiltViewModel())
        }
        composable(NavItem.ChangePassword.route) {
            ChangePasswordScreen(navController = navController, viewModel = hiltViewModel())
        }
    }
}