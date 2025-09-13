package com.sharif.simplenote.ui.navigation

sealed class NavItem(val route: String) {
    data object Onboarding : NavItem("onboarding")
    data object Login : NavItem("login")
    data object Register : NavItem("register")
    data object Home : NavItem("notes")
    data object NoteEdit : NavItem("note/edit/{id}")
    data object Setting : NavItem("setting")
    data object ChangePassword : NavItem("changePassword")
}