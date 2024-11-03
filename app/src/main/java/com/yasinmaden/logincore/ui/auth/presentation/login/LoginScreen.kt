package com.yasinmaden.logincore.ui.auth.presentation.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yasinmaden.logincore.R
import com.yasinmaden.logincore.navigation.Screen
import com.yasinmaden.logincore.ui.auth.presentation.components.buttons.LoginButtonSection
import com.yasinmaden.logincore.ui.auth.presentation.components.others.AppLogo
import com.yasinmaden.logincore.ui.auth.presentation.components.others.ResetPasswordDialog
import com.yasinmaden.logincore.ui.auth.presentation.components.textfields.LoginEmailField
import com.yasinmaden.logincore.ui.auth.presentation.components.textfields.LoginPasswordField
import com.yasinmaden.logincore.ui.auth.presentation.components.texts.LoginForgotPasswordText
import com.yasinmaden.logincore.ui.auth.presentation.login.LoginContract.UiAction
import com.yasinmaden.logincore.ui.auth.presentation.login.LoginContract.UiEffect
import com.yasinmaden.logincore.ui.auth.presentation.login.LoginContract.UiState
import com.yasinmaden.logincore.ui.theme.LoginCoreTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun LoginScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
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
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
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
        HorizontalDivider()
        SocialLoginSection(onAction)
    }
}

@Composable
fun SocialLoginSection(onAction: (UiAction) -> Unit) {
    val context = LocalContext.current
    OutlinedButton(
        onClick = { onAction(UiAction.OnGoogleSignInClick(context)) },
        modifier = Modifier.height(48.dp)
    )
    {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.google_logo),
            contentDescription = "Google Logo",
            tint = Color.Unspecified,
        )
        Spacer(Modifier.width(8.dp))
        Text("Sign in with Google")

    }
}

@Composable
fun HandleLoginUiEffects(
    uiEffect: Flow<UiEffect>,
    navController: NavController
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                UiEffect.NavigateToSignUp -> {
                    navController.navigate(Screen.Signup.route)
                }

                UiEffect.NavigateToHome -> {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                }

                is UiEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
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
            uiState = UiState(),
            onAction = {},
            uiEffect = flow { },
            navController = NavController(LocalContext.current)
        )
    }
}