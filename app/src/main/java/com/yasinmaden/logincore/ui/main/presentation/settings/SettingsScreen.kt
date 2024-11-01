package com.yasinmaden.logincore.ui.main.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow

@Composable
fun SettingsScreen(
    uiState: SettingsContract.UiState,
    uiEffect: Flow<SettingsContract.UiEffect>,
    onAction: (SettingsContract.UiAction) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    HandleSettingsUiEffects(
        uiEffect = uiEffect,
        navController = navController
    )

    SettingsContent(
        uiState = uiState,
        uiEffect = uiEffect,
        onAction = onAction,
        modifier = modifier
    )
}

@Composable
fun SettingsContent(
    uiState: SettingsContract.UiState,
    uiEffect: Flow<SettingsContract.UiEffect>,
    onAction: (SettingsContract.UiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)

    ) {
        Text(text = "Settings")
    }
}

@Composable
fun HandleSettingsUiEffects(
    uiEffect: Flow<SettingsContract.UiEffect>,
    navController: NavController,
) {
    LaunchedEffect(true) {
        uiEffect.collect{effect->
            when(effect){

                else -> {}
            }
        }
    }
}
