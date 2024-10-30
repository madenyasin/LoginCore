package com.yasinmaden.logincore.main.presentation.home

object HomeContract {

    data class UiState(
        val isLoading: Boolean = false,
    )

    sealed class UiAction {

    }

    sealed class UiEffect {

    }
}