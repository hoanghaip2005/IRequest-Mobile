package com.project.irequest.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Message(val text: String, val isFromMe: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(roomId: String, onBackClick: () -> Unit) {
    // Convert roomId to a displayable name (temporary solution)
    val userName = remember(roomId) {
        roomId
            .split('_')
            .joinToString(" ") { it.replaceFirstChar { char -> if (char.isLowerCase()) char.titlecase() else char.toString() } }
    }

    val messages = remember {
        listOf(
            Message("Chào bạn, tôi có thể giúp gì?", isFromMe = false),
            Message("Tôi đang gặp sự cố với yêu cầu số #12345.", isFromMe = true),
            Message("Bạn có thể cho tôi biết chi tiết hơn được không?", isFromMe = false),
            Message("Yêu cầu của tôi đã bị từ chối mà không có lý do rõ ràng.", isFromMe = true),
            Message("Để tôi kiểm tra lại hệ thống.", isFromMe = false),
            Message("Cảm ơn bạn.", isFromMe = true),
            Message("Có vẻ như yêu cầu của bạn thiếu một số tài liệu cần thiết.", isFromMe = false),
            Message("Tôi cần bổ sung những gì?", isFromMe = true),
            Message("Bạn vui lòng đính kèm hóa đơn mua hàng và phiếu bảo hành nhé.", isFromMe = false),
            Message("Ok, tôi sẽ gửi ngay.", isFromMe = true),
        )
    }
    var newMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(userName) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    TextField(
                        value = newMessage,
                        onValueChange = { newMessage = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Nhập tin nhắn...") },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = { /* TODO: Send message */ }) {
                        Icon(Icons.Default.Send, contentDescription = "Send")
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            reverseLayout = true
        ) {
            items(messages.reversed()) { message ->
                MessageBubble(message = message)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun MessageBubble(message: Message) {
    val backgroundColor = if (message.isFromMe) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    val textColor = if (message.isFromMe) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
    val alignment = if (message.isFromMe) Alignment.End else Alignment.Start

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(alignment)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(backgroundColor)
                .padding(12.dp)
        ) {
            Text(
                text = message.text,
                color = textColor,
                fontSize = 16.sp
            )
        }
    }
}
