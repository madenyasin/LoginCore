package com.yasinmaden.logincore.ui.signup

object SignUpContract {
    data class UiState(
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
    )

    sealed class UiAction(){
        data class EmailChanged(val email: String): UiAction()

    }

    sealed class UiEffect()
}