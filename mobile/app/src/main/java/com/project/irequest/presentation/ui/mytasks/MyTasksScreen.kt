package com.project.irequest.presentation.ui.mytasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.project.irequest.presentation.theme.CustomGreen
import com.project.irequest.presentation.theme.CustomOrange
import com.project.irequest.presentation.theme.CustomRed
import com.project.irequest.presentation.theme.PrimaryBlue

/**
 * My Tasks Screen - Work Center for Processing Requests
 * This is the core workflow screen for handling multi-workflow requests
 * 
 * Mapped from C# Controllers:
 * - RequestController.MyTasks()
 * - MultiWorkflowController.ProcessTask()
 * - WorkflowController.GetPendingTasks()
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("FunctionName")
fun MyTasksScreen(
    onTaskClick: (String) -> Unit = {},
    onCreateRequest: () -> Unit = {},
    onQuickApprove: (String) -> Unit = {},
    onQuickReject: (String) -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    
    // Task categories based on multi-workflow system
    val tabs = listOf(
        Pair("To Process", 8),      // Pending tasks requiring action
        Pair("Waiting", 3),          // Tasks waiting for others
        Pair("Processed", 0),        // Tasks I've completed
        Pair("My Requests", 5)       // Requests I created
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "My Tasks",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Work Center",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Open filter dialog */ }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Filter tasks"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateRequest,
                containerColor = PrimaryBlue,
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Create New Request",
                        tint = Color.White
                    )
                }
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
                                                containerColor = if (selectedTab == index) 
                                                    PrimaryBlue else MaterialTheme.colorScheme.primary
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
            
            // Task List based on selected tab
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Sample data - replace with ViewModel data
                val taskCount = when (selectedTab) {
                    0 -> 8  // To Process
                    1 -> 3  // Waiting
                    2 -> 10 // Processed
                    3 -> 5  // My Requests
                    else -> 0
                }
                
                items(taskCount) { index ->
                    TaskListItem(
                        taskId = "TASK-${String.format("%03d", index + 1)}",
                        requestId = "REQ-${String.format("%03d", index + 1)}",
                        title = when (selectedTab) {
                            0 -> "Approve Laptop Request for Nguyen Van A"
                            1 -> "Waiting for Department Head approval"
                            2 -> "Processed: Office Supply Request"
                            3 -> "My Request: New Equipment"
                            else -> "Task $index"
                        },
                        workflowStep = when (selectedTab) {
                            0 -> "Manager Approval"
                            1 -> "Department Head Review"
                            2 -> "Completed"
                            3 -> "In Progress"
                            else -> "Unknown"
                        },
                        priority = when (index % 3) {
                            0 -> "High"
                            1 -> "Medium"
                            else -> "Low"
                        },
                        dueDate = when (selectedTab) {
                            0 -> "${2 - index} days left"
                            1 -> "Waiting"
                            2 -> "Completed ${index + 1} days ago"
                            3 -> "${index + 1} days ago"
                            else -> ""
                        },
                        showQuickActions = selectedTab == 0, // Show quick actions for "To Process"
                        onTaskClick = { onTaskClick("TASK-${String.format("%03d", index + 1)}") },
                        onQuickApprove = { onQuickApprove("TASK-${String.format("%03d", index + 1)}") },
                        onQuickReject = { onQuickReject("TASK-${String.format("%03d", index + 1)}") }
                    )
                }
            }
        }
    }
}

@Composable
private fun TaskListItem(
    taskId: String,
    requestId: String,
    title: String,
    workflowStep: String,
    priority: String,
    dueDate: String,
    showQuickActions: Boolean = false,
    onTaskClick: () -> Unit = {},
    onQuickApprove: () -> Unit = {},
    onQuickReject: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onTaskClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header: Task ID + Request ID
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = taskId,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryBlue
                    )
                )
                Text(
                    text = requestId,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Task Title
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Workflow Step + Priority + Due Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Workflow Step Badge
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = PrimaryBlue.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = workflowStep,
                        style = MaterialTheme.typography.labelSmall,
                        color = PrimaryBlue,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                
                // Priority Badge
                val priorityColor = when (priority) {
                    "High" -> CustomRed
                    "Medium" -> CustomOrange
                    else -> CustomGreen
                }
                
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = priorityColor.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = priority,
                        style = MaterialTheme.typography.labelSmall,
                        color = priorityColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                // Due Date
                Text(
                    text = dueDate,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (dueDate.contains("left") && !dueDate.startsWith("0")) 
                        CustomOrange else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Quick Action Buttons (only for "To Process" tab)
            if (showQuickActions) {
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onQuickReject,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Reject",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Reject")
                    }
                    
                    androidx.compose.material3.Button(
                        onClick = onQuickApprove,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = CustomGreen
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Approve",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Approve")
                    }
                }
            }
        }
    }
}
