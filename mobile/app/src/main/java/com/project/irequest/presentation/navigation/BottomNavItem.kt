package com.project.irequest.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Bottom Navigation Items for IRequest Mobile App
 * 4 main tabs: Home (Dashboard), My Tasks (Work Center), Alerts (Notifications & Chat), Profile
 * Optimized for Request Management Workflow System
 */
sealed class BottomNavItem(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val description: String
) {
    object Home : BottomNavItem(
        route = AppDestinations.Main.HOME,
        label = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        description = "Dashboard & Overview"
    )
    
    object MyTasks : BottomNavItem(
        route = AppDestinations.Main.MY_TASKS,
        label = "My Tasks",
        selectedIcon = Icons.Filled.CheckCircle,
        unselectedIcon = Icons.Outlined.CheckCircle,
        description = "Work Center - Requests to Process"
    )
    
    object Alerts : BottomNavItem(
        route = AppDestinations.Main.ALERTS,
        label = "Chat",
        selectedIcon = Icons.Filled.Email,
        unselectedIcon = Icons.Outlined.Email,
        description = "Messages & Conversations"
    )
    
    object Profile : BottomNavItem(
        route = AppDestinations.Main.PROFILE,
        label = "Profile",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        description = "User Settings & Statistics"
    )
    
    companion object {
        val items = listOf(
            Home,
            MyTasks,
            Alerts,
            Profile
        )
    }
}
