package com.yasinmaden.logincore.presentation.main.profile

object ProfileContract {
    data class UiState(
        val isLoading: Boolean = false,
        val name: String = "",
        val email: String = "",
        val phoneNumber: String = "",
        val profileImageUrl: String = ""
    )

    sealed class UiAction {
        data object SignOut : UiAction()
        data object LoadProfile : UiAction()
        data object OnEditProfilePictureClick : UiAction()

    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        data object NavigateToSignInScreen : UiEffect()
    }
}