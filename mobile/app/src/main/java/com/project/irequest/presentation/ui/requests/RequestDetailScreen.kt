package com.project.irequest.presentation.ui.requests

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.irequest.presentation.theme.CustomGreen
import com.project.irequest.presentation.theme.CustomRed
import com.project.irequest.presentation.theme.PrimaryBlue

/**
 * Request Detail Screen - Feature 2
 * Shows detailed information about a single request
 * - Full request details: title, description, category, priority, requester, assignee, deadline
 * - Status timeline
 * - Tabs: Details, Comments, Attachments, History
 * - Action buttons based on role: Edit, Approve, Reject, Cancel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestDetailScreen(
    requestId: Int,
    onNavigateBack: () -> Unit = {},
    onEditRequest: () -> Unit = {},
    onApproveRequest: () -> Unit = {},
    onRejectRequest: () -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Details", "Comments", "Attachments", "History")

    // Sample request data - in production this would come from ViewModel
    val request = DetailedRequest(
        id = requestId,
        code = "REQ-001",
        title = "Laptop Request for Development",
        description = "Request for a new laptop with following specs:\n- 16GB RAM\n- 512GB SSD\n- i7 Processor\nFor development purposes",
        category = "Equipment",
        priority = "High",
        priorityColor = "#FF6B6B",
        status = "Pending Approval",
        statusColor = "#FFA500",
        requester = "Nguyen Van A",
        requesterId = "user-001",
        assignee = "Smith Johnson",
        assigneeId = "agent-001",
        deadline = "2024-12-26",
        createdAt = "2024-11-26 10:00 AM",
        updatedAt = "2024-11-26 10:00 AM",
        slaStatus = "On Time (8 hours remaining)",
        comments = listOf(
            DetailComment("Agent Smith", "2024-11-26 11:30 AM", "I will review this request and get back to you soon."),
            DetailComment("Manager John", "2024-11-26 12:00 PM", "Please provide additional budget justification.")
        ),
        attachments = listOf(
            DetailAttachment("laptop_specs.pdf", "PDF", "512 KB"),
            DetailAttachment("budget_approval.jpg", "Image", "1.2 MB")
        ),
        history = listOf(
            HistoryEntry("Request Created", "2024-11-26 10:00 AM", "Nguyen Van A"),
            HistoryEntry("Assigned to Agent", "2024-11-26 10:30 AM", "System"),
            HistoryEntry("Comment Added", "2024-11-26 11:30 AM", "Agent Smith")
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Request Details",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = request.code,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onEditRequest) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryBlue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAFAFA))
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Status Header Card
            item {
                StatusHeaderCard(request)
            }

            // Request Info Card
            item {
                RequestInfoCard(request)
            }

            // Tabs
            item {
                TabRow(selectedTabIndex = selectedTab) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title) }
                        )
                    }
                }
            }

            // Tab Content
            item {
                when (selectedTab) {
                    0 -> RequestDetailsTabContent(request)
                    1 -> CommentsTabContent(request.comments)
                    2 -> AttachmentsTabContent(request.attachments)
                    3 -> HistoryTabContent(request.history)
                }
            }

            // Action Buttons
            item {
                RequestActionsSection(
                    onApprove = onApproveRequest,
                    onReject = onRejectRequest,
                    onEdit = onEditRequest
                )
            }

            // Bottom spacing
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

data class DetailedRequest(
    val id: Int,
    val code: String,
    val title: String,
    val description: String,
    val category: String,
    val priority: String,
    val priorityColor: String,
    val status: String,
    val statusColor: String,
    val requester: String,
    val requesterId: String,
    val assignee: String,
    val assigneeId: String,
    val deadline: String,
    val createdAt: String,
    val updatedAt: String,
    val slaStatus: String,
    val comments: List<DetailComment>,
    val attachments: List<DetailAttachment>,
    val history: List<HistoryEntry>
)

data class DetailComment(
    val author: String,
    val timestamp: String,
    val content: String
)

data class DetailAttachment(
    val name: String,
    val type: String,
    val size: String
)

data class HistoryEntry(
    val action: String,
    val timestamp: String,
    val actor: String
)

@Composable
private fun StatusHeaderCard(request: DetailedRequest) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = request.title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusBadge(status = request.status, color = request.statusColor)
                PriorityBadge(priority = request.priority)
            }

            Divider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Requester", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text(request.requester, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                }
                Column {
                    Text("Assignee", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text(request.assignee, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                }
                Column {
                    Text("Deadline", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text(request.deadline, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                }
            }

            Divider()

            Text(
                text = "SLA Status: ${request.slaStatus}",
                style = MaterialTheme.typography.labelMedium,
                color = CustomGreen,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun RequestInfoCard(request: DetailedRequest) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Description",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                request.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Divider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoField("Category", request.category)
                InfoField("Created", request.createdAt)
            }
        }
    }
}

@Composable
private fun InfoField(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        Text(value, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun RequestDetailsTabContent(request: DetailedRequest) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoRow("Code", request.code)
            InfoRow("Title", request.title)
            InfoRow("Category", request.category)
            InfoRow("Priority", request.priority)
            InfoRow("Status", request.status)
            InfoRow("Requester", request.requester)
            InfoRow("Assignee", request.assignee)
            InfoRow("Deadline", request.deadline)
            InfoRow("Created At", request.createdAt)
            InfoRow("Updated At", request.updatedAt)
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Text(
            value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f),
            maxLines = 1
        )
    }
}

@Composable
private fun CommentsTabContent(comments: List<DetailComment>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (comments.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No comments yet", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                }
            }
        } else {
            comments.forEach { comment ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                comment.author,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                comment.timestamp,
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray
                            )
                        }
                        Text(
                            comment.content,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AttachmentsTabContent(attachments: List<DetailAttachment>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (attachments.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No attachments", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                }
            }
        } else {
            attachments.forEach { attachment ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                attachment.name,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                "${attachment.type} • ${attachment.size}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray
                            )
                        }
                        Button(
                            onClick = { },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text("Open")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryTabContent(history: List<HistoryEntry>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        history.forEach { entry ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            entry.action,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            "by ${entry.actor}",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                    }
                    Text(
                        entry.timestamp,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
private fun RequestActionsSection(
    onApprove: () -> Unit,
    onReject: () -> Unit,
    onEdit: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Two-button row: Reject and Approve
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onReject,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = CustomRed
                )
            ) {
                Text("✕ Reject")
            }

            Button(
                onClick = onApprove,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CustomGreen
                )
            ) {
                Text("✓ Approve", color = Color.White)
            }
        }

        // Edit button
        OutlinedButton(
            onClick = onEdit,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Edit Request")
        }
    }
}

@Composable
private fun StatusBadge(status: String, color: String) {
    Box(
        modifier = Modifier
            .background(
                color = Color(android.graphics.Color.parseColor(color)).copy(alpha = 0.2f),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = status,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold
            ),
            color = Color(android.graphics.Color.parseColor(color))
        )
    }
}

@Composable
private fun PriorityBadge(priority: String) {
    val color = when (priority) {
        "Urgent" -> Color(0xFFE74C3C)
        "High" -> Color(0xFFFF6B6B)
        "Medium" -> Color(0xFFF39C12)
        "Low" -> Color(0xFF95A5A6)
        else -> Color.Gray
    }

    Box(
        modifier = Modifier
            .background(
                color = color.copy(alpha = 0.2f),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = priority,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold
            ),
            color = color
        )
    }
}
