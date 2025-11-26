package com.project.irequest.presentation.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ChatRoom(val id: String, val name: String, val lastMessage: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    onNavigateToChatDetail: (roomId: String) -> Unit,
    onBackClick: () -> Unit
) {
    val chatRooms = listOf(
        ChatRoom(id = "hoang_hai_pham", name = "Hoàng Hải Phạm", lastMessage = "Ok, tôi sẽ gửi ngay."),
        ChatRoom(id = "tran_van_an", name = "Trần Văn An", lastMessage = "Bạn có thể xem lại yêu cầu của tôi không?"),
        ChatRoom(id = "nguyen_thi_binh", name = "Nguyễn Thị Bình", lastMessage = "Cảm ơn sự hỗ trợ của bạn!")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tin nhắn") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(chatRooms) { room ->
                ChatItem(room = room, onClick = { onNavigateToChatDetail(room.id) })
                Divider()
            }
        }
    }
}

@Composable
fun ChatItem(room: ChatRoom, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Placeholder for avatar
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = room.name.first().toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = room.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = room.lastMessage,
                color = Color.Gray,
                maxLines = 1
            )
        }
    }
}
