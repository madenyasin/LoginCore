package com.yasinmaden.logincore.ui.main.presentation.profile

object ProfileContract {
    data class UiState(
        val isLoading: Boolean = false,
    )

    sealed class UiAction {
        object Logout : UiAction()

    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        object NavigateToLogin : UiEffect()
    }
}