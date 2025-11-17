package com.project.irequest.presentation.ui.alerts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.project.irequest.presentation.theme.CustomGreen
import com.project.irequest.presentation.theme.CustomOrange
import com.project.irequest.presentation.theme.CustomRed
import com.project.irequest.presentation.theme.PrimaryBlue

/**
 * Alerts Screen - Unified Notifications, Chat, and SLA Warnings
 * Combines multiple alert types into one convenient view
 * 
 * Mapped from C# Controllers:
 * - NotificationController (System notifications)
 * - ChatController (Chat messages via SignalR)
 * - SlaController.OverdueRequests() (SLA warnings)
 * - DashboardController.TimeoutOverview() (Timeout alerts)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("FunctionName")
fun AlertsScreen(
    onAlertClick: (String) -> Unit = {},
    onMarkAllRead: () -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    
    // Alert categories
    val tabs = listOf(
        Pair("All", 12),           // Total alerts
        Pair("Notifications", 5),  // System notifications
        Pair("Chat", 4),           // Chat messages
        Pair("SLA Warnings", 3)    // SLA/Timeout alerts
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Alerts",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "${tabs[selectedTab].second} unread",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onMarkAllRead) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Mark all as read"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Tabs with Badge Counts
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                tabs.forEachIndexed { index, (title, count) ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = if (selectedTab == index) 
                                            FontWeight.Bold else FontWeight.Normal
                                    )
                                )
                                if (count > 0) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    BadgedBox(
                                        badge = {
                                            Badge(
                                                containerColor = when {
                                                    index == 3 -> CustomRed // SLA warnings in red
                                                    selectedTab == index -> PrimaryBlue
                                                    else -> MaterialTheme.colorScheme.primary
                                                }
                                            ) {
                                                Text(
                                                    text = count.toString(),
                                                    style = MaterialTheme.typography.labelSmall
                                                )
                                            }
                                        }
                                    ) {}
                                }
                            }
                        }
                    )
                }
            }
            
            // Alerts List based on selected tab
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                when (selectedTab) {
                    0 -> { // All Alerts
                        items(12) { index ->
                            when {
                                index < 3 -> SlaWarningItem(
                                    requestId = "REQ-${String.format("%03d", index + 1)}",
                                    title = "Request Timeout Warning",
                                    message = "Request is approaching SLA deadline",
                                    timeLeft = "${3 - index} hours left",
                                    onClick = { onAlertClick("SLA-$index") }
                                )
                                index < 7 -> NotificationItem(
                                    title = "Request Status Update",
                                    message = "Your request has been approved by manager",
                                    time = "${index - 2} hours ago",
                                    type = AlertType.REQUEST_UPDATE,
                                    isUnread = true,
                                    onClick = { onAlertClick("NOTIF-$index") }
                                )
                                else -> ChatMessageItem(
                                    senderName = "Nguyen Van A",
                                    message = "Can you please review my request?",
                                    time = "${index - 6} minutes ago",
                                    unreadCount = 2,
                                    onClick = { onAlertClick("CHAT-$index") }
                                )
                            }
                        }
                    }
                    1 -> { // Notifications Only
                        items(5) { index ->
                            NotificationItem(
                                title = when (index % 3) {
                                    0 -> "Request Approved"
                                    1 -> "New Comment Added"
                                    else -> "Request Assigned"
                                },
                                message = when (index % 3) {
                                    0 -> "Your request REQ-${index + 1} has been approved"
                                    1 -> "New comment on request REQ-${index + 1}"
                                    else -> "You have been assigned to request REQ-${index + 1}"
                                },
                                time = "${index + 1} hours ago",
                                type = when (index % 3) {
                                    0 -> AlertType.REQUEST_APPROVED
                                    1 -> AlertType.COMMENT_ADDED
                                    else -> AlertType.TASK_ASSIGNED
                                },
                                isUnread = index < 3,
                                onClick = { onAlertClick("NOTIF-$index") }
                            )
                        }
                    }
                    2 -> { // Chat Messages Only
                        items(4) { index ->
                            ChatMessageItem(
                                senderName = listOf("Nguyen Van A", "Tran Thi B", "Le Van C", "Pham Thi D")[index],
                                message = "Message content preview goes here...",
                                time = "${index * 5} minutes ago",
                                unreadCount = if (index < 2) index + 1 else 0,
                                onClick = { onAlertClick("CHAT-$index") }
                            )
                        }
                    }
                    3 -> { // SLA Warnings Only
                        items(3) { index ->
                            SlaWarningItem(
                                requestId = "REQ-${String.format("%03d", index + 1)}",
                                title = when (index) {
                                    0 -> "Critical: SLA Exceeded"
                                    1 -> "Warning: Approaching Deadline"
                                    else -> "Timeout Risk"
                                },
                                message = when (index) {
                                    0 -> "Request has exceeded SLA deadline by 2 hours"
                                    1 -> "Request will timeout in 1 hour"
                                    else -> "Request at risk of missing deadline"
                                },
                                timeLeft = when (index) {
                                    0 -> "Overdue"
                                    1 -> "1 hour left"
                                    else -> "3 hours left"
                                },
                                onClick = { onAlertClick("SLA-$index") }
                            )
                        }
                    }
                }
            }
        }
    }
}

enum class AlertType {
    REQUEST_UPDATE,
    REQUEST_APPROVED,
    REQUEST_REJECTED,
    COMMENT_ADDED,
    TASK_ASSIGNED,
    CHAT_MESSAGE,
    SLA_WARNING
}

@Composable
private fun NotificationItem(
    title: String,
    message: String,
    time: String,
    type: AlertType,
    isUnread: Boolean = true,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isUnread) 
                PrimaryBlue.copy(alpha = 0.05f) 
            else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isUnread) 3.dp else 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Icon
            val (icon, iconColor) = when (type) {
                AlertType.REQUEST_APPROVED -> Pair(Icons.Default.CheckCircle, CustomGreen)
                AlertType.REQUEST_UPDATE -> Pair(Icons.Default.Info, PrimaryBlue)
                AlertType.COMMENT_ADDED -> Pair(Icons.Default.Email, CustomOrange)
                AlertType.TASK_ASSIGNED -> Pair(Icons.Default.Email, PrimaryBlue)
                else -> Pair(Icons.Default.Info, MaterialTheme.colorScheme.primary)
            }
            
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(iconColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            // Content
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = if (isUnread) FontWeight.Bold else FontWeight.SemiBold
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    
                    if (isUnread) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(PrimaryBlue)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = time,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun ChatMessageItem(
    senderName: String,
    message: String,
    time: String,
    unreadCount: Int = 0,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (unreadCount > 0) 
                CustomOrange.copy(alpha = 0.05f) 
            else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (unreadCount > 0) 3.dp else 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Avatar placeholder
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(CustomOrange.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = senderName.first().toString(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = CustomOrange
                    )
                )
            }
            
            // Content
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = senderName,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = if (unreadCount > 0) FontWeight.Bold else FontWeight.SemiBold
                        )
                    )
                    
                    if (unreadCount > 0) {
                        Badge(
                            containerColor = CustomOrange
                        ) {
                            Text(
                                text = unreadCount.toString(),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = time,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun SlaWarningItem(
    requestId: String,
    title: String,
    message: String,
    timeLeft: String,
    onClick: () -> Unit = {}
) {
    val isOverdue = timeLeft == "Overdue"
    val isCritical = timeLeft.contains("hour") && !timeLeft.contains("3")
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isOverdue -> CustomRed.copy(alpha = 0.1f)
                isCritical -> CustomOrange.copy(alpha = 0.1f)
                else -> PrimaryBlue.copy(alpha = 0.05f)
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Warning Icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            isOverdue -> CustomRed.copy(alpha = 0.2f)
                            isCritical -> CustomOrange.copy(alpha = 0.2f)
                            else -> PrimaryBlue.copy(alpha = 0.1f)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Warning,
                    contentDescription = null,
                    tint = when {
                        isOverdue -> CustomRed
                        isCritical -> CustomOrange
                        else -> PrimaryBlue
                    },
                    modifier = Modifier.size(24.dp)
                )
            }
            
            // Content
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = when {
                                isOverdue -> CustomRed
                                isCritical -> CustomOrange
                                else -> MaterialTheme.colorScheme.onSurface
                            }
                        )
                        
                        Text(
                            text = requestId,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = when {
                            isOverdue -> CustomRed
                            isCritical -> CustomOrange
                            else -> PrimaryBlue
                        }
                    ) {
                        Text(
                            text = timeLeft,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
