package com.yasinmaden.logincore.navigation

sealed class Destinations(val route: String) {
    object Login : Destinations("login")
    object SignUp : Destinations("sign_up")
}