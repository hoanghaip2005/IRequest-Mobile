package com.project.irequest.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.irequest.R
import com.project.irequest.presentation.theme.*

/**
 * Complete Home Dashboard - Inspired by Jira, ServiceNow, Zendesk
 * 
 * Key Features:
 * 1. Header với search và notifications
 * 2. Metrics Overview (KPI Cards)
 * 3. Quick Actions
 * 4. My Work Section (Priority tasks)
 * 5. Team Activity Feed
 * 6. Department Performance
 * 7. Recent Requests với filters
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenComplete(
    onCreateRequest: () -> Unit = {},
    onMyTasks: () -> Unit = {},
    onRequestClick: (String) -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onReportsClick: () -> Unit = {},
    onBoardClick: () -> Unit = {},
    onRoadmapClick: () -> Unit = {}
) {
    var selectedFilter by remember { mutableStateOf(0) }
    val filters = listOf("All", "My Tasks", "Assigned", "Following", "Overdue")
    
    Scaffold(
        topBar = {
            ModernHomeTopBar(
                onSearchClick = onSearchClick,
                onNotificationClick = onNotificationClick,
                onProfileClick = onProfileClick
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateRequest,
                containerColor = PrimaryBlue,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, "Create Request")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAFAFA))
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            // 1. Welcome Header với Greeting
            item {
                WelcomeHeader()
            }
            
            // 2. Metrics Overview Cards (KPI)
            item {
                MetricsOverviewSection()
            }
            
            // 3. Quick Actions Grid
            item {
                QuickActionsGrid(
                    onCreateRequest = onCreateRequest,
                    onMyTasks = onMyTasks
                )
            }
            
            // 4. My Priority Work Section
            item {
                PriorityWorkSection(onRequestClick = onRequestClick)
            }
            
            // 5. Quick Navigation Cards (Reports, Board, Roadmap)
            item {
                QuickNavigationSection(
                    onReportsClick = onReportsClick,
                    onBoardClick = onBoardClick,
                    onRoadmapClick = onRoadmapClick
                )
            }
            
            // 6. Filter Tabs
            item {
                FilterChipsRow(
                    selectedFilter = selectedFilter,
                    filters = filters,
                    onFilterSelected = { selectedFilter = it }
                )
            }
            
            // 7. Requests List với enhanced UI
            item {
                EnhancedRequestsList(
                    filter = filters[selectedFilter],
                    onRequestClick = onRequestClick
                )
            }
            
            // 8. Team Activity Feed
            item {
                TeamActivitySection()
            }
            
            // 9. Department Performance
            item {
                DepartmentPerformanceSection()
            }
        }
    }
}

// ==================== TOP BAR ====================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModernHomeTopBar(
    onSearchClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Surface(
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Logo and Profile Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = PrimaryBlue.copy(alpha = 0.1f),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "iR",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryBlue
                                )
                            )
                        }
                    }
                    
                    Column {
                        Text(
                            text = "iRequest",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Manage your work",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF667085)
                        )
                    }
                }
                
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Notification với Badge
                    BadgedBox(
                        badge = {
                            Badge(
                                containerColor = CustomRed,
                                contentColor = Color.White
                            ) {
                                Text("12")
                            }
                        }
                    ) {
                        IconButton(onClick = onNotificationClick) {
                            Text(
                                text = "🔔",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                    
                    // Profile Avatar
                    Surface(
                        modifier = Modifier
                            .size(40.dp)
                            .clickable(onClick = onProfileClick),
                        shape = CircleShape,
                        color = CustomGreen.copy(alpha = 0.2f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "NA",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = CustomGreen
                                )
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Enhanced Search Bar
            EnhancedSearchBar(onSearchClick = onSearchClick)
        }
    }
}

// ==================== WELCOME HEADER ====================
@Composable
private fun WelcomeHeader() {
    val currentHour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
    val greeting = when (currentHour) {
        in 0..11 -> "Good Morning"
        in 12..17 -> "Good Afternoon"
        else -> "Good Evening"
    }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "$greeting, Nguyễn Văn A",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = Color(0xFF101828)
        )
        Text(
            text = "You have 8 pending tasks today",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF667085)
        )
    }
}

// ==================== METRICS OVERVIEW ====================
@Composable
private fun MetricsOverviewSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Overview",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = Color(0xFF101828)
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // KPI Cards Row 1
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MetricCard(
                modifier = Modifier.weight(1f),
                title = "Total Requests",
                value = "156",
                change = "+12%",
                isPositive = true,
                emoji = "📋",
                color = PrimaryBlue
            )
            
            MetricCard(
                modifier = Modifier.weight(1f),
                title = "Pending",
                value = "42",
                change = "+5",
                isPositive = false,
                emoji = "⏳",
                color = CustomOrange
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // KPI Cards Row 2
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MetricCard(
                modifier = Modifier.weight(1f),
                title = "Completed",
                value = "98",
                change = "+8%",
                isPositive = true,
                emoji = "✅",
                color = CustomGreen
            )
            
            MetricCard(
                modifier = Modifier.weight(1f),
                title = "Overdue",
                value = "16",
                change = "-3",
                isPositive = true,
                emoji = "⚠️",
                color = CustomRed
            )
        }
    }
}

@Composable
private fun MetricCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    change: String,
    isPositive: Boolean,
    emoji: String,
    color: Color
) {
    Surface(
        modifier = modifier.height(110.dp),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF667085)
                )
                
                Surface(
                    shape = CircleShape,
                    color = color.copy(alpha = 0.1f),
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = emoji,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
            
            Column {
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color(0xFF101828)
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isPositive) "↑" else "↓",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isPositive) CustomGreen else CustomRed
                    )
                    Text(
                        text = change,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isPositive) CustomGreen else CustomRed
                    )
                    Text(
                        text = "vs last month",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF667085)
                    )
                }
            }
        }
    }
}

// ==================== QUICK ACTIONS ====================
@Composable
private fun QuickActionsGrid(
    onCreateRequest: () -> Unit,
    onMyTasks: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Quick Actions",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = Color(0xFF101828)
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickActionCard(
                modifier = Modifier.weight(1f),
                title = "Create Request",
                subtitle = "New ticket",
                emoji = "➕",
                gradient = Brush.linearGradient(
                    colors = listOf(PrimaryBlue, PrimaryBlue.copy(alpha = 0.8f))
                ),
                onClick = onCreateRequest
            )
            
            QuickActionCard(
                modifier = Modifier.weight(1f),
                title = "My Tasks",
                subtitle = "8 pending",
                emoji = "📋",
                gradient = Brush.linearGradient(
                    colors = listOf(CustomOrange, CustomOrange.copy(alpha = 0.8f))
                ),
                onClick = onMyTasks
            )
        }
    }
}

@Composable
private fun QuickActionCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    emoji: String,
    gradient: Brush,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .height(100.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 2.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = emoji,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
                
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
            
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.5f),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(20.dp)
            )
        }
    }
}

// ==================== QUICK NAVIGATION SECTION ====================
@Composable
private fun QuickNavigationSection(
    onReportsClick: () -> Unit,
    onBoardClick: () -> Unit,
    onRoadmapClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Explore",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = Color(0xFF101828)
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Reports Card
            NavigationCard(
                modifier = Modifier.weight(1f),
                title = "Reports",
                subtitle = "Analytics",
                emoji = "📊",
                backgroundColor = Color(0xFF6366F1),
                onClick = onReportsClick
            )
            
            // Board Card
            NavigationCard(
                modifier = Modifier.weight(1f),
                title = "Board",
                subtitle = "Kanban",
                emoji = "📋",
                backgroundColor = Color(0xFF8B5CF6),
                onClick = onBoardClick
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Roadmap Card (Full Width)
        NavigationCard(
            modifier = Modifier.fillMaxWidth(),
            title = "Roadmap",
            subtitle = "Project timeline & milestones",
            emoji = "🗺️",
            backgroundColor = Color(0xFF06B6D4),
            onClick = onRoadmapClick,
            isFullWidth = true
        )
    }
}

@Composable
private fun NavigationCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    emoji: String,
    backgroundColor: Color,
    onClick: () -> Unit,
    isFullWidth: Boolean = false
) {
    Surface(
        modifier = modifier
            .height(if (isFullWidth) 80.dp else 100.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
        shadowElevation = 2.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (isFullWidth) {
                // Horizontal layout for full width card
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = emoji,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        
                        Column {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = Color.White
                            )
                            Text(
                                text = subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                    
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.size(24.dp)
                    )
                }
            } else {
                // Vertical layout for grid cards
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = emoji,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color.White
                        )
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
                
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.5f),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(18.dp)
                )
            }
        }
    }
}

// ==================== PRIORITY WORK SECTION ====================
@Composable
private fun PriorityWorkSection(onRequestClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Priority Work",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = Color(0xFF101828)
            )
            
            TextButton(onClick = { /* View all */ }) {
                Text("View all", color = PrimaryBlue)
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = PrimaryBlue,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Horizontal scroll của priority tasks
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(3) { index ->
                PriorityTaskCard(
                    requestId = "REQ-00${index + 1}",
                    title = "Laptop request approval needed",
                    department = "IT Department",
                    priority = "High",
                    dueIn = "${index + 1}h",
                    onClick = { onRequestClick("REQ-00${index + 1}") }
                )
            }
        }
    }
}

