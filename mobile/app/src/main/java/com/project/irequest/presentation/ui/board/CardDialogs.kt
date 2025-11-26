package com.project.irequest.presentation.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.irequest.data.models.Request
import com.project.irequest.presentation.theme.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Card Detail Dialog - Hiển thị thông tin chi tiết của card
 */
@Composable
fun CardDetailDialog(
    request: Request,
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "REQ-${request.requestId}",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = PrimaryBlue
                    )
                    
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(onClick = onEdit) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = Color(0xFF667085)
                            )
                        }
                        IconButton(onClick = onDelete) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = CustomRed
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Content - Scrollable
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Priority Badge
                    request.priorityName?.let { priority ->
                        val priorityColor = when (priority.lowercase()) {
                            "high", "urgent", "critical" -> CustomRed
                            "medium", "normal" -> CustomOrange
                            "low" -> CustomGreen
                            else -> Color(0xFF667085)
                        }
                        
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = priorityColor.copy(alpha = 0.1f)
                        ) {
                            Text(
                                text = priority,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = priorityColor
                            )
                        }
                    }
                    
                    // Title
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Title",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = Color(0xFF667085)
                        )
                        Text(
                            text = request.title,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = Color(0xFF101828)
                        )
                    }
                    
                    HorizontalDivider(color = Color(0xFFEAECF0))
                    
                    // Description
                    request.description?.let { desc ->
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "Description",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = Color(0xFF667085)
                            )
                            Text(
                                text = desc,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF344054)
                            )
                        }
                    }
                    
                    HorizontalDivider(color = Color(0xFFEAECF0))
                    
                    // Status & Workflow
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Status",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = Color(0xFF667085)
                            )
                            Text(
                                text = request.statusName ?: "N/A",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF344054)
                            )
                        }
                        
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Workflow",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = Color(0xFF667085)
                            )
                            Text(
                                text = request.workflowName ?: "N/A",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF344054)
                            )
                        }
                    }
                    
                    HorizontalDivider(color = Color(0xFFEAECF0))
                    
                    // Assignee & Creator
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Assigned To",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = Color(0xFF667085)
                            )
                            Text(
                                text = request.assignedUserName ?: "Unassigned",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF344054)
                            )
                        }
                        
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Created By",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = Color(0xFF667085)
                            )
                            Text(
                                text = request.userName ?: "Unknown",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF344054)
                            )
                        }
                    }
                    
                    HorizontalDivider(color = Color(0xFFEAECF0))
                    
                    // Timestamps
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        request.createdAt?.let { date ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Created:",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF667085)
                                )
                                Text(
                                    text = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()).format(date),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.Medium
                                    ),
                                    color = Color(0xFF344054)
                                )
                            }
                        }
                        
                        request.updatedAt?.let { date ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Updated:",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF667085)
                                )
                                Text(
                                    text = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()).format(date),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.Medium
                                    ),
                                    color = Color(0xFF344054)
                                )
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Close Button
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF2F4F7)
                    )
                ) {
                    Text(
                        text = "Close",
                        color = Color(0xFF344054),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}

/**
 * Add Card Dialog - Thêm card mới
 */
@Composable
fun AddCardDialog(
    columnTitle: String,
    onDismiss: () -> Unit,
    onConfirm: (title: String, description: String, priority: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedPriority by remember { mutableStateOf("Medium") }
    val priorities = listOf("Low", "Medium", "High", "Urgent")
    
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
                Text(
                    text = "Add Card to $columnTitle",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color(0xFF101828)
                )
                
                // Title Input
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    placeholder = { Text("Enter card title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                
                // Description Input
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    placeholder = { Text("Enter description (optional)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    maxLines = 5
                )
                
                // Priority Selection
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Priority",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = Color(0xFF344054)
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        priorities.forEach { priority ->
                            val isSelected = priority == selectedPriority
                            val color = when (priority) {
                                "High", "Urgent" -> CustomRed
                                "Medium" -> CustomOrange
                                "Low" -> CustomGreen
                                else -> Color.Gray
                            }
                            
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedPriority = priority },
                                label = { Text(priority) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = color.copy(alpha = 0.2f),
                                    selectedLabelColor = color
                                )
                            )
                        }
                    }
                }
                
                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                    
                    Button(
                        onClick = {
                            if (title.isNotBlank()) {
                                onConfirm(title, description, selectedPriority)
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = title.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue
                        )
                    ) {
                        Text("Add Card")
                    }
                }
            }
        }
    }
}

/**
 * Edit Card Dialog - Chỉnh sửa card
 */
