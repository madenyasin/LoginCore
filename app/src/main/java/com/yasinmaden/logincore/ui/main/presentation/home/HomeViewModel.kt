package com.yasinmaden.logincore.ui.main.presentation.home

import androidx.lifecycle.ViewModel
import com.yasinmaden.logincore.ui.main.presentation.home.HomeContract.UiEffect
import com.yasinmaden.logincore.ui.main.presentation.home.HomeContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: HomeContract.UiAction) {
        when (uiAction) {

            else -> {}
        }
    }

    private fun updateUiState(block: HomeContract.UiState.() -> HomeContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun sendUiEffect(uiEffect: HomeContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }
}