package com.yasinmaden.logincore.presentation.main.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsContract.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<SettingsContract.UiEffect>() }
    val uiEffect by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: SettingsContract.UiAction) {
        when (uiAction) {

            else -> {}
        }
    }

    private fun updateUiState(block: SettingsContract.UiState.() -> SettingsContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun sendUiEffect(uiEffect: SettingsContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }
}