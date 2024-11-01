package com.yasinmaden.logincore.ui.main.presentation.home

object HomeContract {

    data class UiState(
        val isLoading: Boolean = false,
    )

    sealed class UiAction {

    }

    sealed class UiEffect {

    }
}