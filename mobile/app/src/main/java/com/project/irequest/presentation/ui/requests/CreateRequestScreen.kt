package com.project.irequest.presentation.ui.requests

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.irequest.presentation.theme.PrimaryBlue
import kotlinx.coroutines.launch

/**
 * Create/Edit Request Screen - Feature 3
 * Form for creating or updating requests
 * - Input fields: title, description, category, priority
 * - Attachment management (Feature 4)
 * - Form validation
 * - Success/error messages via Snackbar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRequestScreen(
    requestId: Int? = null,
    onNavigateBack: () -> Unit = {},
    onRequestCreated: (Int) -> Unit = {}
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var selectedPriority by remember { mutableStateOf("") }
    var attachments by remember { mutableStateOf<List<AttachmentItem>>(emptyList()) }
    var isSaving by remember { mutableStateOf(false) }

    var categoryExpanded by remember { mutableStateOf(false) }
    var priorityExpanded by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val categories = listOf("Equipment", "Leave", "IT Support", "Purchasing", "Other")
    val priorities = listOf("Low", "Medium", "High", "Urgent")

    val isFormValid = title.isNotBlank() && description.isNotBlank() 
        && selectedCategory.isNotBlank() && selectedPriority.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (requestId != null) "Edit Request" else "Create Request",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryBlue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAFAFA))
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title Field
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Request Title *") },
                    placeholder = { Text("Enter request title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = title.isBlank() && title.isNotEmpty(),
                    shape = RoundedCornerShape(8.dp)
                )

                // Description Field
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description *") },
                    placeholder = { Text("Enter detailed description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    minLines = 4,
                    shape = RoundedCornerShape(8.dp)
                )

                // Category Dropdown
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedCategory,
                        onValueChange = {},
                        label = { Text("Category *") },
                        placeholder = { Text("Select category") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { categoryExpanded = true },
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                imageVector = if (categoryExpanded)
                                    Icons.Default.Close else Icons.Default.ArrowDropDown,
                                contentDescription = "dropdown"
                            )
                        },
                        shape = RoundedCornerShape(8.dp)
                    )

                    DropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = { categoryExpanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category) },
                                onClick = {
                                    selectedCategory = category
                                    categoryExpanded = false
                                }
                            )
                        }
                    }
                }

                // Priority Dropdown
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedPriority,
                        onValueChange = {},
                        label = { Text("Priority *") },
                        placeholder = { Text("Select priority") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { priorityExpanded = true },
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                imageVector = if (priorityExpanded)
                                    Icons.Default.Close else Icons.Default.ArrowDropDown,
                                contentDescription = "dropdown"
                            )
                        },
                        shape = RoundedCornerShape(8.dp)
                    )

                    DropdownMenu(
                        expanded = priorityExpanded,
                        onDismissRequest = { priorityExpanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        priorities.forEach { priority ->
                            DropdownMenuItem(
                                text = { Text(priority) },
                                onClick = {
                                    selectedPriority = priority
                                    priorityExpanded = false
                                }
                            )
                        }
                    }
                }

                // Attachments Section
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Attachments",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                "${attachments.size}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray
                            )
                        }

                        if (attachments.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .background(
                                        color = Color(0xFFF5F5F5),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable { 
                                        // TODO: Open file picker
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text("📁", fontSize = 28.sp)
                                    Text(
                                        "Tap to add files",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.Gray
                                    )
                                }
                            }
                        } else {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                attachments.forEach { attachment ->
                                    AttachmentItemView(
                                        attachment = attachment,
                                        onRemove = {
                                            attachments = attachments.filter { it.id != attachment.id }
                                        }
                                    )
                                }
                            }
                        }

                        OutlinedButton(
                            onClick = { 
                                // TODO: Open file picker
                                // For now, add a mock attachment
                                attachments = attachments + AttachmentItem(
                                    id = System.currentTimeMillis().toString(),
                                    name = "document_${attachments.size}.pdf",
                                    size = "512 KB",
                                    type = "PDF"
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("+ Add File")
                        }
                    }
                }

                // Form Validation Message
                if (title.isEmpty() || description.isEmpty() || selectedCategory.isEmpty() || selectedPriority.isEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFF3CD)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("⚠", fontSize = 18.sp, modifier = Modifier.padding(end = 8.dp))
                            Text(
                                "Please fill in all required fields (*)",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onNavigateBack,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            if (isFormValid) {
                                isSaving = true
                                scope.launch {
                                    // Simulate API call
                                    kotlinx.coroutines.delay(1500)
                                    snackbarHostState.showSnackbar(
                                        if (requestId != null) "Request updated successfully!"
                                        else "Request created successfully!"
                                    )
                                    isSaving = false
                                    // Navigate to detail or list after delay
                                    kotlinx.coroutines.delay(500)
                                    onRequestCreated(System.currentTimeMillis().toInt())
                                    onNavigateBack()
                                }
                            } else {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Please fill in all required fields")
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        enabled = isFormValid && !isSaving,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue
                        )
                    ) {
                        if (isSaving) {
                            Text("Saving...")
                        } else {
                            Text(
                                if (requestId != null) "Update" else "Create",
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

data class AttachmentItem(
    val id: String,
    val name: String,
    val size: String,
    val type: String
)

@Composable
private fun AttachmentItemView(
    attachment: AttachmentItem,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    attachment.name,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    "${attachment.type} • ${attachment.size}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }

            IconButton(
                onClick = onRemove,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Remove",
                    tint = Color.Gray
                )
            }
        }
    }
}
