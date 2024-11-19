package com.yasinmaden.logincore.presentation.auth.signup

object SignupContract {
    data class UiState(
        val name: String = "",
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val passwordVisibility: Boolean = false,
        val confirmPasswordVisibility: Boolean = false,
        val isNameError: Boolean = false,
        val isEmailError: Boolean = false,
        val isPasswordError: Boolean = false,
        val isConfirmPasswordError: Boolean = false,

    )

    sealed class UiAction() {
        data class OnNameChange(val name: String) : UiAction()
        data class OnEmailChange(val email: String) : UiAction()
        data class OnPasswordChange(val password: String) : UiAction()
        data class OnConfirmPasswordChange(val confirmPassword: String) : UiAction()

        data object OnSignUpClick : UiAction()
        data object OnSignInTextClick: UiAction()
        data object OnPasswordVisibilityChange : UiAction()
        data object OnConfirmPasswordVisibilityChange : UiAction()

    }

    sealed class UiEffect() {
        data class ShowToast(val message: String) : UiEffect()
        data object NavigateToHome : UiEffect()
        data object OnNavigateToLoginScreen : UiEffect()
    }
}