package com.yasinmaden.logincore.presentation.main.home

object HomeContract {

    data class UiState(
        val isLoading: Boolean = false,
    )

    sealed class UiAction {

    }

    sealed class UiEffect {

    }
}