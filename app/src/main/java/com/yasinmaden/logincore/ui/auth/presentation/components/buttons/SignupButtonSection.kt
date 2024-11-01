package com.yasinmaden.logincore.ui.auth.presentation.components.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yasinmaden.logincore.ui.auth.presentation.signup.SignupContract

@Composable
fun SignupButtonSection(
    uiState: SignupContract.UiState,
    onAction: (SignupContract.UiAction) -> Unit
){
    Button(
        onClick = {
            onAction(
                SignupContract.UiAction.OnSignUpClick(
                    uiState.name,
                    uiState.email,
                    uiState.password,
                    uiState.confirmPassword
                )
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        ),
    ) {
        Text(text = "Sign Up")
    }
}