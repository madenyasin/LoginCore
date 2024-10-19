package com.yasinmaden.logincore.ui.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.yasinmaden.logincore.navigation.Destinations
import com.yasinmaden.logincore.ui.login.LoginContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun SignUpScreen(
    uiState: SignUpContract.UiState,
    uiEffect: Flow<SignUpContract.UiEffect>,
    onAction: (SignUpContract.UiAction) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
){
    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = Destinations.SignUp.route)
    }
}
@Composable
@Preview(showBackground = true)
fun SignUpScreenPreview(){
    SignUpScreen(
        uiState = SignUpContract.UiState(),
        uiEffect = flow {  },
        onAction = {},
        navController = NavController(LocalContext.current)
    )
}