package com.yasinmaden.logincore.navigation

sealed class Destinations(val route: String) {
    data object Login : Destinations("login")
    data object SignUp : Destinations("sign_up")
}