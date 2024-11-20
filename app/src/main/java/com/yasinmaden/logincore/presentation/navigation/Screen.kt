package com.yasinmaden.logincore.presentation.navigation

sealed class Screen(
    val title: String,
    val route: String,
) {
    data object Home : Screen(
        title = "Home",
        route = "home",
    )
    data object Profile : Screen(
        title = "Profile",
        route = "profile",
    )
    data object SignIn : Screen(
        title = "signin",
        route = "signin",
    )
    data object SignUp : Screen(
        title = "signup",
        route = "signup",
    )
    data object Settings : Screen(
        title = "Settings",
        route = "settings",
    )


}