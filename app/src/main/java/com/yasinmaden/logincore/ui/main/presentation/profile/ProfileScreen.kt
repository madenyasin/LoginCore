package com.yasinmaden.logincore.ui.main.presentation.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow

@Composable
fun ProfileScreen(
    uiState: ProfileContract.UiState,
    uiEffect: Flow<ProfileContract.UiEffect>,
    onAction: (ProfileContract.UiAction) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    HandleProfileUiEffects(
        uiEffect = uiEffect,
        navController = navController
    )

    ProfileContent(
        uiState = uiState,
        uiEffect = uiEffect,
        onAction = onAction,
        modifier = modifier
    )
}

@Composable
fun ProfileContent(
    uiState: ProfileContract.UiState,
    uiEffect: Flow<ProfileContract.UiEffect>,
    onAction: (ProfileContract.UiAction) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)

    ) {
        Text(text = "Profile")
        Button(
            onClick = { onAction(ProfileContract.UiAction.Logout) }
        ) {
            Text(text = "Logout")
        }
    }
}

@Composable
fun HandleProfileUiEffects(
    uiEffect: Flow<ProfileContract.UiEffect>,
    navController: NavController
) {
    val context = LocalContext.current
    LaunchedEffect(true) {
        uiEffect.collect { effect ->
            when (effect) {
                ProfileContract.UiEffect.NavigateToLogin -> {
                    navController.navigate("login") {
                        popUpTo("home") {
                            inclusive = true
                        }
                    }
                }

                is ProfileContract.UiEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