@Composable
private fun PriorityTaskCard(
    requestId: String,
    title: String,
    department: String,
    priority: String,
    dueIn: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .width(280.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 1.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, CustomRed.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = CustomRed.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = priority,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = CustomRed
                    )
                }
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "⏰",
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = "Due in $dueIn",
                        style = MaterialTheme.typography.labelSmall,
                        color = CustomOrange
                    )
                }
            }
            
            Text(
                text = requestId,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = PrimaryBlue
            )
            
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = Color(0xFF101828),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Divider(color = Color(0xFFEAECF0))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = PrimaryBlue.copy(alpha = 0.1f),
                        modifier = Modifier.size(24.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "IT",
                                style = MaterialTheme.typography.labelSmall,
                                color = PrimaryBlue
                            )
                        }
                    }
                    
                    Text(
                        text = department,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF667085)
                    )
                }
                
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color(0xFF667085),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

// ==================== FILTER CHIPS ====================
@Composable
private fun FilterChipsRow(
    selectedFilter: Int,
    filters: List<String>,
    onFilterSelected: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(filters.size) { index ->
            val isSelected = selectedFilter == index
            FilterChip(
                selected = isSelected,
                onClick = { onFilterSelected(index) },
                label = {
                    Text(
                        text = filters[index],
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                        )
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = PrimaryBlue,
                    selectedLabelColor = Color.White,
                    containerColor = Color.White,
                    labelColor = Color(0xFF667085)
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = Color(0xFFEAECF0),
                    selectedBorderColor = PrimaryBlue,
                    enabled = true,
                    selected = isSelected
                )
            )
        }
    }
}

