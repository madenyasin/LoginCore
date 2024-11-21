package com.yasinmaden.logincore.presentation.auth.components.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yasinmaden.logincore.presentation.auth.signup.SignUpContract

@Composable
fun SignupButtonSection(
    uiState: SignUpContract.UiState,
    onAction: (SignUpContract.UiAction) -> Unit
){
    Button(
        onClick = {
            onAction(SignUpContract.UiAction.OnSignUpClick)
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