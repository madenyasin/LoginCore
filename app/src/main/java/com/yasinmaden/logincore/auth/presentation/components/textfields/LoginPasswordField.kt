package com.yasinmaden.logincore.auth.presentation.components.textfields

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
import com.yasinmaden.logincore.auth.presentation.login.LoginContract
import com.yasinmaden.logincore.auth.presentation.login.LoginContract.UiAction.OnPasswordChange
import com.yasinmaden.logincore.auth.presentation.login.LoginContract.UiAction.OnVisibilityChange

@Composable
fun LoginPasswordField(
    uiState: LoginContract.UiState,
    onAction: (LoginContract.UiAction) -> Unit,
    modifier: Modifier = Modifier,
){
    val visibilityIcon = if (uiState.passwordVisibility) {
        ImageVector.vectorResource(R.drawable.ic_visibility_on)
    } else {
        ImageVector.vectorResource(R.drawable.ic_visibility_off)
    }

    OutlinedTextField(
        value = uiState.password,
        onValueChange = { onAction(OnPasswordChange(it)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        label = { Text("Password") },
        placeholder = { Text("Enter your password") },
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
                onClick = { onAction(OnVisibilityChange) }
            ) {
                Icon(
                    imageVector = visibilityIcon,
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