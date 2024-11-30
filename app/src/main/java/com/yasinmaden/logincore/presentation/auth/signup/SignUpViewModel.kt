package com.yasinmaden.logincore.presentation.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasinmaden.logincore.common.Resource
import com.yasinmaden.logincore.domain.repository.AuthRepository
import com.yasinmaden.logincore.presentation.auth.signup.SignUpContract.UiAction
import com.yasinmaden.logincore.presentation.auth.signup.SignUpContract.UiEffect
import com.yasinmaden.logincore.presentation.auth.signup.SignUpContract.UiEffect.NavigateToSignInScreen
import com.yasinmaden.logincore.presentation.auth.signup.SignUpContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {

            UiAction.OnSignInClick -> navigateToSignIn()
            UiAction.OnPasswordVisibilityToggle -> togglePasswordVisibility()
            UiAction.OnConfirmPasswordVisibilityToggle -> toggleConfirmPasswordVisibility()

            is UiAction.OnSignUpClick -> signUp()
            is UiAction.OnFormChange -> updateForm(uiAction)
        }
    }


    private fun togglePasswordVisibility() {
        updateUiState {
            copy(
                isPasswordVisible = !isPasswordVisible
            )
        }
    }

    private fun toggleConfirmPasswordVisibility() {
        updateUiState {
            copy(
                isConfirmPasswordVisible = !isConfirmPasswordVisible
            )
        }
    }

    private fun navigateToSignIn() {
        viewModelScope.launch {
            sendUiEffect(NavigateToSignInScreen)
        }
    }

    private fun updateForm(uiAction: UiAction.OnFormChange) {
        updateUiState {
            copy(
                name = uiAction.name ?: name,
                email = uiAction.email ?: email,
                password = uiAction.password ?: password,
                confirmPassword = uiAction.confirmPassword ?: confirmPassword
            )
        }
    }

    private fun signUp() = viewModelScope.launch {

        val isNameError = uiState.value.name.isEmpty()
        val isEmailError = uiState.value.email.isEmpty()
        val isPasswordError = uiState.value.password.isEmpty()
        val isConfirmPasswordError = uiState.value.confirmPassword.isEmpty()

        updateUiState {
            copy(
                isNameError = isNameError,
                isEmailError = isEmailError,
                isPasswordError = isPasswordError,
                isConfirmPasswordError = isConfirmPasswordError,
            )
        }

        if (uiState.value.isNameError || uiState.value.isEmailError || uiState.value.isPasswordError || uiState.value.isConfirmPasswordError) {
            return@launch
        }

        when (val result = authRepository.signUpWithEmailAndPassword(
            uiState.value.email,
            uiState.value.password,
            uiState.value.confirmPassword,
            uiState.value.name
        )) {
            is Resource.Success -> {
                sendUiEffect(UiEffect.NavigateToHomeScreen)
                sendUiEffect(UiEffect.ShowToast(result.data.uid))
            }

            is Resource.Error -> sendUiEffect(UiEffect.ShowToast(result.exception.message.toString()))
        }

    }


    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun sendUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }

}