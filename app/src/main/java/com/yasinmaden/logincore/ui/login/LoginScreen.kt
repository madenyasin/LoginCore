package com.yasinmaden.logincore.ui.login

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yasinmaden.logincore.R
import com.yasinmaden.logincore.ui.theme.LoginCoreTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)

    ) {

        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        var visibility by rememberSaveable { mutableStateOf(false) }

        val visibilityIcon = if (visibility) {
            ImageVector.vectorResource(R.drawable.ic_visibility_on)
        } else {
            ImageVector.vectorResource(R.drawable.ic_visibility_off)
        }

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_home_app_logo),
            contentDescription = "Home App Logo",
            modifier = Modifier.size(125.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            label = { Text("Email") },
            placeholder = { Text("Enter your email") },
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
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            label = { Text("Password") },
            placeholder = { Text("Enter your password") },
            supportingText = { Text("Enter your password") },
            singleLine = true,
            visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_password),
                    contentDescription = "Password icon"
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { visibility = !visibility }
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

        Row (
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ){
            OutlinedButton(
                onClick = { TODO("Signup OnClick") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                Text(text = "Sign Up")
            }
            Button(
                onClick = { TODO("Login OnClick") },
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
}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    LoginCoreTheme {
        LoginScreen()
    }
}