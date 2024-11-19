package com.yasinmaden.logincore.presentation.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.yasinmaden.logincore.common.Resource
import com.yasinmaden.logincore.domain.repository.AuthRepository
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
    private val authRepository: AuthRepository,
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileContract.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<ProfileContract.UiEffect>() }
    val uiEffect by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: ProfileContract.UiAction) {
        when (uiAction) {
            ProfileContract.UiAction.Logout -> logout()
            ProfileContract.UiAction.LoadProfile -> loadUiState()
            ProfileContract.UiAction.EditProfilePicture -> {
                //TODO: Edit profile picture
            }
        }
    }

    private fun loadUiState() {
        updateUiState {
            copy(
                name = auth.currentUser?.displayName.toString(),
                email = auth.currentUser?.email.toString(),
                imageUrl = auth.currentUser?.photoUrl.toString(),
                phoneNumber = auth.currentUser?.phoneNumber.toString()
            )
        }
    }

    private fun logout() = viewModelScope.launch {
        when (val result = authRepository.logout()) {
            is Resource.Success -> {
                sendUiEffect(ProfileContract.UiEffect.NavigateToLogin)
                sendUiEffect(ProfileContract.UiEffect.ShowToast(result.data))
            }

            is Resource.Error -> {
                sendUiEffect(ProfileContract.UiEffect.ShowToast(result.exception.message.toString()))
            }
        }
    }

    private fun updateUiState(block: ProfileContract.UiState.() -> ProfileContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun sendUiEffect(uiEffect: ProfileContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }
}