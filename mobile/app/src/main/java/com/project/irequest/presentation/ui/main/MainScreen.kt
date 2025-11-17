package com.project.irequest.presentation.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.irequest.presentation.navigation.AppDestinations
import com.project.irequest.presentation.navigation.BottomNavItem
import com.project.irequest.presentation.theme.PrimaryBlue
import com.project.irequest.presentation.ui.alerts.AlertsScreen
import com.project.irequest.presentation.ui.home.HomeScreen
import com.project.irequest.presentation.ui.mytasks.MyTasksScreen
import com.project.irequest.presentation.ui.profile.ProfileScreen
import com.project.irequest.presentation.ui.profile.EditProfileScreen
import com.project.irequest.presentation.ui.profile.ChangePasswordScreen
import com.project.irequest.presentation.ui.profile.SettingsScreen
import com.project.irequest.presentation.ui.profile.PersonalInfoScreen
import com.project.irequest.presentation.ui.profile.TermsOfServiceScreen
import com.project.irequest.presentation.ui.profile.PrivacyPolicyScreen
import com.project.irequest.presentation.ui.profile.DataPrivacyScreen
import com.project.irequest.presentation.ui.profile.SecurityPrivacyScreen
import com.project.irequest.presentation.ui.profile.SupportHelpScreen

/**
 * Main Screen with Bottom Navigation
 * Optimized 4-tab navigation for Request Management Ecosystem:
 * - Home: Dashboard and Overview
 * - My Tasks: Work Center for Processing Requests (Multi-Workflow)
 * - Alerts: Unified Notifications + Chat + SLA Warnings
 * - Profile: User Settings and Management
 */
