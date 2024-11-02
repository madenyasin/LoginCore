package com.yasinmaden.logincore.ui.auth.presentation.components.textfields

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
import com.yasinmaden.logincore.ui.auth.presentation.signup.SignupContract

@Composable
fun SignupConfirmPasswordField(
    uiState: SignupContract.UiState,
    onAction: (SignupContract.UiAction) -> Unit,
    trailingIcon: ImageVector
){
    OutlinedTextField(
        value = uiState.confirmPassword,
        onValueChange = { onAction(SignupContract.UiAction.OnConfirmPasswordChange(it)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        label = { Text("Confirm Password") },
        placeholder = { Text("Confirm Password") },
        supportingText = { Text("Confirm your password") },
        singleLine = true,
        isError = uiState.isConfirmPasswordError,
        visualTransformation = if (uiState.confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_password),
                contentDescription = "Password icon"
            )
        },
        trailingIcon = {
            IconButton(
                onClick = { onAction(SignupContract.UiAction.OnConfirmPasswordVisibilityChange) }
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