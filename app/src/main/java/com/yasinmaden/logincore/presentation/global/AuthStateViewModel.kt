package com.yasinmaden.logincore.presentation.global

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthStateViewModel @Inject constructor(
    observeAuthStateUseCase: ObserveAuthStateUseCase
) : ViewModel() {
    val authState = observeAuthStateUseCase.authStateFlow
}