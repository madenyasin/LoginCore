package com.yasinmaden.logincore.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yasinmaden.logincore.auth.presentation.login.LoginScreen
import com.yasinmaden.logincore.auth.presentation.login.LoginViewModel
import com.yasinmaden.logincore.auth.presentation.signup.SignUpScreen
import com.yasinmaden.logincore.auth.presentation.signup.SignUpViewModel
import com.yasinmaden.logincore.util.Constants

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Constants.Destinations.Login.route
    ) {
        composable(route = Constants.Destinations.Login.route) {

            val viewModel: LoginViewModel = viewModel()
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
        composable(route = Constants.Destinations.SignUp.route) {

            val viewModel: SignUpViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val onAction = viewModel::onAction
            val uiEffect = viewModel.uiEffect

            SignUpScreen(
                uiState = uiState,
                onAction = onAction,
                uiEffect = uiEffect,
                navController = navController
            )
        }
    }
}

