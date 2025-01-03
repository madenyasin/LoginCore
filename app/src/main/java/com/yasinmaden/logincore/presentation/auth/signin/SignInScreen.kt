package com.yasinmaden.logincore.presentation.auth.signin

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
import com.yasinmaden.logincore.presentation.navigation.Screen
import com.yasinmaden.logincore.presentation.auth.components.buttons.SignInButtonSection
import com.yasinmaden.logincore.presentation.auth.components.others.AppLogo
import com.yasinmaden.logincore.presentation.auth.components.others.ResetPasswordDialog
import com.yasinmaden.logincore.presentation.auth.components.textfields.SignInEmailField
import com.yasinmaden.logincore.presentation.auth.components.textfields.SignInPasswordField
import com.yasinmaden.logincore.presentation.auth.components.texts.SignInForgotPasswordText
import com.yasinmaden.logincore.presentation.auth.signin.SignInContract.UiAction
import com.yasinmaden.logincore.presentation.auth.signin.SignInContract.UiEffect
import com.yasinmaden.logincore.presentation.auth.signin.SignInContract.UiState
import com.yasinmaden.logincore.theme.LoginCoreTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun SignInScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {

    HandleSignInUiEffects(
        uiEffect = uiEffect,
        navController = navController
    )

    SignInContent(
        uiState = uiState,
        onAction = onAction,
        modifier = modifier
    )
}

@Composable
fun SignInContent(
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

        if (uiState.isResetDialogVisible) {
            ResetPasswordDialog(uiState = uiState, onAction = onAction)
        }

        AppLogo()
        SignInEmailField(uiState = uiState, onAction = onAction)
        SignInPasswordField(uiState = uiState, onAction = onAction)
        SignInButtonSection(uiState = uiState, onAction = onAction)
        SignInForgotPasswordText(onAction)
        HorizontalDivider()
        SocialSignInSection(onAction)
    }
}

@Composable
fun SocialSignInSection(onAction: (UiAction) -> Unit) {
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
fun HandleSignInUiEffects(
    uiEffect: Flow<UiEffect>,
    navController: NavController
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                UiEffect.NavigateToSignUpScreen -> {
                    navController.navigate(Screen.SignUp.route)
                }

                UiEffect.NavigateToHomeScreen -> {
                    navController.navigate(Screen.Profile.route) {
                        popUpTo(Screen.SignIn.route) {
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
fun SignInScreenPreview() {
    LoginCoreTheme {
        SignInScreen(
            uiState = UiState(),
            onAction = {},
            uiEffect = flow { },
            navController = NavController(LocalContext.current)
        )
    }
}