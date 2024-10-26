package com.yasinmaden.logincore.util

object Constants {

    sealed class ClickableLink(val tag: String)  {
        object SignIn : ClickableLink("sign_in")
        object ForgotPassword : ClickableLink("forgot_password")
    }
}