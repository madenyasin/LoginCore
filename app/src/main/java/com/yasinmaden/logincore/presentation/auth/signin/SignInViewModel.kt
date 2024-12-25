package com.yasinmaden.logincore.presentation.auth.signin

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasinmaden.logincore.common.Resource
import com.yasinmaden.logincore.domain.repository.AuthRepository
import com.yasinmaden.logincore.presentation.auth.signin.SignInContract.UiAction
import com.yasinmaden.logincore.presentation.auth.signin.SignInContract.UiEffect
import com.yasinmaden.logincore.presentation.auth.signin.SignInContract.UiState
import com.yasinmaden.logincore.presentation.global.ObserveAuthStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val observeAuthStateUseCase: ObserveAuthStateUseCase
) : ViewModel() {


    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect by lazy { _uiEffect.receiveAsFlow() }

    init {
        viewModelScope.launch {
            delay(100)
            observeAuthState()
        }
    }

    fun onAction(uiAction: UiAction) {

        when (uiAction) {

            is UiAction.OnCredentialChange -> handleCredentialChange(uiAction)
            is UiAction.OnGoogleSignInClick -> signInWithGoogle(uiAction.context)

            UiAction.OnSignInClick -> signInWithEmailAndPassword()
            UiAction.OnSignUpClick -> viewModelScope.launch { sendUiEffect(UiEffect.NavigateToSignUpScreen) }
            UiAction.OnResetDialogConfirm -> sendResetPasswordEmail()
            UiAction.OnPasswordVisibilityToggle -> togglePasswordVisibility()
            UiAction.OnForgotPasswordClick -> toggleResetDialogVisibility()
            UiAction.OnResetDialogDismissRequest -> hideResetDialog()
            UiAction.OnResetDialogDismiss -> hideResetDialog()
        }
    }

    private fun observeAuthState() = viewModelScope.launch {

        observeAuthStateUseCase.authStateFlow.collect { isAuth ->
            if (isAuth && authRepository.isUserLoggedIn()) {
                sendUiEffect(UiEffect.NavigateToHomeScreen)
            }
        }
    }

    private fun signInWithEmailAndPassword() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }

        val isEmailError = uiState.value.email.isEmpty()
        val isPasswordError = uiState.value.password.isEmpty()

        if (isEmailError || isPasswordError) {
            updateUiState {
                copy(
                    isEmailError = isEmailError,
                    isPasswordError = isPasswordError,
                    isLoading = false
                )
            }
            return@launch
        }

        try {
            when (val result = authRepository.signInWithEmailAndPassword(
                email = uiState.value.email,
                password = uiState.value.password
            )) {
                is Resource.Success -> {
                    sendUiEffect(UiEffect.NavigateToHomeScreen)
                    sendUiEffect(UiEffect.ShowToast(result.data.uid))
                }

                is Resource.Error -> {
                    sendUiEffect(UiEffect.ShowToast(result.exception.message.toString()))
                }
            }
        } finally {
            updateUiState { copy(isLoading = false) }
        }
    }

    private fun sendResetPasswordEmail() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }

        try {
            when (val result = authRepository.sendResetPasswordEmail(uiState.value.resetEmail)) {
                is Resource.Success -> {
                    hideResetDialog()
                    sendUiEffect(UiEffect.ShowToast(result.data))
                }

                is Resource.Error -> {
                    sendUiEffect(UiEffect.ShowToast(result.exception.message.toString()))
                }
            }
        } finally {
            updateUiState { copy(isLoading = false) }
        }
    }

    private fun signInWithGoogle(context: Context) = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }

        try {
            when (val result = authRepository.signInWithGoogle(activityContext = context)) {
                is Resource.Success -> {
                    sendUiEffect(UiEffect.ShowToast(result.data.uid))
                    sendUiEffect(UiEffect.NavigateToHomeScreen)
                }

                is Resource.Error -> {
                    sendUiEffect(UiEffect.ShowToast(result.exception.message.toString()))
                }
            }
        } finally {
            updateUiState { copy(isLoading = false) }
        }
    }

    private fun handleCredentialChange(uiAction: UiAction.OnCredentialChange) {
        updateUiState {
            copy(
                email = uiAction.email ?: email,
                password = uiAction.password ?: password,
                resetEmail = uiAction.resetEmail ?: resetEmail
            )
        }
    }

    private fun togglePasswordVisibility() {
        updateUiState { copy(isPasswordVisible = !isPasswordVisible) }
    }

    private fun toggleResetDialogVisibility() {
        updateUiState { copy(isResetDialogVisible = !isResetDialogVisible) }
    }

    private fun hideResetDialog() {
        updateUiState { copy(isResetDialogVisible = false) }
    }

    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun sendUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }

}