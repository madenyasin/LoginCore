package com.yasinmaden.logincore.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yasinmaden.logincore.auth.presentation.login.LoginScreen
import com.yasinmaden.logincore.auth.presentation.login.LoginViewModel
import com.yasinmaden.logincore.auth.presentation.signup.SignupScreen
import com.yasinmaden.logincore.auth.presentation.signup.SignupViewModel
import com.yasinmaden.logincore.main.presentation.home.HomeScreen
import com.yasinmaden.logincore.main.presentation.home.HomeViewModel
import com.yasinmaden.logincore.main.presentation.profile.ProfileScreen
import com.yasinmaden.logincore.main.presentation.profile.ProfileViewModel
import com.yasinmaden.logincore.main.presentation.settings.SettingsScreen
import com.yasinmaden.logincore.main.presentation.settings.SettingsViewModel

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {

            val viewModel: LoginViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val onAction = viewModel::onAction
            val uiEffect = viewModel.uiEffect

            LoginScreen(
                uiState = uiState,
                onAction = onAction,
                uiEffect = uiEffect,
                navController = navController
            )
        }
        composable(route = Screen.Signup.route) {

            val viewModel: SignupViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val onAction = viewModel::onAction
            val uiEffect = viewModel.uiEffect

            SignupScreen(
                uiState = uiState,
                onAction = onAction,
                uiEffect = uiEffect,
                navController = navController
            )
        }

        composable(route = Screen.Home.route){
            val viewModel: HomeViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val onAction = viewModel::onAction
            val uiEffect = viewModel.uiEffect

            HomeScreen(
                uiState = uiState,
                onAction = onAction,
                uiEffect = uiEffect,
                navController = navController
            )
        }

        composable(route = Screen.Profile.route){
            val viewModel: ProfileViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val onAction = viewModel::onAction
            val uiEffect = viewModel.uiEffect
            ProfileScreen(
                uiState = uiState,
                onAction = onAction,
                uiEffect = uiEffect,
                navController = navController
            )
        }

        composable(route = Screen.Settings.route){
            val viewModel: SettingsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val onAction = viewModel::onAction
            val uiEffect = viewModel.uiEffect

            SettingsScreen(
                uiState = uiState,
                onAction = onAction,
                uiEffect = uiEffect,
                navController = navController
            )
        }
    }
}

