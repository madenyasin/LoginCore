package com.yasinmaden.logincore.presentation.auth.components.others

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.yasinmaden.logincore.presentation.auth.login.LoginContract
import com.yasinmaden.logincore.presentation.auth.login.LoginContract.UiAction

@Composable
fun ResetPasswordDialog(
    uiState: LoginContract.UiState,
    onAction: (UiAction) -> Unit,
    modifier: Modifier = Modifier,
){
    AlertDialog(
        title = { Text(text = "Reset Password") },
        text = {
            OutlinedTextField(
                value = uiState.resetPasswordEmail,
                onValueChange = { onAction(UiAction.OnResetPasswordEmailChange(it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Email") },
                placeholder = { Text(text = "Enter your email") },
                supportingText = { Text(text = "Send reset password link to this email") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email icon"
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send icon"
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                )
            )
        },
        confirmButton = {
            Button(
                onClick = { onAction(UiAction.OnResetPasswordDialogConfirm) }
            ) {
                Text(text = "Send")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = { onAction(UiAction.OnResetPasswordDialogDismiss) }
            ) {
                Text(text = "Cancel")
            }
        },
        onDismissRequest = { onAction(UiAction.OnResetPasswordDialogDismissRequest) },
    )
}