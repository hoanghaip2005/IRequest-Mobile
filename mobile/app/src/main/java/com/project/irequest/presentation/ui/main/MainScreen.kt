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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.project.irequest.presentation.navigation.AppDestinations
import com.project.irequest.presentation.navigation.BottomNavItem
import com.project.irequest.presentation.theme.PrimaryBlue
import com.project.irequest.presentation.ui.alerts.AlertsScreen
import com.project.irequest.presentation.ui.home.HomeScreenComplete
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
// Import màn hình chi tiết mới tạo
import com.project.irequest.presentation.ui.requests.detail.RequestDetailScreen

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
    onLogout: () -> Unit = {},
    onNavigateToDetail: (String) -> Unit = {}
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
                HomeScreenComplete(
                    onCreateRequest = {
                        // TODO: Navigate to create request
                    },
                    onMyTasks = {
                        navController.navigate(AppDestinations.Main.MY_TASKS) {
                            launchSingleTop = true
                        }
                    },
                    onRequestClick = { requestId ->
                        // [UPDATE] Điều hướng đến chi tiết yêu cầu
                        navController.navigate("request_detail/$requestId")
                    },
                    onNotificationClick = {
                        navController.navigate(AppDestinations.Main.ALERTS) {
                            launchSingleTop = true
                        }
                    },
                    onProfileClick = {
                        navController.navigate(AppDestinations.Main.PROFILE) {
                            launchSingleTop = true
                        }
                    },
                    onSearchClick = {
                        // TODO: Navigate to search screen
                    },
                    onReportsClick = {
                        navController.navigate(AppDestinations.Main.REPORTS) {
                            launchSingleTop = true
                        }
                    },
                    onBoardClick = {
                        navController.navigate(AppDestinations.Main.BOARD) {
                            launchSingleTop = true
                        }
                    },
                    onRoadmapClick = {
                        navController.navigate(AppDestinations.Main.ROADMAP) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            // Reports Screen - Analytics Dashboard
            composable(AppDestinations.Main.REPORTS) {
                com.project.irequest.presentation.ui.reports.ReportsScreen(
                    onBack = { navController.navigateUp() }
                )
            }

            // Board Screen - Kanban Board
            composable(AppDestinations.Main.BOARD) {
                com.project.irequest.presentation.ui.board.BoardScreen(
                    onBack = { navController.navigateUp() },
                    onRequestClick = { requestId ->
                        // [UPDATE] Điều hướng đến chi tiết yêu cầu từ Board
                        navController.navigate("request_detail/$requestId")
                    }
                )
            }

            // Roadmap Screen - Project Timeline & Milestones
            composable(AppDestinations.Main.ROADMAP) {
                com.project.irequest.presentation.ui.roadmap.RoadmapScreen(
                    onBack = { navController.navigateUp() }
                )
            }

            // My Tasks Tab - Work Center for Processing Multi-Workflow Requests
            composable(AppDestinations.Main.MY_TASKS) {
                MyTasksScreen(
                    onTaskClick = { taskId ->
                        // [UPDATE] Điều hướng đến chi tiết yêu cầu từ My Tasks
                        navController.navigate("request_detail/$taskId")
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
                        // [UPDATE] Điều hướng đến chi tiết từ thông báo
                        // Giả định alertId map với requestId hoặc xử lý logic riêng
                        navController.navigate("request_detail/$alertId")
                    },
                    onMarkAllRead = {
                        // TODO: Mark all alerts as read
                    }
                )
            }

            // [NEW] Request Detail Screen - Global Route
            composable(
                route = "request_detail/{requestId}",
                arguments = listOf(navArgument("requestId") { type = NavType.StringType })
            ) { backStackEntry ->
                val requestId = backStackEntry.arguments?.getString("requestId") ?: ""
                RequestDetailScreen(
                    requestId = requestId,
                    onBackClick = { navController.navigateUp() }
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

            // ... (Các màn hình Profile giữ nguyên) ...
            composable(AppDestinations.Profile.EDIT) {
                EditProfileScreen(
                    onBack = { navController.navigateUp() },
                    onSave = { address, birthDate -> navController.navigateUp() }
                )
            }
            composable(AppDestinations.Profile.CHANGE_PASSWORD) {
                ChangePasswordScreen(
                    onBack = { navController.navigateUp() },
                    onChangePassword = { o, n, c -> navController.navigateUp() }
                )
            }
            composable(AppDestinations.Profile.SETTINGS) {
                SettingsScreen(
                    onBack = { navController.navigateUp() },
                    onLanguageClick = { },
                    onAboutClick = { }
                )
            }
            composable(AppDestinations.Profile.PERSONAL_INFO) {
                PersonalInfoScreen(
                    onBack = { navController.navigateUp() },
                    onEdit = { navController.navigate(AppDestinations.Profile.EDIT) }
                )
            }
            composable(AppDestinations.Profile.TERMS_OF_SERVICE) {
                TermsOfServiceScreen(onBack = { navController.navigateUp() })
            }
            composable(AppDestinations.Profile.PRIVACY_POLICY) {
                PrivacyPolicyScreen(onBack = { navController.navigateUp() })
            }
            composable(AppDestinations.Profile.DATA_PRIVACY) {
                DataPrivacyScreen(onBack = { navController.navigateUp() })
            }
            composable(AppDestinations.Profile.SECURITY_PRIVACY) {
                SecurityPrivacyScreen(
                    onBack = { navController.navigateUp() },
                    onChangePassword = { navController.navigate(AppDestinations.Profile.CHANGE_PASSWORD) }
                )
            }
            composable(AppDestinations.Profile.SUPPORT_HELP) {
                SupportHelpScreen(onBack = { navController.navigateUp() })
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
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    when (item) {
                        is BottomNavItem.MyTasks -> {
                            BadgedBox(
                                badge = {
                                    val pendingTaskCount = 8
                                    if (pendingTaskCount > 0) {
                                        Badge { Text(text = pendingTaskCount.toString()) }
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
                                    val alertCount = 12
                                    if (alertCount > 0) {
                                        Badge { Text(text = alertCount.toString()) }
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