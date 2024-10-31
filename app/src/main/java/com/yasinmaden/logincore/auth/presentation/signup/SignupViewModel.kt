package com.yasinmaden.logincore.auth.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasinmaden.logincore.auth.presentation.signup.SignupContract.UiAction
import com.yasinmaden.logincore.auth.presentation.signup.SignupContract.UiEffect
import com.yasinmaden.logincore.auth.presentation.signup.SignupContract.UiEffect.OnNavigateToLoginScreen
import com.yasinmaden.logincore.auth.presentation.signup.SignupContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(

): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {

            is UiAction.OnEmailChange -> updateUiState { copy(email = uiAction.email) }


            is UiAction.OnPasswordChange -> updateUiState { copy(password = uiAction.password) }

            is UiAction.OnNameChange -> updateUiState { copy(name = uiAction.name) }


            is UiAction.OnConfirmPasswordChange -> updateUiState {
                copy(
                    confirmPassword = uiAction.confirmPassword
                )
            }

            UiAction.OnPasswordVisibilityChange -> updateUiState {
                copy(
                    passwordVisibility = !_uiState.value.passwordVisibility
                )
            }

            UiAction.OnConfirmPasswordVisibilityChange -> updateUiState {
                copy(
                    confirmPasswordVisibility = !_uiState.value.confirmPasswordVisibility
                )
            }

            UiAction.OnSignInTextClick -> viewModelScope.launch {
                sendUiEffect(OnNavigateToLoginScreen)
            }

            is UiAction.OnSignUpClick -> {
                // TODO: Implement sign up logic
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