package com.yasinmaden.logincore.presentation.auth.components.textfields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
fun SignupNameField(
    uiState: SignUpContract.UiState,
    onAction: (SignUpContract.UiAction) -> Unit,
    modifier: Modifier = Modifier
){
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
        isError = uiState.isNameError,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Person, contentDescription = "Person icon")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
    )
}