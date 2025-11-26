package com.project.irequest.presentation.ui.alerts

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.UUID
// Import Theme Colors
import com.project.irequest.presentation.theme.CustomGreen
import com.project.irequest.presentation.theme.CustomOrange
import com.project.irequest.presentation.theme.CustomRed
import com.project.irequest.presentation.theme.PrimaryBlue

// ----------------------------------------------------------------
// 1. DATA MODELS & ENUMS
// ----------------------------------------------------------------

enum class AlertType {
    REQUEST_UPDATE, REQUEST_APPROVED, REQUEST_REJECTED,
    COMMENT_ADDED, TASK_ASSIGNED, CHAT_MESSAGE, SLA_WARNING, INFO
}

// Model dữ liệu để quản lý trạng thái xóa/đọc
data class AlertData(
    val id: String = UUID.randomUUID().toString(),
    val type: AlertType,
    val title: String,
    val message: String,
    val time: String,
    val isRead: Boolean,
    val group: String, // "Today", "Yesterday"
    val badgeText: String? = null
)

// ----------------------------------------------------------------
// 2. MAIN SCREEN
// ----------------------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
@Suppress("FunctionName")
fun AlertsScreen(
    onAlertClick: (String) -> Unit = {},
    onMarkAllRead: () -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    // TRẠNG THÁI LOADING CHO SHIMMER
    var isLoading by remember { mutableStateOf(true) }

    // DANH SÁCH DỮ LIỆU CÓ THỂ THAY ĐỔI (ĐỂ XÓA)
    val allAlerts = remember { mutableStateListOf<AlertData>() }

    // Giả lập load data từ API
    LaunchedEffect(Unit) {
        isLoading = true
        delay(2000) // Giả lập mạng chậm 2s

        // Tạo dữ liệu khớp y hệt logic cũ của bạn (4 Today, 8 Yesterday, v.v.)
        allAlerts.addAll(generateInitialData())
        isLoading = false
    }

    // Lọc dữ liệu theo Tab đang chọn
    val displayedAlerts = remember(selectedTab, allAlerts.size) {
        when (selectedTab) {
            1 -> allAlerts.filter { it.type == AlertType.REQUEST_UPDATE || it.type == AlertType.INFO || it.type == AlertType.REQUEST_APPROVED } // Notifications
            2 -> allAlerts.filter { it.type == AlertType.CHAT_MESSAGE } // Chat (Hiện tại đang để trống trong data mẫu để test empty)
            3 -> allAlerts.filter { it.type == AlertType.SLA_WARNING } // SLA Warnings
            else -> allAlerts // All
        }
    }

    // Gom nhóm theo Header (Today/Yesterday)
    val groupedAlerts = displayedAlerts.groupBy { it.group }

    // Tính toán Badge động (Số lượng sẽ giảm khi xóa item)
    val badges = listOf(
        allAlerts.size, // All
        allAlerts.count { it.type == AlertType.REQUEST_UPDATE || it.type == AlertType.INFO }, // Notif
        allAlerts.count { it.type == AlertType.CHAT_MESSAGE }, // Chat
        allAlerts.count { it.type == AlertType.SLA_WARNING }   // SLA
    )

    val tabs = listOf("All", "Notifications", "Chat", "SLA Warnings")

    Scaffold(
        containerColor = Color(0xFFF8F9FA),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Alerts",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = if (isLoading) "Updating..." else if (badges[selectedTab] > 0) "${badges[selectedTab]} unread" else "All caught up",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    if (!isLoading && badges[selectedTab] > 0) {
                        IconButton(onClick = onMarkAllRead) {
                            Icon(Icons.Default.CheckCircle, contentDescription = "Mark all read", tint = PrimaryBlue)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF8F9FA))
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            // TABS
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                edgePadding = 16.dp,
                divider = {},
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
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium
                                    ),
                                    color = if (selectedTab == index) PrimaryBlue else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                if (badges.getOrElse(index) { 0 } > 0) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(
                                        color = if (selectedTab == index) PrimaryBlue else MaterialTheme.colorScheme.surfaceVariant,
                                        shape = CircleShape,
                                        modifier = Modifier.size(18.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = badges[index].toString(),
                                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                                color = if (selectedTab == index) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // CONTENT AREA
            if (isLoading) {
                // HIỆN SHIMMER KHI ĐANG TẢI
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(6) { ShimmerAlertItem() }
                }
            } else if (displayedAlerts.isEmpty()) {
                // HIỆN EMPTY STATE NẾU KHÔNG CÓ DỮ LIỆU
                EmptyState(
                    title = "No alerts found",
                    description = "You have no notifications in this category."
                )
            } else {
                // HIỆN LIST VỚI TÍNH NĂNG VUỐT XÓA
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    groupedAlerts.forEach { (group, alerts) ->
                        stickyHeader { DateHeader(text = "$group (${alerts.size})") }

                        items(items = alerts, key = { it.id }) { alert ->
                            SwipeToDeleteContainer(
                                item = alert,
                                onDelete = {
                                    allAlerts.remove(alert) // Xóa khỏi danh sách gốc
                                }
                            ) {
                                AlertItem(
                                    data = alert,
                                    onClick = { onAlertClick(alert.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------------------
// 3. SWIPE TO DELETE COMPONENT
// ----------------------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    content: @Composable (T) -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onDelete(item)
                true
            } else false
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            val color = if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                Color.Red.copy(alpha = 0.8f)
            } else Color.Transparent

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 2.dp)
                    .background(color, RoundedCornerShape(12.dp))
                    .padding(end = 24.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete",
                    tint = Color.White
                )
            }
        },
        content = { content(item) },
        enableDismissFromStartToEnd = false
    )
}

// ----------------------------------------------------------------
// 4. SHIMMER EFFECT COMPONENT
// ----------------------------------------------------------------

@Composable
fun ShimmerAlertItem() {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.3f),
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.3f),
    )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ), label = "shimmer"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(80.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.size(40.dp).clip(CircleShape).background(brush))
        Spacer(modifier = Modifier.width(16.dp))
        Column(verticalArrangement = Arrangement.Center, modifier = Modifier.weight(1f)) {
            Spacer(modifier = Modifier.height(16.dp).fillMaxWidth(0.7f).clip(RoundedCornerShape(4.dp)).background(brush))
            Spacer(modifier = Modifier.height(8.dp))
            Spacer(modifier = Modifier.height(14.dp).fillMaxWidth(0.9f).clip(RoundedCornerShape(4.dp)).background(brush))
        }
    }
}

