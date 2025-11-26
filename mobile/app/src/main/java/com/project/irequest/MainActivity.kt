package com.project.irequest

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.Surface
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
import com.project.irequest.presentation.navigation.IRequestNavigation
import com.project.irequest.presentation.theme.IRequestTheme
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
/**
 * Main Screen with Bottom Navigation
 * Updated to accept onNavigateToDetail callback
 */

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Thay "IRequestTheme" bằng tên Theme chính xác của dự án bạn
             IRequestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    IRequestNavigation(navController = navController)
                }
            }
        }
    }
}

@Composable
@Suppress("FunctionName")
fun MainScreen(
    onLogout: () -> Unit = {},
    onNavigateToDetail: (String) -> Unit = {}
    // [FIX] Thêm dòng này để IRequestNavigation có thể truyền hàm vào
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
            // Home Tab
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
                        // [FIX] Gọi hàm của cha (IRequestNavigation) thay vì tự điều hướng
                        onNavigateToDetail(requestId)
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
                    onSearchClick = { },
                    onReportsClick = { navController.navigate(AppDestinations.Main.REPORTS) },
                    onBoardClick = { navController.navigate(AppDestinations.Main.BOARD) },
                    onRoadmapClick = { navController.navigate(AppDestinations.Main.ROADMAP) }
                )
            }

            // Reports Screen
            composable(AppDestinations.Main.REPORTS) {
                com.project.irequest.presentation.ui.reports.ReportsScreen(
                    onBack = { navController.navigateUp() }
                )
            }

            // Board Screen
            composable(AppDestinations.Main.BOARD) {
                com.project.irequest.presentation.ui.board.BoardScreen(
                    onBack = { navController.navigateUp() },
                    onRequestClick = { requestId ->
                        // [FIX] Gọi hàm của cha
                        onNavigateToDetail(requestId)
                    }
                )
            }

            // Roadmap Screen
            composable(AppDestinations.Main.ROADMAP) {
                com.project.irequest.presentation.ui.roadmap.RoadmapScreen(
                    onBack = { navController.navigateUp() }
                )
            }

            // My Tasks Tab
            composable(AppDestinations.Main.MY_TASKS) {
                MyTasksScreen(
                    onTaskClick = { taskId ->
                        // [FIX] Gọi hàm của cha
                        onNavigateToDetail(taskId)
                    },
                    onCreateRequest = { },
                    onQuickApprove = { },
                    onQuickReject = { }
                )
            }

            // Alerts Tab
            composable(AppDestinations.Main.ALERTS) {
                AlertsScreen(
                    onAlertClick = { alertId ->
                        // [FIX] Gọi hàm của cha
                        onNavigateToDetail(alertId)
                    },
                    onMarkAllRead = { }
                )
            }

            // [QUAN TRỌNG]: Đã XÓA composable("request_detail") ở đây
            // Vì IRequestNavigation (cấp cha) đã xử lý việc này để che thanh BottomBar

            // Profile Tab
            composable(AppDestinations.Main.PROFILE) {
                ProfileScreen(
                    onLogout = onLogout,
                    onEditProfile = { navController.navigate(AppDestinations.Profile.EDIT) },
                    onChangePassword = { navController.navigate(AppDestinations.Profile.CHANGE_PASSWORD) },
                    onSettings = { navController.navigate(AppDestinations.Profile.SETTINGS) },
                    onPersonalInfo = { navController.navigate(AppDestinations.Profile.PERSONAL_INFO) },
                    onTermsOfService = { navController.navigate(AppDestinations.Profile.TERMS_OF_SERVICE) },
                    onPrivacyPolicy = { navController.navigate(AppDestinations.Profile.PRIVACY_POLICY) },
                    onDataPrivacy = { navController.navigate(AppDestinations.Profile.DATA_PRIVACY) },
                    onSecurityPrivacy = { navController.navigate(AppDestinations.Profile.SECURITY_PRIVACY) },
                    onSupportHelp = { navController.navigate(AppDestinations.Profile.SUPPORT_HELP) }
                )
            }

            // Các màn hình con của Profile
            composable(AppDestinations.Profile.EDIT) {
                EditProfileScreen(
                    onBack = { navController.navigateUp() },
                    onSave = { _, _ -> navController.navigateUp() }
                )
            }
            composable(AppDestinations.Profile.CHANGE_PASSWORD) {
                ChangePasswordScreen(
                    onBack = { navController.navigateUp() },
                    onChangePassword = { _, _, _ -> navController.navigateUp() }
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