package com.yasinmaden.logincore.common

object Constants {

    sealed class ClickableLink(val tag: String)  {
        object SignIn : ClickableLink("sign_in")
        object ForgotPassword : ClickableLink("forgot_password")
    }
}