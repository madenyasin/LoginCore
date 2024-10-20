package com.yasinmaden.logincore.auth.presentation.signup

sealed class ClickableLink(val tag: String)  {
    object SignIn : ClickableLink("sign_in")
}