package com.project.irequest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.irequest.presentation.ui.auth.LoginScreen
import com.project.irequest.presentation.ui.auth.RegisterScreen
import com.project.irequest.presentation.ui.auth.ForgotPasswordScreen
import com.project.irequest.presentation.ui.main.MainScreen

@Composable
@Suppress("FunctionName")
fun IRequestNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.Auth.LOGIN
    ) {
        // Authentication Flow
        composable(AppDestinations.Auth.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(AppDestinations.Main.HOME) {
                        popUpTo(AppDestinations.Auth.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(AppDestinations.Auth.REGISTER)
                },
                onNavigateToForgotPassword = {
                    navController.navigate(AppDestinations.Auth.FORGOT_PASSWORD)
                }
            )
        }
        
        composable(AppDestinations.Auth.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(AppDestinations.Main.HOME) {
                        popUpTo(AppDestinations.Auth.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(AppDestinations.Auth.FORGOT_PASSWORD) {
            ForgotPasswordScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Main App Flow with Bottom Navigation
        composable(AppDestinations.Main.HOME) {
            MainScreen(
                onLogout = {
                    navController.navigate(AppDestinations.Auth.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}