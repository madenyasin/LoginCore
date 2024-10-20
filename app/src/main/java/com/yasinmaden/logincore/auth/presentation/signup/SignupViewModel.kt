package com.yasinmaden.logincore.auth.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignupContract.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<SignupContract.UiEffect>() }
    val uiEffect by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: SignupContract.UiAction){
        when(uiAction){
            is SignupContract.UiAction.OnEmailChange -> {
                _uiState.update {
                    it.copy(email = uiAction.email)
                }
            }
            is SignupContract.UiAction.OnConfirmPasswordChange -> {
                _uiState.update {
                    it.copy(confirmPassword = uiAction.confirmPassword)
                }
            }
            is SignupContract.UiAction.OnPasswordChange -> {
                _uiState.update {
                    it.copy(password = uiAction.password)
                }
            }
            is SignupContract.UiAction.OnNameChange -> {
                _uiState.update {
                    it.copy(name = uiAction.name)
                }
            }
            is SignupContract.UiAction.OnSignUpClick -> {
                // TODO: Implement sign up logic
            }

            SignupContract.UiAction.OnPasswordVisibilityChange -> {
                _uiState.update {
                    it.copy(passwordVisibility = !_uiState.value.passwordVisibility)
                }
            }
            SignupContract.UiAction.OnConfirmPasswordVisibilityChange -> {
                _uiState.update {
                    it.copy(confirmPasswordVisibility = !_uiState.value.confirmPasswordVisibility)
                }
            }

            SignupContract.UiAction.OnSignInTextClick -> viewModelScope.launch{
                _uiEffect.send(SignupContract.UiEffect.OnNavigateToLoginScreen)
            }
        }
    }
}