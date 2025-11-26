package com.project.irequest.presentation.ui.requests.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.irequest.presentation.theme.CustomGreen
import com.project.irequest.presentation.theme.CustomOrange
import com.project.irequest.presentation.theme.PrimaryBlue
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.imePadding

// --- DATA MODELS (Giả lập Backend) ---
data class Comment(
    val id: String,
    val userName: String,
    val content: String,
    val time: String,
    val isMe: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestDetailScreen(
    requestId: String, // Nhận ID từ màn hình Alerts chuyển sang
    onBackClick: () -> Unit
) {
    // State cho ô nhập bình luận
    var commentText by remember { mutableStateOf("") }

    // Dữ liệu bình luận giả lập
    val comments = remember { mutableStateListOf(
        Comment("1", "Manager", "Please clarify the budget for this device.", "2h ago", false),
        Comment("2", "You", "It is around $1500 as per policy.", "1h ago", true),
        Comment("3", "Manager", "Approved. Please proceed.", "10m ago", false)
    )}

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Request Detail", fontWeight = FontWeight.Bold)
                        Text(requestId, style = MaterialTheme.typography.bodySmall)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        // THANH NHẬP BÌNH LUẬN Ở ĐÁY (FEATURE 6)
        bottomBar = {
            CommentInputBar(
                text = commentText,
                onTextChange = { commentText = it },
                onSend = {
                    if (commentText.isNotBlank()) {
                        comments.add(Comment("4", "You", commentText, "Just now", true))
                        commentText = ""
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF8F9FA))
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // --- PHẦN 1: THÔNG TIN YÊU CẦU (FEATURE 3) ---
                item {
                    RequestInfoCard(
                        title = "New Laptop for Developer",
                        status = "In Progress",
                        priority = "High",
                        description = "My current laptop is lagging when compiling Android projects. Need a MacBook Pro M3."
                    )
                }

                item {
                    Text(
                        "Discussion",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                // --- PHẦN 2: DANH SÁCH BÌNH LUẬN (FEATURE 6) ---
                items(comments) { comment ->
                    CommentItem(comment)
                }
            }
        }
    }
}

// --- COMPONENTS ---

@Composable
fun RequestInfoCard(title: String, status: String, priority: String, description: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Tags
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(color = PrimaryBlue.copy(alpha = 0.1f), shape = RoundedCornerShape(4.dp)) {
                    Text(status, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), color = PrimaryBlue, style = MaterialTheme.typography.labelSmall)
                }
                Surface(color = CustomOrange.copy(alpha = 0.1f), shape = RoundedCornerShape(4.dp)) {
                    Text("Priority: $priority", modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), color = CustomOrange, style = MaterialTheme.typography.labelSmall)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(title, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(8.dp))
            Text(description, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (comment.isMe) Arrangement.End else Arrangement.Start
    ) {
        if (!comment.isMe) {
            Box(
                modifier = Modifier.size(32.dp).clip(CircleShape).background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Outlined.Person, contentDescription = null, tint = Color.White)
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(horizontalAlignment = if (comment.isMe) Alignment.End else Alignment.Start) {
            Surface(
                shape = RoundedCornerShape(12.dp).copy(
                    topStart = if (comment.isMe) CornerSize(12.dp) else CornerSize(0.dp),
                    topEnd = if (comment.isMe) CornerSize(0.dp) else CornerSize(12.dp)
                ),
                color = if (comment.isMe) PrimaryBlue else Color.White,
                shadowElevation = 1.dp
            ) {
                Text(
                    text = comment.content,
                    modifier = Modifier.padding(12.dp),
                    color = if (comment.isMe) Color.White else Color.Black
                )
            }
            Text(
                text = "${comment.userName} • ${comment.time}",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun CommentInputBar(text: String, onTextChange: (String) -> Unit, onSend: () -> Unit) {
    Surface(
        shadowElevation = 8.dp,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding() // <--- QUAN TRỌNG 1: Tránh thanh 3 nút điều hướng
            .imePadding()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = onTextChange,
                placeholder = { Text("Write a comment...") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = PrimaryBlue
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = onSend,
                modifier = Modifier.background(PrimaryBlue, CircleShape)
            ) {
                Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White)
            }
        }
    }
}