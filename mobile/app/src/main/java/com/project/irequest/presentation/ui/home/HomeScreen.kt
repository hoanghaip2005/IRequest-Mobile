package com.project.irequest.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.irequest.presentation.theme.*

/**
 * Simplified Home Screen - Grid Navigation Only
 * Features: 4 columns × 4 rows = 16 feature cards
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenComplete(
    onCreateRequest: () -> Unit = {},
    onMyRequests: () -> Unit = {},
    onAssignedToMe: () -> Unit = {},
    onDrafts: () -> Unit = {},
    onTemplates: () -> Unit = {},
    onReports: () -> Unit = {},
    onBoardClick: () -> Unit = {},
    onRoadmapClick: () -> Unit = {},
    onAnalytics: () -> Unit = {},
    onDepartments: () -> Unit = {},
    onUsers: () -> Unit = {},
    onWorkflows: () -> Unit = {},
    onSettings: () -> Unit = {},
    onNotifications: () -> Unit = {},
    onCalendar: () -> Unit = {},
    onArchive: () -> Unit = {},
    onEmployees: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "iRequest Dashboard",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryBlue,
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = onNotifications) {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAFAFA))
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Welcome Section
            Text(
                text = "Welcome Back!",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color(0xFF101828)
            )
            
            Text(
                text = "Quick access to all features",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF667085)
            )
            
            // Feature Grid - 4 columns
            CompactFeatureGrid(
                onCreateRequest = onCreateRequest,
                onMyRequests = onMyRequests,
                onAssignedToMe = onAssignedToMe,
                onDrafts = onDrafts,
                onTemplates = onTemplates,
                onReports = onReports,
                onBoardClick = onBoardClick,
                onRoadmapClick = onRoadmapClick,
                onAnalytics = onAnalytics,
                onDepartments = onDepartments,
                onUsers = onUsers,
                onWorkflows = onWorkflows,
                onSettings = onSettings,
                onCalendar = onCalendar,
                onArchive = onArchive,
                onNotifications = onNotifications,
                onEmployees = onEmployees
            )
        }
    }
}

// ==================== COMPACT FEATURE GRID ====================
/**
 * Data class cho Feature Card
 */
data class FeatureItem(
    val id: String,
    val title: String,
    val icon: String,
    val onClick: () -> Unit
)

@Composable
private fun CompactFeatureGrid(
    onCreateRequest: () -> Unit,
    onMyRequests: () -> Unit,
    onAssignedToMe: () -> Unit,
    onDrafts: () -> Unit,
    onTemplates: () -> Unit,
    onReports: () -> Unit,
    onBoardClick: () -> Unit,
    onRoadmapClick: () -> Unit,
    onAnalytics: () -> Unit,
    onDepartments: () -> Unit,
    onUsers: () -> Unit,
    onWorkflows: () -> Unit,
    onSettings: () -> Unit,
    onCalendar: () -> Unit,
    onArchive: () -> Unit,
    onNotifications: () -> Unit,
    onEmployees: () -> Unit
) {
    val features = listOf(
        // Row 1
        FeatureItem("create", "Create Request", "➕", onCreateRequest),
        FeatureItem("my_requests", "My Requests", "📋", onMyRequests),
        FeatureItem("assigned", "Assigned To Me", "👤", onAssignedToMe),
        FeatureItem("board", "Kanban Board", "📊", onBoardClick),
        
        // Row 2
        FeatureItem("reports", "Reports", "📈", onReports),
        FeatureItem("analytics", "Analytics", "💹", onAnalytics),
        FeatureItem("roadmap", "Roadmap", "🗺️", onRoadmapClick),
        FeatureItem("calendar", "Calendar", "📅", onCalendar),
        
        // Row 3
        FeatureItem("departments", "Departments", "🏢", onDepartments),
        FeatureItem("employees", "Employees", "👥", onEmployees),
        FeatureItem("workflows", "Workflows", "⚙️", onWorkflows),
        FeatureItem("templates", "Templates", "📄", onTemplates),
        
        // Row 4
        FeatureItem("drafts", "Drafts", "✏️", onDrafts),
        FeatureItem("archive", "Archive", "📦", onArchive),
        FeatureItem("notifications", "Notifications", "🔔", onNotifications),
        FeatureItem("settings", "Settings", "⚙️", onSettings)
    )
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(features) { feature ->
            CompactFeatureCard(
                title = feature.title,
                icon = feature.icon,
                onClick = feature.onClick
            )
        }
    }
}

@Composable
private fun CompactFeatureCard(
    title: String,
    icon: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Card chỉ chứa icon
        Surface(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
                .clickable(onClick = onClick),
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 4.dp
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = icon,
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        
        // Title text nằm ngoài card
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Medium
            ),
            color = Color(0xFF344054),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreenComplete()
    }
}
