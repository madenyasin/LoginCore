package com.yasinmaden.logincore.presentation.auth.components.textfields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.yasinmaden.logincore.presentation.auth.signup.SignUpContract

@Composable
fun SignupEmailField(
    uiState: SignUpContract.UiState,
    onAction: (SignUpContract.UiAction) -> Unit
){
    OutlinedTextField(
        value = uiState.email,
        onValueChange = { onAction(SignUpContract.UiAction.OnFormChange(email = it)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        label = { Text("Email") },
        placeholder = { Text("Email") },
        supportingText = { Text("Enter your email address") },
        singleLine = true,
        isError = uiState.isEmailError,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Email, contentDescription = "Email icon")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )
    )
}