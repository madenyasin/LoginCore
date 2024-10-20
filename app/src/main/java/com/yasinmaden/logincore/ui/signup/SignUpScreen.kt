package com.yasinmaden.logincore.ui.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yasinmaden.logincore.R
import com.yasinmaden.logincore.navigation.Destinations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun SignUpScreen(
    uiState: SignUpContract.UiState,
    uiEffect: Flow<SignUpContract.UiEffect>,
    onAction: (SignUpContract.UiAction) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
) {

    HandleSignUpUiEffect(
        uiEffect = uiEffect,
        navController = navController
    )

    SignUpContent(
        uiState = uiState,
        onAction = onAction,
        modifier = modifier
    )
}

@Composable
fun HandleSignUpUiEffect(
    uiEffect: Flow<SignUpContract.UiEffect>,
    navController: NavController,
) {
    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                SignUpContract.UiEffect.OnNavigateToLoginScreen -> {
                    navController.navigate(Destinations.Login.route) {
                        launchSingleTop = true
                        popUpTo(Destinations.SignUp.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SignUpContent(
    uiState: SignUpContract.UiState,
    onAction: (SignUpContract.UiAction) -> Unit,
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
            .padding(top = 64.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_home_app_logo),
            contentDescription = "Home App Logo",
            modifier = Modifier.size(125.dp)
        )


        OutlinedTextField(
            value = uiState.name,
            onValueChange = { onAction(SignUpContract.UiAction.OnNameChange(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            label = { Text("Name") },
            placeholder = { Text("Name") },
            supportingText = { Text("Enter your name") },
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = "Person icon")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = uiState.email,
            onValueChange = { onAction(SignUpContract.UiAction.OnEmailChange(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            label = { Text("Email") },
            placeholder = { Text("Email") },
            supportingText = { Text("Enter your email address") },
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = "Email icon")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = uiState.password,
            onValueChange = { onAction(SignUpContract.UiAction.OnPasswordChange(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            label = { Text("Password") },
            placeholder = { Text("Password") },
            supportingText = { Text("Enter your password") },
            singleLine = true,
            visualTransformation = if (uiState.passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_password),
                    contentDescription = "Password icon"
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { onAction(SignUpContract.UiAction.OnPasswordVisibilityChange) }
                ) {
                    Icon(
                        imageVector = passwordVisibilityIcon,
                        contentDescription = "Visibility icon"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = uiState.confirmPassword,
            onValueChange = { onAction(SignUpContract.UiAction.OnConfirmPasswordChange(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            label = { Text("Confirm Password") },
            placeholder = { Text("Confirm Password") },
            supportingText = { Text("Confirm your password") },
            singleLine = true,
            visualTransformation = if (uiState.confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_password),
                    contentDescription = "Password icon"
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { onAction(SignUpContract.UiAction.OnConfirmPasswordVisibilityChange) }
                ) {
                    Icon(
                        imageVector = confirmPasswordVisibilityIcon,
                        contentDescription = "Visibility icon"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            )
        )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            Button(
                onClick = {
                    onAction(
                        SignUpContract.UiAction.OnSignUpClick(
                            uiState.name,
                            uiState.email,
                            uiState.password,
                            uiState.confirmPassword
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 2.dp,
                    pressedElevation = 4.dp
                ),
            ) {
                Text(text = "Sign Up")
            }
        }
        Text(
            text = buildAnnotatedString {
                append("Already have an account? ")
                withLink(
                    link = LinkAnnotation.Clickable(
                        tag = ClickableLink.SignIn.tag,
                        styles = TextLinkStyles(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold
                            )
                        ),
                        linkInteractionListener = { onAction(SignUpContract.UiAction.OnSignInTextClick) }
                    )
                ) {
                    append("Sign In")
                }
            },
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SignUpScreenPreview() {
    SignUpScreen(
        uiState = SignUpContract.UiState(),
        uiEffect = flow { },
        onAction = {},
        navController = NavController(LocalContext.current)
    )
}