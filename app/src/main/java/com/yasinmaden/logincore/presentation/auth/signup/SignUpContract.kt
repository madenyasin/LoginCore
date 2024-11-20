package com.yasinmaden.logincore.presentation.auth.signup

object SignUpContract {
    data class UiState(
        val name: String = "",
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val isPasswordVisible: Boolean = false,
        val isConfirmPasswordVisible: Boolean = false,
        val isNameError: Boolean = false,
        val isEmailError: Boolean = false,
        val isPasswordError: Boolean = false,
        val isConfirmPasswordError: Boolean = false,

        )

    sealed class UiAction {
        data class OnNameChange(val name: String) : UiAction()
        data class OnEmailChange(val email: String) : UiAction()
        data class OnPasswordChange(val password: String) : UiAction()
        data class OnConfirmPasswordChange(val confirmPassword: String) : UiAction()

        data object OnSignUpClick : UiAction()
        data object OnSignInClick: UiAction()
        data object OnPasswordVisibilityToggle : UiAction()
        data object OnConfirmPasswordVisibilityToggle : UiAction()

    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        data object NavigateToHomeScreen : UiEffect()
        data object NavigateToSignInScreen : UiEffect()
    }
}