// ----------------------------------------------------------------
// 5. STANDARD UI COMPONENTS
// ----------------------------------------------------------------

@Composable
fun AlertItem(data: AlertData, onClick: () -> Unit) {
    val (icon, themeColor) = when (data.type) {
        AlertType.REQUEST_UPDATE -> Pair(Icons.Default.Info, PrimaryBlue)
        AlertType.REQUEST_APPROVED -> Pair(Icons.Default.CheckCircle, CustomGreen)
        AlertType.SLA_WARNING -> Pair(Icons.Default.Warning, CustomOrange)
        AlertType.CHAT_MESSAGE -> Pair(Icons.Default.Email, PrimaryBlue)
        AlertType.INFO -> Pair(Icons.Default.Info, Color.Gray)
        else -> Pair(Icons.Default.Notifications, Color.Gray)
    }

    val containerColor = if (!data.isRead) Color.White else Color.White.copy(alpha = 0.5f)
    val elevation = if (!data.isRead) 1.dp else 0.dp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier.size(40.dp).clip(CircleShape).background(themeColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = themeColor, modifier = Modifier.size(20.dp))
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = data.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = if (!data.isRead) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 15.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1, overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    if (data.badgeText != null) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(color = themeColor, shape = RoundedCornerShape(4.dp)) {
                            Text(
                                text = data.badgeText,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color.White, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    } else {
                        Text(text = data.time, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = data.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2, overflow = TextOverflow.Ellipsis
                )
            }

            if (!data.isRead) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier.padding(top = 6.dp).size(8.dp).clip(CircleShape).background(PrimaryBlue))
            }
        }
    }
}

@Composable
fun DateHeader(text: String) {
    Surface(color = Color(0xFFF8F9FA), modifier = Modifier.fillMaxWidth()) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun EmptyState(title: String, description: String) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(100.dp).background(Color.LightGray.copy(alpha = 0.2f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Outlined.Email, contentDescription = null, modifier = Modifier.size(48.dp), tint = PrimaryBlue.copy(alpha = 0.6f))
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp), color = MaterialTheme.colorScheme.onSurface)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center, lineHeight = 20.sp)
    }
}

// ----------------------------------------------------------------
// 6. INITIAL DATA GENERATOR (Khớp với logic cũ của bạn)
// ----------------------------------------------------------------
fun generateInitialData(): List<AlertData> {
    val list = mutableListOf<AlertData>()

    // TODAY: 4 Items (1 Critical + 3 Updates)
    list.add(AlertData(type = AlertType.SLA_WARNING, title = "Critical Timeout", message = "Request #REQ-001 is overdue!", time = "1h left", isRead = false, group = "Today", badgeText = "1h left"))
    for (i in 1..3) {
        list.add(AlertData(type = AlertType.REQUEST_UPDATE, title = "Request Updated", message = "Request #REQ-00${i+1} status has changed.", time = "${i+1}h ago", isRead = false, group = "Today"))
    }

    // YESTERDAY: 8 Items (Approved, Old notifications)
    for (i in 0..7) {
        list.add(AlertData(type = AlertType.REQUEST_APPROVED, title = "Request Approved", message = "Manager approved request #REQ-OLD-$i.", time = "1d ago", isRead = true, group = "Yesterday"))
    }

    // Thêm vài dữ liệu ẩn cho các tab khác để test badge (Tổng badge sẽ khớp)
    // Ví dụ: SLA Warnings
    list.add(AlertData(type = AlertType.SLA_WARNING, title = "SLA Warning", message = "Task overdue soon", time = "2h ago", isRead = false, group = "Today", badgeText = "Urgent"))
    list.add(AlertData(type = AlertType.SLA_WARNING, title = "SLA Breach", message = "Task breached", time = "5h ago", isRead = false, group = "Yesterday", badgeText = "Late"))

    // Notifications specific
    list.add(AlertData(type = AlertType.INFO, title = "System Info", message = "Maintenance scheduled", time = "2d ago", isRead = true, group = "Yesterday"))

    return list
}