package com.yasinmaden.logincore.presentation.auth.signin

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasinmaden.logincore.common.Resource
import com.yasinmaden.logincore.domain.repository.AuthRepository
import com.yasinmaden.logincore.presentation.auth.signin.SignInContract.UiAction
import com.yasinmaden.logincore.presentation.auth.signin.SignInContract.UiState
import com.yasinmaden.logincore.presentation.auth.signin.SignInContract.UiEffect
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

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    fun onAction(uiAction: UiAction) {
        when (uiAction) {

            is UiAction.OnEmailChange -> updateUiState { copy(email = uiAction.email) }

            is UiAction.OnPasswordChange -> updateUiState { copy(password = uiAction.password) }

            UiAction.OnPasswordVisibilityToggle -> updateUiState { copy(isPasswordVisible = !_uiState.value.isPasswordVisible) }

            UiAction.OnSignUpClick -> viewModelScope.launch { sendUiEffect(UiEffect.NavigateToSignUpScreen) }

            UiAction.OnForgotPasswordClick -> updateUiState {
                copy(
                    isResetDialogVisible = !_uiState.value.isResetDialogVisible
                )
            }

            UiAction.OnResetDialogDismiss -> updateUiState {
                copy(
                    isResetDialogVisible = false
                )
            }

            UiAction.OnResetDialogDismissRequest -> updateUiState {
                copy(
                    isResetDialogVisible = false
                )
            }

            is UiAction.OnResetEmailChange -> updateUiState { copy(resetEmail = uiAction.email) }
            UiAction.OnResetDialogConfirm -> sendResetPasswordEmail()

            is UiAction.OnSignInClick -> signInWithEmailAndPassword()
            is UiAction.OnGoogleSignInClick -> signInWithGoogle(uiAction.context)
        }
    }

    init {
        Log.d("debugs", "SignInViewModel initialized")
        _isAuthenticated.value = observeAuthStateUseCase.authStateFlow.value
        Log.d("debugs", "auth state: ${observeAuthStateUseCase.authStateFlow.value}")
        Log.d("debugs", "isUserLoggedIn(): ${authRepository.isUserLoggedIn()}")

        viewModelScope.launch {
            delay(1000) // 1 saniye bekle
            // Gecikmeden sonra yapılacak işlemler
            isUserLoggedIn()
            Log.d("debugs", "auth state: ${observeAuthStateUseCase.authStateFlow.value}")
            Log.d("debugs", "isUserLoggedIn(): ${authRepository.isUserLoggedIn()}")
        }

    }

    private fun isUserLoggedIn() = viewModelScope.launch {
        if (isAuthenticated.value and authRepository.isUserLoggedIn())
            sendUiEffect(UiEffect.NavigateToHomeScreen)
    }

    private fun signInWithEmailAndPassword() = viewModelScope.launch {
        updateUiState {
            copy(
                isEmailError = uiState.value.email.isEmpty(),
                isPasswordError = uiState.value.password.isEmpty()
            )
        }
        if (uiState.value.isEmailError || uiState.value.isPasswordError)
            return@launch

        when (val result = authRepository.signInWithEmailAndPassword(uiState.value.email, uiState.value.password)) {
            is Resource.Success -> {
                sendUiEffect(UiEffect.NavigateToHomeScreen)
                sendUiEffect(UiEffect.ShowToast(result.data.uid))
            }

            is Resource.Error -> {
                sendUiEffect(UiEffect.ShowToast(result.exception.message.toString()))
            }
        }
    }

    private fun sendResetPasswordEmail() = viewModelScope.launch {
        when (val result =
            authRepository.sendResetPasswordEmail(uiState.value.resetEmail)) {
            is Resource.Success -> {
                updateUiState { copy(isResetDialogVisible = false) }
                sendUiEffect(UiEffect.ShowToast(result.data))
            }

            is Resource.Error -> {
                sendUiEffect(UiEffect.ShowToast(result.exception.message.toString()))
            }
        }
    }

    private fun signInWithGoogle(context: Context) = viewModelScope.launch {
        when (val result = authRepository.signInWithGoogle(activityContext = context)) {
            is Resource.Success -> {
                sendUiEffect(UiEffect.ShowToast(result.data.uid))
                sendUiEffect(UiEffect.NavigateToHomeScreen)
            }

            is Resource.Error -> {
                sendUiEffect(UiEffect.ShowToast(result.exception.message.toString()))
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