@Composable
@Suppress("FunctionName")
fun MainScreen(
    onLogout: () -> Unit = {}
) {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = AppDestinations.Main.HOME,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Home Tab - Dashboard and Overview
            composable(AppDestinations.Main.HOME) {
                HomeScreen(
                    onCreateRequest = {
                        // TODO: Navigate to create request
                    },
                    onMyTasks = {
                        // Navigate to My Tasks tab
                        navController.navigate(AppDestinations.Main.MY_TASKS) {
                            launchSingleTop = true
                        }
                    },
                    onRequestClick = { requestId ->
                        // TODO: Navigate to request details
                    },
                    onNotificationClick = {
                        // Navigate to Alerts tab
                        navController.navigate(AppDestinations.Main.ALERTS) {
                            launchSingleTop = true
                        }
                    },
                    onProfileClick = {
                        navController.navigate(AppDestinations.Main.PROFILE) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            
            // My Tasks Tab - Work Center for Processing Multi-Workflow Requests
            composable(AppDestinations.Main.MY_TASKS) {
                MyTasksScreen(
                    onTaskClick = { taskId ->
                        // TODO: Navigate to task processing screen
                    },
                    onCreateRequest = {
                        // TODO: Navigate to create request screen
                    },
                    onQuickApprove = { taskId ->
                        // TODO: Handle quick approve
                    },
                    onQuickReject = { taskId ->
                        // TODO: Handle quick reject
                    }
                )
            }
            
            // Alerts Tab - Unified Notifications + Chat + SLA Warnings
            composable(AppDestinations.Main.ALERTS) {
                AlertsScreen(
                    onAlertClick = { alertId ->
                        // TODO: Navigate to related request/chat/screen
                    },
                    onMarkAllRead = {
                        // TODO: Mark all alerts as read
                    }
                )
            }
            
            // Profile Tab - User Settings and Management
            composable(AppDestinations.Main.PROFILE) {
                ProfileScreen(
                    onLogout = onLogout,
                    onEditProfile = {
                        navController.navigate(AppDestinations.Profile.EDIT)
                    },
                    onChangePassword = {
                        navController.navigate(AppDestinations.Profile.CHANGE_PASSWORD)
                    },
                    onSettings = {
                        navController.navigate(AppDestinations.Profile.SETTINGS)
                    },
                    onPersonalInfo = {
                        navController.navigate(AppDestinations.Profile.PERSONAL_INFO)
                    },
                    onTermsOfService = {
                        navController.navigate(AppDestinations.Profile.TERMS_OF_SERVICE)
                    },
                    onPrivacyPolicy = {
                        navController.navigate(AppDestinations.Profile.PRIVACY_POLICY)
                    },
                    onDataPrivacy = {
                        navController.navigate(AppDestinations.Profile.DATA_PRIVACY)
                    },
                    onSecurityPrivacy = {
                        navController.navigate(AppDestinations.Profile.SECURITY_PRIVACY)
                    },
                    onSupportHelp = {
                        navController.navigate(AppDestinations.Profile.SUPPORT_HELP)
                    }
                )
            }
            
            // Edit Profile Screen
            composable(AppDestinations.Profile.EDIT) {
                EditProfileScreen(
                    onBack = { navController.navigateUp() },
                    onSave = { address, birthDate ->
                        // TODO: Save profile data to ViewModel
                        navController.navigateUp()
                    }
                )
            }
            
            // Change Password Screen
            composable(AppDestinations.Profile.CHANGE_PASSWORD) {
                ChangePasswordScreen(
                    onBack = { navController.navigateUp() },
                    onChangePassword = { oldPassword, newPassword, confirmPassword ->
                        // TODO: Call change password API
                        navController.navigateUp()
                    }
                )
            }
            
            // Settings Screen
            composable(AppDestinations.Profile.SETTINGS) {
                SettingsScreen(
                    onBack = { navController.navigateUp() },
                    onLanguageClick = {
                        // TODO: Show language selection dialog
                    },
                    onAboutClick = {
                        // TODO: Navigate to about screen
                    }
                )
            }
            
            // Personal Info Screen
            composable(AppDestinations.Profile.PERSONAL_INFO) {
                PersonalInfoScreen(
                    onBack = { navController.navigateUp() },
                    onEdit = { navController.navigate(AppDestinations.Profile.EDIT) }
                )
            }
            
            // Terms of Service Screen
            composable(AppDestinations.Profile.TERMS_OF_SERVICE) {
                TermsOfServiceScreen(
                    onBack = { navController.navigateUp() }
                )
            }
            
            // Privacy Policy Screen
            composable(AppDestinations.Profile.PRIVACY_POLICY) {
                PrivacyPolicyScreen(
                    onBack = { navController.navigateUp() }
                )
            }
            
            // Data Privacy Screen
            composable(AppDestinations.Profile.DATA_PRIVACY) {
                DataPrivacyScreen(
                    onBack = { navController.navigateUp() }
                )
            }
            
            // Security and Privacy Screen
            composable(AppDestinations.Profile.SECURITY_PRIVACY) {
                SecurityPrivacyScreen(
                    onBack = { navController.navigateUp() },
                    onChangePassword = { navController.navigate(AppDestinations.Profile.CHANGE_PASSWORD) }
                )
            }
            
            // Support and Help Screen
            composable(AppDestinations.Profile.SUPPORT_HELP) {
                SupportHelpScreen(
                    onBack = { navController.navigateUp() }
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        BottomNavItem.items.forEach { item ->
            val isSelected = currentDestination?.hierarchy?.any { 
                it.route == item.route 
            } == true
            
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    // Add badges for My Tasks and Alerts
                    when (item) {
                        is BottomNavItem.MyTasks -> {
                            BadgedBox(
                                badge = {
                                    // TODO: Get actual pending task count from ViewModel
                                    val pendingTaskCount = 8
                                    if (pendingTaskCount > 0) {
                                        Badge {
                                            Text(text = pendingTaskCount.toString())
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.label
                                )
                            }
                        }
                        is BottomNavItem.Alerts -> {
                            BadgedBox(
                                badge = {
                                    // TODO: Get actual alert count (notifications + chat + SLA) from ViewModel
                                    val alertCount = 12
                                    if (alertCount > 0) {
                                        Badge {
                                            Text(text = alertCount.toString())
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.label
                                )
                            }
                        }
                        else -> {
                            Icon(
                                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.label
                            )
                        }
                    }
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PrimaryBlue,
                    selectedTextColor = PrimaryBlue,
                    indicatorColor = PrimaryBlue.copy(alpha = 0.1f),
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}