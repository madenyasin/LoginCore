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
        data object OnVisibilityChange : LoginUiAction()
        data object OnSignUpClick : LoginUiAction()
    }

    sealed class UiEffect {
        data object NavigateToSignUp : UiEffect()
    }
}