package com.yasinmaden.logincore.ui.signup

sealed class ClickableLink(val tag: String)  {
    object SignIn : ClickableLink("sign_in")
}