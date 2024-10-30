package com.yasinmaden.logincore.auth.presentation.login

object LoginContract {

    data class UiState(
        val email: String = "",
        val password: String = "",
        val resetPasswordEmail: String = "",
        val passwordVisibility: Boolean = false,
        val resetPasswordDialogVisibility: Boolean = false
    )

    sealed class UiAction {
        data class OnEmailChange(val email: String) : UiAction()
        data class OnResetPasswordEmailChange(val email: String) : UiAction()
        data class OnPasswordChange(val password: String) : UiAction()
        data class OnLoginClick(val email: String, val password: String) : UiAction()
        object OnVisibilityChange : UiAction()
        object OnSignUpClick : UiAction()
        object OnForgotPasswordTextClick : UiAction()
        object OnResetPasswordDialogConfirm : UiAction()
        object OnResetPasswordDialogDismiss : UiAction()
        object OnResetPasswordDialogDismissRequest : UiAction()
    }

    sealed class UiEffect {
        object NavigateToSignUp : UiEffect()
        object NavigateToHome : UiEffect()
    }
}