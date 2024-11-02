package com.yasinmaden.logincore.ui.auth.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasinmaden.logincore.common.Resource
import com.yasinmaden.logincore.ui.auth.presentation.signup.SignupContract.UiAction
import com.yasinmaden.logincore.ui.auth.presentation.signup.SignupContract.UiEffect
import com.yasinmaden.logincore.ui.auth.presentation.signup.SignupContract.UiEffect.OnNavigateToLoginScreen
import com.yasinmaden.logincore.ui.auth.presentation.signup.SignupContract.UiState
import com.yasinmaden.logincore.repository.AuthRepository
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
    private val authRepository: AuthRepository
) : ViewModel() {
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

            is UiAction.OnSignUpClick -> signUp()
        }
    }

    private fun signUp() = viewModelScope.launch {
        when (val result = authRepository.signUp(
            uiState.value.email,
            uiState.value.password,
            uiState.value.confirmPassword,
            uiState.value.name
        )) {
            is Resource.Success -> {
                sendUiEffect(UiEffect.NavigateToHome)
                sendUiEffect(UiEffect.ShowToast(result.data))
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