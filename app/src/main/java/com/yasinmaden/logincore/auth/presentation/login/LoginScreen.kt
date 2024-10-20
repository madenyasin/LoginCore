package com.yasinmaden.logincore.auth.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yasinmaden.logincore.auth.presentation.components.others.AppLogo
import com.yasinmaden.logincore.auth.presentation.components.buttons.LoginButtonSection
import com.yasinmaden.logincore.auth.presentation.components.textfields.LoginEmailField
import com.yasinmaden.logincore.auth.presentation.components.texts.LoginForgotPasswordText
import com.yasinmaden.logincore.auth.presentation.components.textfields.LoginPasswordField
import com.yasinmaden.logincore.auth.presentation.components.others.ResetPasswordDialog
import com.yasinmaden.logincore.auth.presentation.login.LoginContract.LoginUiAction
import com.yasinmaden.logincore.auth.presentation.login.LoginContract.LoginUiState
import com.yasinmaden.logincore.auth.presentation.login.LoginContract.UiEffect
import com.yasinmaden.logincore.ui.theme.LoginCoreTheme
import com.yasinmaden.logincore.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    uiEffect: Flow<UiEffect>,
    onAction: (LoginUiAction) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {

    HandleLoginUiEffects(
        uiEffect = uiEffect,
        navController = navController
    )

    LoginContent(
        uiState = uiState,
        onAction = onAction,
        modifier = modifier
    )
}

@Composable
fun LoginContent(
    uiState: LoginUiState,
    onAction: (LoginUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {

        if (uiState.resetPasswordDialogVisibility) {
            ResetPasswordDialog(uiState = uiState, onAction = onAction)
        }

        AppLogo()
        LoginEmailField(uiState = uiState, onAction = onAction)
        LoginPasswordField(uiState = uiState, onAction = onAction)
        LoginButtonSection(uiState = uiState, onAction = onAction)
        LoginForgotPasswordText(onAction)
    }
}

@Composable
fun HandleLoginUiEffects(
    uiEffect: Flow<UiEffect>,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                UiEffect.NavigateToSignUp -> {
                    navController.navigate(Constants.Destinations.SignUp.route)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    LoginCoreTheme {
        LoginScreen(
            uiState = LoginUiState(),
            onAction = {},
            uiEffect = flow { },
            navController = NavController(LocalContext.current)
        )
    }
}