package com.yasinmaden.logincore.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasinmaden.logincore.auth.presentation.login.LoginContract.LoginUiAction
import com.yasinmaden.logincore.auth.presentation.login.LoginContract.LoginUiState
import com.yasinmaden.logincore.auth.presentation.login.LoginContract.UiEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: LoginUiAction) {
        when (uiAction) {
            is LoginUiAction.OnEmailChange -> {
                updateUiState {
                    copy(email = uiAction.email)
                }
            }
            is LoginUiAction.OnPasswordChange -> {
                updateUiState {
                    copy(password = uiAction.password)
                }
            }
            LoginUiAction.OnVisibilityChange -> {
                updateUiState {
                    copy(
                        passwordVisibility = !_uiState.value.passwordVisibility
                    )
                }
            }
            is LoginUiAction.OnLoginClick -> {
                // todo: implement login logic
            }
            LoginUiAction.OnSignUpClick -> viewModelScope.launch {
                sendUiEffect(UiEffect.NavigateToSignUp)
            }

            LoginUiAction.OnForgotPasswordTextClick -> {
                _uiState.update {
                    it.copy(
                        resetPasswordDialogVisibility = !_uiState.value.resetPasswordDialogVisibility
                    )
                }
            }

            LoginUiAction.OnResetPasswordDialogConfirm -> {
                // todo: implement send reset password logic
            }
            LoginUiAction.OnResetPasswordDialogDismiss -> {
                _uiState.update {
                    it.copy(
                        resetPasswordDialogVisibility = false
                    )
                }
            }
            LoginUiAction.OnResetPasswordDialogDismissRequest -> {
                _uiState.update {
                    it.copy(
                        resetPasswordDialogVisibility = false
                    )
                }
            }
            is LoginUiAction.OnResetPasswordEmailChange -> {
                _uiState.update {
                    it.copy(
                        resetPasswordEmail = uiAction.email
                    )
                }
            }
        }
    }

    private fun updateUiState(block: LoginUiState.() -> LoginUiState) {
        _uiState.update(block)
    }

    private suspend fun sendUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }

}