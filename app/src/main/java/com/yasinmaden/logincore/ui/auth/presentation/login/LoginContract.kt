package com.yasinmaden.logincore.ui.auth.presentation.login

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
        object OnVisibilityChange : UiAction()
        object OnSignUpClick : UiAction()
        object OnForgotPasswordTextClick : UiAction()
        object OnResetPasswordDialogConfirm : UiAction()
        object OnResetPasswordDialogDismiss : UiAction()
        object OnResetPasswordDialogDismissRequest : UiAction()
    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        object NavigateToSignUp : UiEffect()
        object NavigateToHome : UiEffect()
    }
}