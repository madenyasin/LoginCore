package com.yasinmaden.logincore.presentation.auth.login

import android.content.Context

object LoginContract {

    data class UiState(
        val email: String = "",
        val password: String = "",
        val resetPasswordEmail: String = "",
        val passwordVisibility: Boolean = false,
        val resetPasswordDialogVisibility: Boolean = false,
        val isEmailError: Boolean = false,
        val isPasswordError: Boolean = false,
    )

    sealed class UiAction {
        data class OnEmailChange(val email: String) : UiAction()
        data class OnResetPasswordEmailChange(val email: String) : UiAction()
        data class OnPasswordChange(val password: String) : UiAction()
        data object OnLoginClick : UiAction()
        data class OnGoogleSignInClick(val context: Context) : UiAction()
        data object OnVisibilityChange : UiAction()
        data object OnSignUpClick : UiAction()
        data object OnForgotPasswordTextClick : UiAction()
        data object OnResetPasswordDialogConfirm : UiAction()
        data object OnResetPasswordDialogDismiss : UiAction()
        data object OnResetPasswordDialogDismissRequest : UiAction()
    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        data object NavigateToSignUp : UiEffect()
        data object NavigateToHome : UiEffect()
    }
}