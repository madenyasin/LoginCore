package com.yasinmaden.logincore.ui.auth.presentation.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yasinmaden.logincore.R
import com.yasinmaden.logincore.ui.auth.presentation.components.buttons.SignupButtonSection
import com.yasinmaden.logincore.ui.auth.presentation.components.others.AppLogo
import com.yasinmaden.logincore.ui.auth.presentation.components.textfields.SignupConfirmPasswordField
import com.yasinmaden.logincore.ui.auth.presentation.components.textfields.SignupEmailField
import com.yasinmaden.logincore.ui.auth.presentation.components.textfields.SignupNameField
import com.yasinmaden.logincore.ui.auth.presentation.components.textfields.SignupPasswordField
import com.yasinmaden.logincore.ui.auth.presentation.components.texts.SignupHaveAnAccountText
import com.yasinmaden.logincore.navigation.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun SignupScreen(
    uiState: SignupContract.UiState,
    uiEffect: Flow<SignupContract.UiEffect>,
    onAction: (SignupContract.UiAction) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
) {

    HandleSignupUiEffect(
        uiEffect = uiEffect,
        navController = navController
    )

    SignupContent(
        uiState = uiState,
        onAction = onAction,
        modifier = modifier
    )
}

@Composable
fun HandleSignupUiEffect(
    uiEffect: Flow<SignupContract.UiEffect>,
    navController: NavController,
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                SignupContract.UiEffect.OnNavigateToLoginScreen -> {
                    navController.navigate(Screen.Login.route) {
                        launchSingleTop = true
                        popUpTo(Screen.Signup.route) {
                            inclusive = true
                        }
                    }
                }

                SignupContract.UiEffect.NavigateToHome -> {
                    navController.navigate(Screen.Profile.route){
                        popUpTo(Screen.Login.route){
                            inclusive = true
                        }
                    }
                }
                is SignupContract.UiEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}


@Composable
fun SignupContent(
    uiState: SignupContract.UiState,
    onAction: (SignupContract.UiAction) -> Unit,
    modifier: Modifier = Modifier,
) {

    val passwordVisibilityIcon = if (uiState.passwordVisibility) {
        ImageVector.vectorResource(R.drawable.ic_visibility_on)
    } else {
        ImageVector.vectorResource(R.drawable.ic_visibility_off)
    }

    val confirmPasswordVisibilityIcon = if (uiState.confirmPasswordVisibility) {
        ImageVector.vectorResource(R.drawable.ic_visibility_on)
    } else {
        ImageVector.vectorResource(R.drawable.ic_visibility_off)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 64.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
    ) {

        AppLogo()
        SignupNameField(uiState = uiState, onAction = onAction)
        SignupEmailField(uiState = uiState, onAction = onAction)
        SignupPasswordField(
            uiState = uiState,
            onAction = onAction,
            trailingIcon = passwordVisibilityIcon
        )
        SignupConfirmPasswordField(
            uiState = uiState,
            onAction = onAction,
            trailingIcon = confirmPasswordVisibilityIcon
        )
        SignupButtonSection(
            uiState = uiState,
            onAction = onAction
        )
        SignupHaveAnAccountText(onAction = onAction)

    }
}

@Composable
@Preview(showBackground = true)
fun SignupScreenPreview() {
    SignupScreen(
        uiState = SignupContract.UiState(),
        uiEffect = flow { },
        onAction = {},
        navController = NavController(LocalContext.current)
    )
}