package com.yasinmaden.logincore.util

object Constants {

    sealed class Destinations(val route: String) {
        object Login : Destinations("login")
        object SignUp : Destinations("sign_up")
    }

    sealed class ClickableLink(val tag: String)  {
        object SignIn : ClickableLink("sign_in")
    }
}