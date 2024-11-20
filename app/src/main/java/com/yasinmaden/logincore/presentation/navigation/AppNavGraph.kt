package com.yasinmaden.logincore.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yasinmaden.logincore.presentation.auth.signin.SignInScreen
import com.yasinmaden.logincore.presentation.auth.signin.SignInViewModel
import com.yasinmaden.logincore.presentation.auth.signup.SignupScreen
import com.yasinmaden.logincore.presentation.auth.signup.SignUpViewModel
import com.yasinmaden.logincore.presentation.main.home.HomeScreen
import com.yasinmaden.logincore.presentation.main.home.HomeViewModel
import com.yasinmaden.logincore.presentation.main.profile.ProfileScreen
import com.yasinmaden.logincore.presentation.main.profile.ProfileViewModel
import com.yasinmaden.logincore.presentation.main.settings.SettingsScreen
import com.yasinmaden.logincore.presentation.main.settings.SettingsViewModel

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.SignIn.route
    ) {
        composable(route = Screen.SignIn.route) {

            val viewModel: SignInViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val onAction = viewModel::onAction
            val uiEffect = viewModel.uiEffect

            SignInScreen(
                uiState = uiState,
                onAction = onAction,
                uiEffect = uiEffect,
                navController = navController
            )
        }
        composable(route = Screen.SignUp.route) {

            val viewModel: SignUpViewModel = hiltViewModel()
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

