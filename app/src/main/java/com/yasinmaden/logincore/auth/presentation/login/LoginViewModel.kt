package com.yasinmaden.logincore.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasinmaden.logincore.auth.presentation.login.LoginContract.UiAction
import com.yasinmaden.logincore.auth.presentation.login.LoginContract.UiState
import com.yasinmaden.logincore.auth.presentation.login.LoginContract.UiEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {

            is UiAction.OnEmailChange -> updateUiState { copy(email = uiAction.email) }

            is UiAction.OnPasswordChange -> updateUiState { copy(password = uiAction.password) }

            UiAction.OnVisibilityChange -> updateUiState { copy(passwordVisibility = !_uiState.value.passwordVisibility) }

            UiAction.OnSignUpClick -> viewModelScope.launch { sendUiEffect(UiEffect.NavigateToSignUp) }

            UiAction.OnForgotPasswordTextClick -> updateUiState {
                copy(
                    resetPasswordDialogVisibility = !_uiState.value.resetPasswordDialogVisibility
                )
            }

            UiAction.OnResetPasswordDialogDismiss -> updateUiState { copy(resetPasswordDialogVisibility = false) }

            UiAction.OnResetPasswordDialogDismissRequest -> updateUiState { copy(resetPasswordDialogVisibility = false) }

            is UiAction.OnResetPasswordEmailChange -> updateUiState { copy(resetPasswordEmail = uiAction.email) }
            UiAction.OnResetPasswordDialogConfirm -> {
                // todo: implement send reset password logic
            }

            is UiAction.OnLoginClick -> {
                // todo: implement login logic
            }
        }
    }

    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun sendUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }

}