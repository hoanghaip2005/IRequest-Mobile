package com.project.irequest.presentation.ui.notifications

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Đảm bảo bạn đã có các màu này trong Theme, nếu chưa hãy thay bằng Color(0xFF...) tương ứng
import com.project.irequest.presentation.theme.CustomOrange
import com.project.irequest.presentation.theme.PrimaryBlue

/**
 * Notifications Screen - UI Updated Version
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
@Suppress("FunctionName")
fun NotificationsScreen(
    onNotificationClick: (String) -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    // Danh sách tab dài cũng không sợ bị lỗi hiển thị
    val tabs = listOf("All", "Notifications", "Chat", "SLA Warnings")

    Scaffold(
        containerColor = Color(0xFFF8F9FA), // Màu nền tổng thể xám rất nhạt cho hiện đại
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Alerts", // Đổi tên thành Alerts cho ngắn gọn súc tích
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        // Hiển thị số lượng chưa đọc nhỏ bên dưới
                        Text(
                            text = "12 unread",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    // Nút đánh dấu đã đọc tất cả
                    IconButton(onClick = { /* TODO: Mark all read */ }) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Mark all read",
                            tint = PrimaryBlue
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF8F9FA),
                    scrolledContainerColor = Color(0xFFF8F9FA)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 1. SCROLLABLE TAB ROW (Khắc phục lỗi xuống dòng)
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                edgePadding = 16.dp, // Padding đầu dòng
                divider = {}, // Bỏ đường gạch chân xám mặc định
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = PrimaryBlue
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium
                                ),
                                color = if (selectedTab == index) PrimaryBlue else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 2. LIST VỚI STICKY HEADER (Phân nhóm ngày tháng)
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp) // Khoảng cách giữa các item nhỏ lại chút cho tinh tế
            ) {
                // Nhóm: Today
                stickyHeader {
                    DateHeader(text = "Today")
                }

                items(5) { index ->
                    // Giả lập logic hiển thị item
                    val type = if(index == 0) NotificationType.WARNING else NotificationType.INFO
                    NotificationItemUpdated(
                        type = type,
                        title = if(type == NotificationType.WARNING) "Request Timeout Warning" else "Request Assigned",
                        message = "Request #REQ-00$index is approaching SLA deadline. Please verify immediately.",
                        time = "${index + 1}h ago",
                        isRead = index > 2, // 3 cái đầu chưa đọc
                        badgeText = if(type == NotificationType.WARNING) "${3-index} hours left" else null,
                        onClick = { onNotificationClick("NOTIF-$index") }
                    )
                }

                // Nhóm: Yesterday
                stickyHeader {
                    DateHeader(text = "Yesterday")
                }

                items(5) { index ->
                    NotificationItemUpdated(
                        type = NotificationType.SUCCESS,
                        title = "Request Approved",
                        message = "Your request for Laptop has been approved by Manager.",
                        time = "1d ago",
                        isRead = true,
                        onClick = { onNotificationClick("OLD-$index") }
                    )
                }
            }
        }
    }
}

// --- COMPONENTS ---

@Composable
fun DateHeader(text: String) {
    Surface(
        color = Color(0xFFF8F9FA), // Trùng màu nền để che nội dung khi scroll
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun NotificationItemUpdated(
    type: NotificationType,
    title: String,
    message: String,
    time: String,
    isRead: Boolean,
    badgeText: String? = null, // Text cho badge (ví dụ: "3 hours left")
    onClick: () -> Unit
) {
    // Config màu sắc dựa trên Type
    val (icon, themeColor, containerColor) = when (type) {
        NotificationType.INFO -> Triple(Icons.Default.Info, PrimaryBlue, PrimaryBlue.copy(alpha = 0.1f))
        NotificationType.SUCCESS -> Triple(Icons.Outlined.CheckCircle, Color(0xFF4CAF50), Color(0xFF4CAF50).copy(alpha = 0.1f))
        NotificationType.WARNING -> Triple(Icons.Default.Warning, CustomOrange, CustomOrange.copy(alpha = 0.1f))
        NotificationType.COMMENT -> Triple(Icons.Default.Email, Color(0xFF9C27B0), Color(0xFF9C27B0).copy(alpha = 0.1f))
    }

    // Logic nền: Chưa đọc = Trắng sáng + Viền nhẹ hoặc Màu tint rất nhạt. Đã đọc = Trong suốt hơn.
    val backgroundColor = if (!isRead) Color.White else Color.White.copy(alpha = 0.6f)
    val elevation = if (!isRead) 2.dp else 0.dp // Item chưa đọc sẽ nổi hơn
    val border = if (!isRead) null else null // Có thể thêm border cho item đã đọc nếu muốn

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp) // Padding item
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        shape = RoundedCornerShape(16.dp), // Bo góc mềm mại hơn
        // Border ẩn cho item đã đọc để tạo cảm giác chìm xuống
        border = if(isRead) androidx.compose.foundation.BorderStroke(1.dp, Color.Transparent) else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top // Căn top để icon thẳng hàng với title
        ) {
            // 1. ICON TRÒN
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(containerColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = themeColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // 2. NỘI DUNG CHÍNH
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Header Row: Title + Time/Badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            // Font đậm nếu chưa đọc
                            fontWeight = if (!isRead) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 16.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    // Nếu có Badge Text (Ví dụ: SLA Warning) thì hiện Badge
                    if (badgeText != null) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            color = themeColor, // Badge cùng màu với icon (Cam/Đỏ)
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = badgeText,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    } else {
                        // Nếu không có Badge thì hiện giờ
                        Text(
                            text = time,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Body
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp
                )
            }

            // 3. DOT UNREAD (Dấu chấm xanh nhỏ bên phải nếu chưa đọc)
            if (!isRead) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(PrimaryBlue)
                )
            }
        }
    }
}

// Giữ nguyên Enum cũ của bạn
enum class NotificationType {
    INFO, SUCCESS, WARNING, COMMENT
}