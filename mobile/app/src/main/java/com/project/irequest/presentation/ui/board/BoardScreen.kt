package com.project.irequest.presentation.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.irequest.data.models.Request
import com.example.irequest.data.repository.FirebaseRequestRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.project.irequest.presentation.theme.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoardScreen(
    onBack: () -> Unit = {},
    onRequestClick: (Int) -> Unit = {}
) {
    // Create repository instance
    val repository = remember {
        FirebaseRequestRepository(
            firestore = FirebaseFirestore.getInstance(),
            auth = FirebaseAuth.getInstance()
        )
    }
    
    // State management
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var backlogRequests by remember { mutableStateOf<List<Request>>(emptyList()) }
    var todoRequests by remember { mutableStateOf<List<Request>>(emptyList()) }
    var inProgressRequests by remember { mutableStateOf<List<Request>>(emptyList()) }
    var reviewRequests by remember { mutableStateOf<List<Request>>(emptyList()) }
    var doneRequests by remember { mutableStateOf<List<Request>>(emptyList()) }
    
    // Card action states
    var selectedRequest by remember { mutableStateOf<Request?>(null) }
    var showCardDetailDialog by remember { mutableStateOf(false) }
    var showAddCardDialog by remember { mutableStateOf(false) }
    var showEditCardDialog by remember { mutableStateOf(false) }
    var showDeleteCardDialog by remember { mutableStateOf(false) }
    var addCardToColumn by remember { mutableStateOf("") }
    
    val scope = rememberCoroutineScope()
    
    // Load requests on first composition
    LaunchedEffect(Unit) {
        scope.launch {
            isLoading = true
            errorMessage = null
            
            try {
                // Check if user is authenticated
                val currentUser = FirebaseAuth.getInstance().currentUser
                
                if (currentUser == null) {
                    // Try to sign in anonymously or use a test account
                    try {
                        // Sign in with test account from seeded data
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(
                            "john.doe@company.com",
                            "password123"
                        ).await()
                    } catch (authError: Exception) {
                        // Try anonymous auth as fallback
                        FirebaseAuth.getInstance().signInAnonymously().await()
                    }
                }
                
                // Load ALL requests for kanban board view (not just user's requests)
                val result = repository.getAllRequests(pageSize = 100)
                result.onSuccess { requests ->
                    // Group requests by status
                    backlogRequests = requests.filter { 
                        it.statusName?.contains("Backlog", ignoreCase = true) == true ||
                        it.statusName?.contains("New", ignoreCase = true) == true
                    }
                    todoRequests = requests.filter { 
                        it.statusName?.contains("To Do", ignoreCase = true) == true ||
                        it.statusName?.contains("Pending", ignoreCase = true) == true
                    }
                    inProgressRequests = requests.filter { 
                        it.statusName?.contains("In Progress", ignoreCase = true) == true ||
                        it.statusName?.contains("Processing", ignoreCase = true) == true
                    }
                    reviewRequests = requests.filter { 
                        it.statusName?.contains("Review", ignoreCase = true) == true ||
                        it.statusName?.contains("Approval", ignoreCase = true) == true
                    }
                    doneRequests = requests.filter { 
                        it.statusName?.contains("Done", ignoreCase = true) == true ||
                        it.statusName?.contains("Completed", ignoreCase = true) == true ||
                        it.statusName?.contains("Closed", ignoreCase = true) == true
                    }
                    isLoading = false
                }.onFailure { error ->
                    errorMessage = error.message ?: "Failed to load requests"
                    isLoading = false
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error occurred"
                isLoading = false
            }
        }
    }
    
    // Show error dialog if needed
    errorMessage?.let { error ->
        AlertDialog(
            onDismissRequest = { errorMessage = null },
            title = { Text("Error") },
            text = { Text(error) },
            confirmButton = {
                TextButton(onClick = { errorMessage = null }) {
                    Text("OK")
                }
            }
        )
    }
    
    // Card Detail Dialog
    if (showCardDetailDialog && selectedRequest != null) {
        CardDetailDialog(
            request = selectedRequest!!,
            onDismiss = { showCardDetailDialog = false },
            onEdit = {
                showCardDetailDialog = false
                showEditCardDialog = true
            },
            onDelete = {
                showCardDetailDialog = false
                showDeleteCardDialog = true
            }
        )
    }
    
    // Add Card Dialog
    if (showAddCardDialog) {
        AddCardDialog(
            columnTitle = addCardToColumn,
            onDismiss = { showAddCardDialog = false },
            onConfirm = { title, description, priority ->
                // TODO: Add card to Firebase
                showAddCardDialog = false
            }
        )
    }
    
    // Edit Card Dialog
    if (showEditCardDialog && selectedRequest != null) {
        EditCardDialog(
            request = selectedRequest!!,
            onDismiss = { showEditCardDialog = false },
            onConfirm = { title, description, priority ->
                // TODO: Update card in Firebase
                showEditCardDialog = false
            }
        )
    }
    
    // Delete Card Dialog
    if (showDeleteCardDialog && selectedRequest != null) {
        DeleteCardDialog(
            request = selectedRequest!!,
            onDismiss = { showDeleteCardDialog = false },
            onConfirm = {
                // TODO: Delete card from Firebase
                showDeleteCardDialog = false
            }
        )
    }
    Scaffold(
        topBar = {
            // Custom TopAppBar với chiều cao tự động
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 8.dp), // Padding ôm sát content
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Navigation Icon
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                    
                    // Title - chiếm không gian giữa
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = "Công việc của tôi",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color(0xFF101828)
                        )
                    }
                    
                    // Actions
                    IconButton(onClick = { /* Filter */ }) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "Options",
                            tint = Color(0xFF667085)
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Create new card */ },
                containerColor = PrimaryBlue
            ) {
                Icon(Icons.Default.Add, "Add Card", tint = Color.White)
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAFAFA))
                .padding(padding)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = PrimaryBlue
                )
            } else {
                LazyRow(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Backlog Column
                    item {
                        BoardColumn(
                            title = "Backlog",
                            count = backlogRequests.size,
                            color = Color(0xFF6B7280),
                            requests = backlogRequests,
                            onCardClick = { request ->
                                selectedRequest = request
                                showCardDetailDialog = true
                            },
                            onAddCard = {
                                addCardToColumn = "Backlog"
                                showAddCardDialog = true
                            }
                        )
                    }
                    
                    // To Do Column
                    item {
                        BoardColumn(
                            title = "To Do",
                            count = todoRequests.size,
                            color = PrimaryBlue,
                            requests = todoRequests,
                            onCardClick = { request ->
                                selectedRequest = request
                                showCardDetailDialog = true
                            },
                            onAddCard = {
                                addCardToColumn = "To Do"
                                showAddCardDialog = true
                            }
                        )
                    }
                    
                    // In Progress Column
                    item {
                        BoardColumn(
                            title = "In Progress",
                            count = inProgressRequests.size,
                            color = CustomOrange,
                            requests = inProgressRequests,
                            onCardClick = { request ->
                                selectedRequest = request
                                showCardDetailDialog = true
                            },
                            onAddCard = {
                                addCardToColumn = "In Progress"
                                showAddCardDialog = true
                            }
                        )
                    }
                    
                    // Review Column
                    item {
                        BoardColumn(
                            title = "Review",
                            count = reviewRequests.size,
                            color = Color(0xFF8B5CF6),
                            requests = reviewRequests,
                            onCardClick = { request ->
                                selectedRequest = request
                                showCardDetailDialog = true
                            },
                            onAddCard = {
                                addCardToColumn = "Review"
                                showAddCardDialog = true
                            }
                        )
                    }
                    
                    // Done Column
                    item {
                        BoardColumn(
                            title = "Done",
                            count = doneRequests.size,
                            color = CustomGreen,
                            requests = doneRequests,
                            onCardClick = { request ->
                                selectedRequest = request
                                showCardDetailDialog = true
                            },
                            onAddCard = {
                                addCardToColumn = "Done"
                                showAddCardDialog = true
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BoardColumn(
    title: String,
    count: Int,
    color: Color,
    requests: List<Request>,
    onCardClick: (Request) -> Unit,
    onAddCard: () -> Unit
) {
    Surface(
        modifier = Modifier
            .width(300.dp)
            .fillMaxHeight(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Column Header
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
                        modifier = Modifier.size(12.dp),
                        shape = CircleShape,
                        color = color
                    ) {}
                    
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color(0xFF101828)
                    )
                }
                
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = color.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = count.toString(),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = color
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Cards
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(requests) { request ->
                    BoardCard(
                        request = request,
                        onClick = { onCardClick(request) }
                    )
                }
                
                // Add Card Button
                item {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = onAddCard),
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFF9FAFB),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            Color(0xFFEAECF0)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add card",
                                tint = Color(0xFF667085),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Add card",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF667085)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BoardCard(
    request: Request,
    onClick: () -> Unit
) {
    val priorityColor = when (request.priorityName?.lowercase()) {
        "high", "urgent", "critical" -> CustomRed
        "medium", "normal" -> CustomOrange
        "low" -> CustomGreen
        else -> Color(0xFF667085)
    }
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        color = Color.White,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEAECF0)),
        shadowElevation = 0.5.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Priority Badge & ID
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                request.priorityName?.let { priority ->
                    Surface(
                        shape = RoundedCornerShape(4.dp),
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
                
                Text(
                    text = "REQ-${request.requestId}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = PrimaryBlue
                )
            }
            
            // Title
            Text(
                text = request.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = Color(0xFF101828),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            HorizontalDivider(color = Color(0xFFEAECF0))
            
            // Assignee
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val assigneeName = request.assignedUserName ?: request.userName ?: "Unassigned"
                Surface(
                    shape = CircleShape,
                    color = PrimaryBlue.copy(alpha = 0.2f),
                    modifier = Modifier.size(24.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = assigneeName.firstOrNull()?.uppercase() ?: "?",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = PrimaryBlue
                        )
                    }
                }
                
                Text(
                    text = assigneeName,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF667085)
                )
            }
        }
    }
}
