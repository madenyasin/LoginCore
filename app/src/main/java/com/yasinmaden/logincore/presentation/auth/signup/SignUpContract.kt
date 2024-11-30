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
        data object OnSignUpClick : UiAction()
        data object OnSignInClick : UiAction()
        data object OnPasswordVisibilityToggle : UiAction()
        data object OnConfirmPasswordVisibilityToggle : UiAction()

        data class OnFormChange(
            val name: String? = null,
            val email: String? = null,
            val password: String? = null,
            val confirmPassword: String? = null
        ) : UiAction()

    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        data object NavigateToHomeScreen : UiEffect()
        data object NavigateToSignInScreen : UiEffect()
    }
}