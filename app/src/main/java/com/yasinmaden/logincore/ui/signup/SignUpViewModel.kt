package com.yasinmaden.logincore.ui.signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class SignUpViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpContract.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<SignUpContract.UiEffect>() }
    val uiEffect by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: SignUpContract.UiAction){
        when(uiAction){
            is SignUpContract.UiAction.EmailChanged -> TODO()
        }
    }
}