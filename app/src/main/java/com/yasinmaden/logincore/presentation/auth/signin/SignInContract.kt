package com.yasinmaden.logincore.presentation.auth.signin

import android.content.Context

object SignInContract {

    data class UiState(
        val email: String = "",
        val password: String = "",
        val resetEmail: String = "",
        val isPasswordVisible: Boolean = false,
        val isResetDialogVisible: Boolean = false,
        val isEmailError: Boolean = false,
        val isPasswordError: Boolean = false,
    )

    sealed class UiAction {
        data class OnEmailChange(val email: String) : UiAction()
        data class OnResetEmailChange(val email: String) : UiAction()
        data class OnPasswordChange(val password: String) : UiAction()
        data class OnGoogleSignInClick(val context: Context) : UiAction()

        data object OnSignInClick : UiAction()
        data object OnPasswordVisibilityToggle : UiAction()
        data object OnSignUpClick : UiAction()
        data object OnForgotPasswordClick : UiAction()
        data object OnResetDialogConfirm : UiAction()
        data object OnResetDialogDismiss : UiAction()
        data object OnResetDialogDismissRequest : UiAction()
    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()

        data object NavigateToHomeScreen : UiEffect()
        data object NavigateToSignUpScreen : UiEffect()
    }
}