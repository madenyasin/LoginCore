package com.yasinmaden.logincore.presentation.auth.components.textfields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.yasinmaden.logincore.R
import com.yasinmaden.logincore.presentation.auth.signup.SignUpContract

@Composable
fun SignupConfirmPasswordField(
    uiState: SignUpContract.UiState,
    onAction: (SignUpContract.UiAction) -> Unit,
    trailingIcon: ImageVector
){
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
        isError = uiState.isConfirmPasswordError,
        visualTransformation = if (uiState.isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_password),
                contentDescription = "Password icon"
            )
        },
        trailingIcon = {
            IconButton(
                onClick = { onAction(SignUpContract.UiAction.OnConfirmPasswordVisibilityToggle) }
            ) {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = "Visibility icon"
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        )
    )
}