// ==================== ENHANCED REQUESTS LIST ====================
@Composable
private fun EnhancedRequestsList(
    filter: String,
    onRequestClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Requests",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = Color(0xFF101828)
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Sample requests
        repeat(5) { index ->
            EnhancedRequestCard(
                requestId = "REQ-${1000 + index}",
                title = "Laptop RAM upgrade request",
                description = "Need to upgrade RAM from 8GB to 16GB for development work",
                status = listOf("Pending", "In Progress", "Review", "Approved", "Completed")[index % 5],
                priority = listOf("High", "Medium", "Low", "Medium", "High")[index % 5],
                assignee = "John Doe",
                createdDate = "2 hours ago",
                onClick = { onRequestClick("REQ-${1000 + index}") }
            )
            
            if (index < 4) {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun EnhancedRequestCard(
    requestId: String,
    title: String,
    description: String,
    status: String,
    priority: String,
    assignee: String,
    createdDate: String,
    onClick: () -> Unit
) {
    val statusColor = when (status) {
        "Completed" -> CustomGreen
        "In Progress" -> PrimaryBlue
        "Pending" -> CustomOrange
        "Review" -> Color(0xFF8B5CF6)
        else -> Color(0xFF667085)
    }
    
    val priorityColor = when (priority) {
        "High" -> CustomRed
        "Medium" -> CustomOrange
        "Low" -> CustomGreen
        else -> Color(0xFF667085)
    }
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = requestId,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = PrimaryBlue
                    )
                    
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = priorityColor.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = priority,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = priorityColor
                        )
                    }
                }
                
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = statusColor.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = status,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = statusColor
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = Color(0xFF101828)
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // Description
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF667085),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Divider(color = Color(0xFFEAECF0))
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Footer Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = CustomGreen.copy(alpha = 0.2f),
                        modifier = Modifier.size(28.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = assignee.split(" ").map { it.first() }.joinToString(""),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = CustomGreen
                            )
                        }
                    }
                    
                    Column {
                        Text(
                            text = assignee,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = Color(0xFF101828)
                        )
                        Text(
                            text = createdDate,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF667085)
                        )
                    }
                }
                
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color(0xFF667085)
                )
            }
        }
    }
}

// ==================== TEAM ACTIVITY ====================
@Composable
private fun TeamActivitySection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Team Activity",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = Color(0xFF101828)
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
            shadowElevation = 1.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                repeat(3) { index ->
                    ActivityItem(
                        userName = listOf("John Doe", "Jane Smith", "Mike Johnson")[index],
                        action = listOf("approved", "commented on", "completed")[index],
                        targetRequest = "REQ-${1050 + index}",
                        timeAgo = "${index + 1}h ago"
                    )
                    
                    if (index < 2) {
                        Divider(color = Color(0xFFEAECF0))
                    }
                }
            }
        }
    }
}

