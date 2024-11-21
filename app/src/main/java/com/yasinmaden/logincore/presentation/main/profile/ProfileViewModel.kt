package com.yasinmaden.logincore.presentation.main.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.yasinmaden.logincore.common.Resource
import com.yasinmaden.logincore.domain.repository.AuthRepository
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
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val auth: FirebaseAuth,
    private val observeAuthStateUseCase: ObserveAuthStateUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileContract.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<ProfileContract.UiEffect>() }
    val uiEffect by lazy { _uiEffect.receiveAsFlow() }

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    fun onAction(uiAction: ProfileContract.UiAction) {
        when (uiAction) {
            ProfileContract.UiAction.SignOut -> signOut()
            ProfileContract.UiAction.LoadProfile -> loadUiState()
            ProfileContract.UiAction.OnEditProfilePictureClick -> {
                //TODO: Edit profile picture
            }
        }
    }

    init {
        Log.d("debugs", "ProfileViewModel initialized")
        _isAuthenticated.value = observeAuthStateUseCase.authStateFlow.value
        Log.d("debugs", "auth state: ${observeAuthStateUseCase.authStateFlow.value}")
        Log.d("debugs", "isUserLoggedIn(): ${authRepository.isUserLoggedIn()}")


        viewModelScope.launch {
            delay(1000) // 1 saniye bekle
            // Gecikmeden sonra yapılacak işlemler
            isUserAuthenticated()
            Log.d("debugs", "auth state: ${observeAuthStateUseCase.authStateFlow.value}")
            Log.d("debugs", "isUserLoggedIn(): ${authRepository.isUserLoggedIn()}")


        }

    }
    private fun isUserAuthenticated() {
        if(!isAuthenticated.value and !authRepository.isUserLoggedIn()){
            viewModelScope.launch {
                sendUiEffect(ProfileContract.UiEffect.NavigateToSignInScreen)
            }
        }
    }
    private fun loadUiState() {
        updateUiState {
            copy(
                name = auth.currentUser?.displayName.toString(),
                email = auth.currentUser?.email.toString(),
                profileImageUrl = auth.currentUser?.photoUrl.toString(),
                phoneNumber = auth.currentUser?.phoneNumber.toString()
            )
        }
    }

    private fun signOut() = viewModelScope.launch {
        when (val result = authRepository.signOut()) {
            is Resource.Success -> {
                sendUiEffect(ProfileContract.UiEffect.NavigateToSignInScreen)
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