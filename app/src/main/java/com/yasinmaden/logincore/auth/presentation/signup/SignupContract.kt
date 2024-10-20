package com.yasinmaden.logincore.auth.presentation.signup

object SignupContract {
    data class UiState(
        val name: String = "",
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val passwordVisibility: Boolean = false,
        val confirmPasswordVisibility: Boolean = false
    )

    sealed class UiAction() {
        data class OnNameChange(val name: String) : UiAction()
        data class OnEmailChange(val email: String) : UiAction()
        data class OnPasswordChange(val password: String) : UiAction()
        data class OnConfirmPasswordChange(val confirmPassword: String) : UiAction()
        data class OnSignUpClick(
            val name: String,
            val email: String,
            val password: String,
            val confirmPassword: String
        ) : UiAction()

        object OnSignInTextClick: UiAction()
        object OnPasswordVisibilityChange : UiAction()
        object OnConfirmPasswordVisibilityChange : UiAction()

    }

    sealed class UiEffect() {
        object OnNavigateToLoginScreen : UiEffect()
    }
}