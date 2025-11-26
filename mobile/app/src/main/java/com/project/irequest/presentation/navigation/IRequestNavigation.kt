package com.project.irequest.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.project.irequest.presentation.chat.ChatDetailScreen
import com.project.irequest.presentation.chat.ChatListScreen
import com.project.irequest.presentation.ui.auth.ForgotPasswordScreen
import com.project.irequest.presentation.ui.auth.LoginScreen
import com.project.irequest.presentation.ui.auth.RegisterScreen
import com.project.irequest.presentation.ui.home.HomeScreenComplete
import com.project.irequest.presentation.ui.main.MainScreen
import com.project.irequest.presentation.ui.mytasks.MyTasksScreen
import com.project.irequest.presentation.ui.profile.ProfileScreen
import com.project.irequest.presentation.ui.requests.detail.RequestDetailScreen

@Composable
@Suppress("FunctionName")
fun IRequestNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.Auth.LOGIN
    ) {
        // --- AUTH ---
        composable(AppDestinations.Auth.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(AppDestinations.Main.HOME) {
                        popUpTo(AppDestinations.Auth.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(AppDestinations.Auth.REGISTER) },
                onNavigateToForgotPassword = { navController.navigate(AppDestinations.Auth.FORGOT_PASSWORD) }
            )
        }

        composable(AppDestinations.Auth.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(AppDestinations.Main.HOME) {
                        popUpTo(AppDestinations.Auth.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        composable(AppDestinations.Auth.FORGOT_PASSWORD) {
            ForgotPasswordScreen(onNavigateBack = { navController.popBackStack() })
        }

        // --- MAIN (includes bottom navigation) ---
        composable(AppDestinations.Main.HOME) {
            val mainNavController = rememberNavController()
            MainScreen(
                navController = mainNavController,
                onLogout = {
                    navController.navigate(AppDestinations.Auth.LOGIN) {
                        popUpTo(AppDestinations.Main.HOME) { inclusive = true }
                    }
                }
            ) { paddingValues ->
                NavHost(
                    navController = mainNavController,
                    startDestination = AppDestinations.Main.HOME,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    composable(AppDestinations.Main.HOME) {
                        HomeScreenComplete(
                            onChatClick = { mainNavController.navigate(AppDestinations.Main.ALERTS) },
                            onCreateRequest = {},
                            onMyRequests = {},
                            onAssignedToMe = { mainNavController.navigate(AppDestinations.Main.MY_TASKS) },
                            onDrafts = {},
                            onTemplates = {},
                            onReports = {},
                            onBoardClick = {},
                            onRoadmapClick = {},
                            onAnalytics = {},
                            onDepartments = {},
                            onUsers = {},
                            onWorkflows = {},
                            onSettings = {},
                            onNotifications = {},
                            onCalendar = {},
                            onArchive = {}
                        )
                    }
                    composable(AppDestinations.Main.MY_TASKS) { 
                        MyTasksScreen(onTaskClick = {}, onCreateRequest = {}, onQuickApprove = {}, onQuickReject = {}) 
                    }
                    composable(AppDestinations.Main.ALERTS) {
                        ChatListScreen(
                            onNavigateToChatDetail = { roomId ->
                                navController.navigate(AppDestinations.Chat.roomRoute(roomId))
                            },
                            onBackClick = { mainNavController.popBackStack() }
                        )
                    }
                    composable(AppDestinations.Main.PROFILE) {
                        ProfileScreen(
                            onLogout = {
                                navController.navigate(AppDestinations.Auth.LOGIN) {
                                    popUpTo(AppDestinations.Main.HOME) { inclusive = true }
                                }
                            },
                             onEditProfile = {},
                             onChangePassword = {},
                             onSettings = {},
                             onPersonalInfo = {},
                             onTermsOfService = {},
                             onPrivacyPolicy = {},
                             onDataPrivacy = {},
                             onSecurityPrivacy = {},
                             onSupportHelp = {}
                        )
                    }
                }
            }
        }

        // --- SCREENS WITHOUT BOTTOM BAR ---
        composable(
            route = "request_detail/{requestId}",
            arguments = listOf(navArgument("requestId") { type = NavType.StringType })
        ) { backStackEntry ->
            val requestId = backStackEntry.arguments?.getString("requestId") ?: ""
            RequestDetailScreen(
                requestId = requestId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = AppDestinations.Chat.ChatDetailScreen,
            arguments = listOf(navArgument("roomId") { type = NavType.StringType })
        ) { backStackEntry ->
            val roomId = backStackEntry.arguments?.getString("roomId") ?: ""
            ChatDetailScreen(
                roomId = roomId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