@Composable
fun EditCardDialog(
    request: Request,
    onDismiss: () -> Unit,
    onConfirm: (title: String, description: String, priority: String) -> Unit
) {
    var title by remember { mutableStateOf(request.title) }
    var description by remember { mutableStateOf(request.description ?: "") }
    var selectedPriority by remember { mutableStateOf(request.priorityName ?: "Medium") }
    val priorities = listOf("Low", "Medium", "High", "Urgent")
    
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
                Text(
                    text = "Edit Card REQ-${request.requestId}",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color(0xFF101828)
                )
                
                // Title Input
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                
                // Description Input
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    maxLines = 5
                )
                
                // Priority Selection
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Priority",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = Color(0xFF344054)
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        priorities.forEach { priority ->
                            val isSelected = priority == selectedPriority
                            val color = when (priority) {
                                "High", "Urgent" -> CustomRed
                                "Medium" -> CustomOrange
                                "Low" -> CustomGreen
                                else -> Color.Gray
                            }
                            
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedPriority = priority },
                                label = { Text(priority) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = color.copy(alpha = 0.2f),
                                    selectedLabelColor = color
                                )
                            )
                        }
                    }
                }
                
                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                    
                    Button(
                        onClick = {
                            if (title.isNotBlank()) {
                                onConfirm(title, description, selectedPriority)
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = title.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue
                        )
                    ) {
                        Text("Save Changes")
                    }
                }
            }
        }
    }
}

/**
 * Delete Card Dialog - Xác nhận xóa card
 */
@Composable
fun DeleteCardDialog(
    request: Request,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                Icons.Default.Delete,
                contentDescription = null,
                tint = CustomRed,
                modifier = Modifier.size(48.dp)
            )
        },
        title = {
            Text(
                text = "Delete Card?",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Are you sure you want to delete this card?",
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF9FAFB),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEAECF0))
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "REQ-${request.requestId}",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = PrimaryBlue
                        )
                        Text(
                            text = request.title,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF344054)
                        )
                    }
                }
                
                Text(
                    text = "This action cannot be undone.",
                    style = MaterialTheme.typography.bodySmall,
                    color = CustomRed
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CustomRed
                )
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

// ============================================
// PREVIEW SECTION
// ============================================

/**
 * Preview cho CardDetailDialog
 */
@Preview(showBackground = true)
@Composable
private fun PreviewCardDetailDialog() {
    IRequestTheme {
        CardDetailDialog(
            request = Request(
                id = "sample-id-123",
                requestId = 1001,
                title = "Fix login authentication bug",
                description = "Users are experiencing issues when trying to log in with their credentials. The error occurs intermittently and affects about 15% of users.",
                userId = "user-123",
                userName = "John Doe",
                priorityName = "High",
                statusName = "In Progress",
                workflowName = "Bug Fix Workflow",
                assignedUserId = "user-456",
                assignedUserName = "Jane Smith",
                createdAt = Date(),
                updatedAt = Date()
            ),
            onDismiss = {},
            onEdit = {},
            onDelete = {}
        )
    }
}

/**
 * Preview cho AddCardDialog
 */
@Preview(showBackground = true)
@Composable
private fun PreviewAddCardDialog() {
    IRequestTheme {
        AddCardDialog(
            columnTitle = "To Do",
            onDismiss = {},
            onConfirm = { _, _, _ -> }
        )
    }
}

/**
 * Preview cho EditCardDialog
 */
@Preview(showBackground = true)
@Composable
private fun PreviewEditCardDialog() {
    IRequestTheme {
        EditCardDialog(
            request = Request(
                id = "sample-id-456",
                requestId = 2002,
                title = "Implement new dashboard feature",
                description = "Create a new analytics dashboard that shows user activity metrics.",
                userId = "user-789",
                userName = "Mike Johnson",
                priorityName = "Medium",
                statusName = "To Do",
                workflowName = "Feature Development",
                assignedUserId = "user-101",
                assignedUserName = "Sarah Wilson",
                createdAt = Date(),
                updatedAt = Date()
            ),
            onDismiss = {},
            onConfirm = { _, _, _ -> }
        )
    }
}

/**
 * Preview cho DeleteCardDialog
 */
@Preview(showBackground = true)
@Composable
private fun PreviewDeleteCardDialog() {
    IRequestTheme {
        DeleteCardDialog(
            request = Request(
                id = "sample-id-789",
                requestId = 3003,
                title = "Update user profile API endpoint",
                description = "The API needs to be updated to support new profile fields.",
                userId = "user-999",
                userName = "Alex Brown",
                priorityName = "Low",
                statusName = "Review",
                workflowName = "API Enhancement"
            ),
            onDismiss = {},
            onConfirm = {}
        )
    }
}

/**
 * Preview cho tất cả priorities trong AddCardDialog
 */
@Preview(showBackground = true, name = "All Priority Options")
@Composable
private fun PreviewAddCardDialogWithPriorities() {
    IRequestTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9FAFB))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Priority Color Coding Preview",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color(0xFF101828)
            )
            
            val priorities = listOf(
                "Low" to CustomGreen,
                "Medium" to CustomOrange,
                "High" to CustomRed,
                "Urgent" to CustomRed
            )
            
            priorities.forEach { (priority, color) ->
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = color.copy(alpha = 0.1f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = priority,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = color
                        )
                        Surface(
                            shape = CircleShape,
                            color = color,
                            modifier = Modifier.size(12.dp)
                        ) {}
                    }
                }
            }
        }
    }
}
