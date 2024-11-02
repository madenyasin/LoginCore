package com.yasinmaden.logincore.ui.auth.presentation.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yasinmaden.logincore.ui.auth.presentation.login.LoginContract
import com.yasinmaden.logincore.ui.auth.presentation.login.LoginContract.UiAction

@Composable
fun LoginButtonSection(
    uiState: LoginContract.UiState,
    onAction: (UiAction) -> Unit,
    modifier: Modifier = Modifier,
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
    ) {
        OutlinedButton(
            onClick = { onAction(UiAction.OnSignUpClick) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            Text(text = "Sign Up")
        }
        Button(
            onClick = {
                onAction(UiAction.OnLoginClick)
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 2.dp,
                pressedElevation = 4.dp
            ),
        ) {
            Text(text = "Login")
        }
    }
}