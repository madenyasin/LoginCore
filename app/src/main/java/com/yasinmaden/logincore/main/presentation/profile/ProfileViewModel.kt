package com.yasinmaden.logincore.main.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileContract.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<ProfileContract.UiEffect>() }
    val uiEffect by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: ProfileContract.UiAction) {
        when (uiAction) {
            ProfileContract.UiAction.Logout -> logout()
        }
    }

    private fun logout() = viewModelScope.launch {
        authRepository.logout()
        sendUiEffect(ProfileContract.UiEffect.NavigateToLogin)
    }

    private fun updateUiState(block: ProfileContract.UiState.() -> ProfileContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun sendUiEffect(uiEffect: ProfileContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }
}