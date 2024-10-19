package com.yasinmaden.logincore.ui.login

import androidx.lifecycle.ViewModel
import com.yasinmaden.logincore.ui.login.LoginContract.LoginUiAction
import com.yasinmaden.logincore.ui.login.LoginContract.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(uiAction: LoginUiAction){
        when(uiAction){
            is LoginUiAction.OnEmailChange -> {
                updateUiState {
                    copy(email = uiAction.email)
                }
            }
            is LoginUiAction.OnPasswordChange -> {
                updateUiState {
                    copy(password = uiAction.password)
                }
            }
            LoginUiAction.OnVisibilityChange -> {
                updateUiState {
                    copy(
                        visibility = !_uiState.value.visibility
                    )
                }
            }
        }
    }
    private fun updateUiState(block: LoginUiState.() -> LoginUiState) {
        _uiState.update(block)
    }

}