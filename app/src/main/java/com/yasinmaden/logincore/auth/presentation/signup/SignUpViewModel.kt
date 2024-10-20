package com.yasinmaden.logincore.auth.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpContract.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<SignUpContract.UiEffect>() }
    val uiEffect by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: SignUpContract.UiAction){
        when(uiAction){
            is SignUpContract.UiAction.OnEmailChange -> {
                _uiState.update {
                    it.copy(email = uiAction.email)
                }
            }
            is SignUpContract.UiAction.OnConfirmPasswordChange -> {
                _uiState.update {
                    it.copy(confirmPassword = uiAction.confirmPassword)
                }
            }
            is SignUpContract.UiAction.OnPasswordChange -> {
                _uiState.update {
                    it.copy(password = uiAction.password)
                }
            }
            is SignUpContract.UiAction.OnNameChange -> {
                _uiState.update {
                    it.copy(name = uiAction.name)
                }
            }
            is SignUpContract.UiAction.OnSignUpClick -> {
                // TODO: Implement sign up logic
            }

            SignUpContract.UiAction.OnPasswordVisibilityChange -> {
                _uiState.update {
                    it.copy(passwordVisibility = !_uiState.value.passwordVisibility)
                }
            }
            SignUpContract.UiAction.OnConfirmPasswordVisibilityChange -> {
                _uiState.update {
                    it.copy(confirmPasswordVisibility = !_uiState.value.confirmPasswordVisibility)
                }
            }

            SignUpContract.UiAction.OnSignInTextClick -> viewModelScope.launch{
                _uiEffect.send(SignUpContract.UiEffect.OnNavigateToLoginScreen)
            }
        }
    }
}