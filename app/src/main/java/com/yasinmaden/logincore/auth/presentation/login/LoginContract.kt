package com.yasinmaden.logincore.auth.presentation.login

object LoginContract {
    data class LoginUiState(
        val email: String = "",
        val password: String = "",
        val resetPasswordEmail: String = "",
        val passwordVisibility: Boolean = false,
        val resetPasswordDialogVisibility: Boolean = false
    )

    sealed class LoginUiAction {
        data class OnEmailChange(val email: String) : LoginUiAction()
        data class OnResetPasswordEmailChange(val email: String) : LoginUiAction()
        data class OnPasswordChange(val password: String) : LoginUiAction()
        data class OnLoginClick(val email: String, val password: String) : LoginUiAction()
        object OnVisibilityChange : LoginUiAction()
        object OnSignUpClick : LoginUiAction()
        object OnForgotPasswordTextClick : LoginUiAction()
        object OnResetPasswordDialogConfirm : LoginUiAction()
        object OnResetPasswordDialogDismiss : LoginUiAction()
        object OnResetPasswordDialogDismissRequest : LoginUiAction()




    }

    sealed class UiEffect {
        object NavigateToSignUp : UiEffect()
    }
}