@Composable
private fun ActivityItem(
    userName: String,
    action: String,
    targetRequest: String,
    timeAgo: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = CircleShape,
            color = PrimaryBlue.copy(alpha = 0.2f),
            modifier = Modifier.size(36.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = userName.split(" ").map { it.first() }.joinToString(""),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = PrimaryBlue
                )
            }
        }
        
        Column(modifier = Modifier.weight(1f)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color(0xFF101828)
                )
                Text(
                    text = action,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF667085)
                )
                Text(
                    text = targetRequest,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = PrimaryBlue
                )
            }
            
            Text(
                text = timeAgo,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF667085)
            )
        }
    }
}

// ==================== DEPARTMENT PERFORMANCE ====================
@Composable
private fun DepartmentPerformanceSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Department Performance",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = Color(0xFF101828)
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
            shadowElevation = 1.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val departments = listOf(
                    Triple("IT Department", 45, 0.85f),
                    Triple("HR Department", 32, 0.72f),
                    Triple("Finance", 28, 0.68f)
                )
                
                departments.forEachIndexed { index, (name, count, progress) ->
                    DepartmentPerformanceItem(
                        departmentName = name,
                        requestCount = count,
                        completionRate = progress
                    )
                    
                    if (index < departments.size - 1) {
                        Divider(color = Color(0xFFEAECF0))
                    }
                }
            }
        }
    }
}

@Composable
private fun DepartmentPerformanceItem(
    departmentName: String,
    requestCount: Int,
    completionRate: Float
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = PrimaryBlue.copy(alpha = 0.1f),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = departmentName.split(" ").first().take(2).uppercase(),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = PrimaryBlue
                        )
                    }
                }
                
                Column {
                    Text(
                        text = departmentName,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = Color(0xFF101828)
                    )
                    Text(
                        text = "$requestCount requests",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF667085)
                    )
                }
            }
            
            Text(
                text = "${(completionRate * 100).toInt()}%",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = if (completionRate >= 0.8f) CustomGreen else CustomOrange
            )
        }
        
        LinearProgressIndicator(
            progress = { completionRate },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = if (completionRate >= 0.8f) CustomGreen else CustomOrange,
            trackColor = Color(0xFFEAECF0)
        )
    }
}

// ==================== ENHANCED SEARCH BAR ====================
@Composable
private fun EnhancedSearchBar(onSearchClick: () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    val searchPlaceholders = listOf(
        "Search requests...",
        "Find by ID or title...",
        "Search tasks, people...",
        "Type to search..."
    )
    var currentPlaceholder by remember { mutableStateOf(searchPlaceholders[0]) }
    
    Column(modifier = Modifier.fillMaxWidth()) {
        // Main Search Bar
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clickable { 
                    isExpanded = !isExpanded
                    onSearchClick()
                },
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
            shadowElevation = if (isExpanded) 4.dp else 1.dp,
            border = androidx.compose.foundation.BorderStroke(
                width = if (isExpanded) 2.dp else 1.dp,
                color = if (isExpanded) PrimaryBlue else Color(0xFFEAECF0)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Search Icon
                Surface(
                    shape = CircleShape,
                    color = PrimaryBlue.copy(alpha = 0.1f),
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = PrimaryBlue,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                
                // Search Text/Placeholder
                Text(
                    text = currentPlaceholder,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF667085),
                    modifier = Modifier.weight(1f)
                )
                
                // Voice Search Icon
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFF9FAFB),
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "🎤",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 16.sp
                        )
                    }
                }
                
                // Filter Icon
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFF9FAFB),
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "⚙️",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
        
        // Quick Filter Chips (shown when expanded)
        if (isExpanded) {
            Spacer(modifier = Modifier.height(12.dp))
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 0.dp)
            ) {
                items(
                    listOf(
                        "🔍 All" to Color(0xFF6366F1),
                        "📋 Requests" to PrimaryBlue,
                        "✓ Tasks" to CustomGreen,
                        "👥 People" to CustomOrange,
                        "📊 Reports" to Color(0xFF8B5CF6)
                    )
                ) { (label, color) ->
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = color.copy(alpha = 0.1f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.3f))
                    ) {
                        Text(
                            text = label,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = color
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Recent Searches
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Recent Searches",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = Color(0xFF101828)
                        )
                        Text(
                            text = "Clear",
                            style = MaterialTheme.typography.labelMedium,
                            color = PrimaryBlue,
                            modifier = Modifier.clickable { }
                        )
                    }
                    
                    HorizontalDivider(color = Color(0xFFEAECF0))
                    
                    listOf(
                        "REQ-1234" to "⏱️",
                        "Laptop request" to "💻",
                        "John Doe" to "👤",
                        "Pending approvals" to "⏳"
                    ).forEach { (text, icon) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { }
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = icon,
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 18.sp
                            )
                            Text(
                                text = text,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF344054),
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null,
                                tint = Color(0xFF667085),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
