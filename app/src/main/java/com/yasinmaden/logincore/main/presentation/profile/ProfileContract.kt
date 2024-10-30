package com.yasinmaden.logincore.main.presentation.profile

object ProfileContract {
    data class UiState(
        val isLoading: Boolean = false,
    )

    sealed class UiAction {
        object Logout : UiAction()
    }

    sealed class UiEffect {
        object NavigateToLogin : UiEffect()
    }
}