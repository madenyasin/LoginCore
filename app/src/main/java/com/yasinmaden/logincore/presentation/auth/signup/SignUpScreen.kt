package com.yasinmaden.logincore.presentation.auth.signup

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
import com.yasinmaden.logincore.presentation.auth.components.buttons.SignupButtonSection
import com.yasinmaden.logincore.presentation.auth.components.others.AppLogo
import com.yasinmaden.logincore.presentation.auth.components.textfields.SignupConfirmPasswordField
import com.yasinmaden.logincore.presentation.auth.components.textfields.SignupEmailField
import com.yasinmaden.logincore.presentation.auth.components.textfields.SignupNameField
import com.yasinmaden.logincore.presentation.auth.components.textfields.SignupPasswordField
import com.yasinmaden.logincore.presentation.auth.components.texts.SignupHaveAnAccountText
import com.yasinmaden.logincore.presentation.navigation.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun SignupScreen(
    uiState: SignUpContract.UiState,
    uiEffect: Flow<SignUpContract.UiEffect>,
    onAction: (SignUpContract.UiAction) -> Unit,
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
    uiEffect: Flow<SignUpContract.UiEffect>,
    navController: NavController,
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                SignUpContract.UiEffect.NavigateToSignInScreen -> {
                    navController.navigate(Screen.SignIn.route) {
                        launchSingleTop = true
                        popUpTo(Screen.SignUp.route) {
                            inclusive = true
                        }
                    }
                }

                SignUpContract.UiEffect.NavigateToHomeScreen -> {
                    navController.navigate(Screen.Profile.route){
                        popUpTo(Screen.SignIn.route){
                            inclusive = true
                        }
                    }
                }
                is SignUpContract.UiEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}


@Composable
fun SignupContent(
    uiState: SignUpContract.UiState,
    onAction: (SignUpContract.UiAction) -> Unit,
    modifier: Modifier = Modifier,
) {

    val passwordVisibilityIcon = if (uiState.isPasswordVisible) {
        ImageVector.vectorResource(R.drawable.ic_visibility_on)
    } else {
        ImageVector.vectorResource(R.drawable.ic_visibility_off)
    }

    val confirmPasswordVisibilityIcon = if (uiState.isConfirmPasswordVisible) {
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
        uiState = SignUpContract.UiState(),
        uiEffect = flow { },
        onAction = {},
        navController = NavController(LocalContext.current)
    )
}