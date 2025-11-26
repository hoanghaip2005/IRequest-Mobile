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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.project.irequest.presentation.ui.calendar.CalendarScreen
import com.project.irequest.presentation.ui.chat.ChatScreen
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
 * - Chat: Messages & Conversations
 * - Profile: User Settings and Management
 */
@Composable
@Suppress("FunctionName")
fun MainScreen(
    onLogout: () -> Unit = {},
    onNavigateToDetail: (String) -> Unit = {}
) {
    val navController = rememberNavController()
    var currentBottomTab by remember { mutableStateOf(AppDestinations.Main.HOME) }
    
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentTab = currentBottomTab,
                onTabChange = { newTab -> currentBottomTab = newTab }
            )
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
                        // Navigate to create request screen
                        navController.navigate("main/create-request")
                    },
                    onMyRequests = {
                        // Navigate to My Requests tab
                        navController.navigate("main/my-requests")
                    },
                    onAssignedToMe = {
                        // Navigate to My Tasks tab
                        navController.navigate(AppDestinations.Main.MY_TASKS) {
                            popUpTo(AppDestinations.Main.HOME) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onDrafts = {
                        // TODO: Navigate to drafts
                    },
                    onTemplates = {
                        // TODO: Navigate to templates
                    },
                    onReports = {
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
                    },
                    onAnalytics = {
                        navController.navigate(AppDestinations.Main.REPORTS) {
                            launchSingleTop = true
                        }
                    },
                    onDepartments = {
                        // TODO: Navigate to departments
                    },
                    onUsers = {
                        // TODO: Navigate to users
                    },
                    onWorkflows = {
                        // TODO: Navigate to workflows
                    },
                    onSettings = {
                        navController.navigate(AppDestinations.Main.PROFILE) {
                            launchSingleTop = true
                        }
                    },
                    // SỬA: Nút Notification (Chuông) sẽ dẫn đến route riêng là "system_alerts"
                    onNotifications = {
                        navController.navigate("system_alerts") {
                            launchSingleTop = true
                        }
                    },
                    onCalendar = {
                        navController.navigate(AppDestinations.Main.CALENDAR) {
                            launchSingleTop = true
                        }
                    },
                    onArchive = {
                        // TODO: Navigate to archive
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
                        // TODO: Navigate to request details
                    }
                )
            }

            // Roadmap Screen - Project Timeline & Milestones
            composable(AppDestinations.Main.ROADMAP) {
                com.project.irequest.presentation.ui.roadmap.RoadmapScreen(
                    onBack = { navController.navigateUp() }
                )
            }
            
            // Calendar Screen - Request Schedule & Events
            composable(AppDestinations.Main.CALENDAR) {
                CalendarScreen(
                    onBack = { navController.navigateUp() }
                )
            }

            // My Tasks Tab - Work Center for Processing Multi-Workflow Requests
            composable(AppDestinations.Main.MY_TASKS) {
                MyTasksScreen(
                    onTaskClick = { taskId ->
                        // Navigate to task detail/processing screen
                        // Extract request ID from task ID (TASK-001 -> REQ-001)
                        val requestId = taskId.replace("TASK", "REQ").trim()
                        navController.navigate("main/request-detail/$requestId") {
                            launchSingleTop = true
                        }
                    },
                    onCreateRequest = {
                        // Navigate to create request screen
                        navController.navigate("main/create-request") {
                            launchSingleTop = true
                        }
                    },
                    onQuickApprove = { taskId ->
                        // Handle quick approve with confirmation
                        // TODO: Implement approval confirmation dialog
                        // For now: show a snackbar message and update state
                        println("Approved task: $taskId")
                        // After approval, the task should be removed from list
                    },
                    onQuickReject = { taskId ->
                        // Handle quick reject with reason input
                        // TODO: Implement rejection reason dialog
                        // For now: show a snackbar message
                        println("Rejected task: $taskId")
                        // After rejection, the task should be removed from list
                    }
                )
            }

            // Chat Tab - Messages & Conversations
            // SỬA: Khôi phục lại ChatScreen ở đây để Tab dưới đáy hiện Chat
            composable(AppDestinations.Main.ALERTS) {
                ChatScreen(
                    onChatClick = { chatId ->
                        // TODO: Navigate to chat detail
                    },
                    onNewChat = {
                        // TODO: Create new chat
                    }
                )
            }

            // SỬA: Thêm route mới dành riêng cho màn hình Thông báo (Alerts)
            composable("system_alerts") {
                AlertsScreen(
                    onAlertClick = { alertId ->
                        // TODO: Navigate to detail based on alert
                    },
                    onMarkAllRead = {
                        // TODO: Mark all read logic
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
            
            // Create Request Screen - Feature 3
            composable("main/create-request") {
                com.project.irequest.presentation.ui.requests.CreateRequestScreen(
                    requestId = null,
                    onNavigateBack = { navController.navigateUp() },
                    onRequestCreated = { requestId ->
                        // Navigate to request detail after creation
                        navController.navigate("main/request-detail/$requestId") {
                            popUpTo("main/create-request") { inclusive = true }
                        }
                    }
                )
            }
            
            // Edit Request Screen
            composable("main/edit-request/{requestId}") { backStackEntry ->
                val requestId = backStackEntry.arguments?.getString("requestId")?.toIntOrNull() ?: 1
                com.project.irequest.presentation.ui.requests.CreateRequestScreen(
                    requestId = requestId,
                    onNavigateBack = { navController.navigateUp() },
                    onRequestCreated = { updatedRequestId ->
                        // Navigate back to request detail after update
                        navController.navigate("main/request-detail/$updatedRequestId") {
                            popUpTo("main/edit-request/{requestId}") { inclusive = true }
                        }
                    }
                )
            }
            
            // My Requests Screen - Feature 1
            composable("main/my-requests") {
                com.project.irequest.presentation.ui.requests.MyRequestsScreen(
                    onRequestClick = { requestId ->
                        // Navigate to request detail
                        navController.navigate("main/request-detail/$requestId") {
                            launchSingleTop = true
                        }
                    },
                    onNavigateBack = { 
                        // Navigate back using back stack
                        navController.navigateUp()
                    }
                )
            }
            
            // Request Detail Screen - Feature 2
            composable("main/request-detail/{requestId}") { backStackEntry ->
                val requestId = backStackEntry.arguments?.getString("requestId")?.toIntOrNull() ?: 1
                com.project.irequest.presentation.ui.requests.RequestDetailScreen(
                    requestId = requestId,
                    onNavigateBack = { 
                        // Navigate back using back stack
                        navController.navigateUp()
                    },
                    onEditRequest = {
                        navController.navigate("main/edit-request/$requestId") {
                            launchSingleTop = true
                        }
                    },
                    onApproveRequest = {
                        // TODO: Show approval dialog
                    },
                    onRejectRequest = {
                        // TODO: Show rejection reason dialog
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationBar(
    navController: NavHostController,
    currentTab: String = AppDestinations.Main.HOME,
    onTabChange: (String) -> Unit = {},
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
                    // Update current tab
                    onTabChange(item.route)
                    
                    // If already on this tab, do nothing
                    if (item.route == currentDestination?.route) {
                        return@NavigationBarItem
                    }
                    
                    // Define bottom nav routes
                    val bottomNavRoutes = listOf(
                        AppDestinations.Main.HOME,
                        AppDestinations.Main.MY_TASKS,
                        AppDestinations.Main.ALERTS,
                        AppDestinations.Main.PROFILE
                    )
                    
                    // Clear all nested screens by popping back to a bottom nav tab or HOME
                    while (navController.currentDestination?.route !in bottomNavRoutes &&
                           navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                    
                    // Navigate to the selected bottom tab
                    navController.navigate(item.route) {
                        // Pop back to HOME but keep it in stack
                        popUpTo(AppDestinations.Main.HOME) {
                            saveState = true
                            inclusive = false
                        }
                        launchSingleTop = true
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
                            // Badge cho Tab Chat (Item Alerts cũ)
                            BadgedBox(
                                badge = {
                                    // TODO: Get unread message count from ViewModel
                                    val unreadCount = 3
                                    if (unreadCount > 0) {
                                        Badge {
                                            Text(text = unreadCount.toString())
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