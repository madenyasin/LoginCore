package com.yasinmaden.logincore.ui.login

object LoginContract {
    data class LoginUiState(
        val email: String = "",
        val password: String = "",
        val visibility: Boolean = false
    )

    sealed class LoginUiAction {
        data class OnEmailChange(val email: String) : LoginUiAction()
        data class OnPasswordChange(val password: String) : LoginUiAction()
        data class OnLoginClick(val email: String, val password: String) : LoginUiAction()
        object OnVisibilityChange : LoginUiAction()
        object OnSignUpClick : LoginUiAction()
    }

    sealed class UiEffect {
        object NavigateToSignUp : UiEffect()
    }
}