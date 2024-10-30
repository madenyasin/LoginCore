package com.yasinmaden.logincore.navigation

sealed class Screen(
    val title: String,
    val route: String,
) {
    object Home : Screen(
        title = "Home",
        route = "home",
    )
    object Profile : Screen(
        title = "Profile",
        route = "profile",
    )
    object Login : Screen(
        title = "Login",
        route = "login",
    )
    object Signup : Screen(
        title = "signup",
        route = "signup",
    )
    object Settings : Screen(
        title = "Settings",
        route = "settings",
    )


}