package com.project.irequest.presentation.ui.employees

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.irequest.presentation.theme.PrimaryBlue

/**
 * Màn hình chi tiết thông tin nhân viên
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeDetailScreen(
    employeeId: String,
    onNavigateBack: () -> Unit = {}
) {
    // Tìm nhân viên từ ID (trong thực tế sẽ load từ ViewModel)
    val employee = remember(employeeId) {
        try {
            getSampleEmployees().find { it.id == employeeId }
        } catch (e: Exception) {
            null
        }
    }
    
    if (employee == null) {
        // Hiển thị màn hình lỗi nếu không tìm thấy
        ErrorScreen(onNavigateBack = onNavigateBack)
        return
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chi tiết nhân viên") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Quay lại")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Edit employee */ }) {
                        Icon(Icons.Default.Edit, "Chỉnh sửa")
                    }
                    IconButton(onClick = { /* TODO: More actions */ }) {
                        Icon(Icons.Default.MoreVert, "Thêm")
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
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFF5F5F5))
        ) {
            // Header với avatar và tên
            EmployeeHeader(employee)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Thông tin cơ bản
            InfoSection(
                title = "Thông tin cơ bản",
                items = listOf(
                    InfoItem(Icons.Default.Email, "Email", employee.email),
                    InfoItem(Icons.Default.Phone, "Số điện thoại", employee.phone),
                    InfoItem(Icons.Default.AccountCircle, "Phòng ban", employee.department),
                    InfoItem(Icons.Default.Star, "Chức vụ", employee.position),
                    InfoItem(Icons.Default.DateRange, "Ngày vào làm", employee.joinDate)
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Trạng thái
            StatusSection(employee)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Thống kê công việc
            WorkStatisticsSection(employee)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Các hành động
            ActionButtons(employee)
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun EmployeeHeader(employee: Employee) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar lớn
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(getAvatarColor(employee.name)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = employee.name.take(2).uppercase(),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = employee.name,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = employee.position,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Badge trạng thái
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = if (employee.isActive) 
                    Color(0xFF10B981).copy(alpha = 0.1f) 
                else 
                    Color(0xFFEF4444).copy(alpha = 0.1f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(
                                if (employee.isActive) Color(0xFF10B981) else Color(0xFFEF4444)
                            )
                    )
                    Text(
                        text = if (employee.isActive) "Đang làm việc" else "Nghỉ việc",
                        style = MaterialTheme.typography.labelMedium,
                        color = if (employee.isActive) Color(0xFF10B981) else Color(0xFFEF4444)
                    )
                }
            }
        }
    }
}

@Composable
private fun InfoSection(
    title: String,
    items: List<InfoItem>
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            items.forEachIndexed { index, item ->
                InfoRow(item)
                if (index < items.size - 1) {
                    Divider(modifier = Modifier.padding(vertical = 12.dp))
                }
            }
        }
    }
}

@Composable
private fun InfoRow(item: InfoItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            item.icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = PrimaryBlue
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.label,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = item.value,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
private fun StatusSection(employee: Employee) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Trạng thái hiện tại",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatusCard(
                    title = "Trạng thái",
                    value = if (employee.isActive) "Hoạt động" else "Không hoạt động",
                    color = if (employee.isActive) Color(0xFF10B981) else Color(0xFFEF4444),
                    modifier = Modifier.weight(1f)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                StatusCard(
                    title = "Phòng ban",
                    value = employee.department,
                    color = PrimaryBlue,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun StatusCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = 0.1f)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = color
            )
        }
    }
}

@Composable
private fun WorkStatisticsSection(employee: Employee) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Thống kê công việc",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatisticCard(
                    title = "Yêu cầu",
                    value = "24",
                    icon = Icons.Default.List,
                    color = Color(0xFF3B82F6),
                    modifier = Modifier.weight(1f)
                )
                StatisticCard(
                    title = "Hoàn thành",
                    value = "18",
                    icon = Icons.Default.CheckCircle,
                    color = Color(0xFF10B981),
                    modifier = Modifier.weight(1f)
                )
                StatisticCard(
                    title = "Đang xử lý",
                    value = "6",
                    icon = Icons.Default.Settings,
                    color = Color(0xFFF59E0B),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun StatisticCard(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = 0.1f)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = color
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = color
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun ActionButtons(employee: Employee) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { /* TODO: View requests */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue
                )
            ) {
                Icon(Icons.Default.List, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Xem các yêu cầu")
            }
            
            OutlinedButton(
                onClick = { /* TODO: Send message */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Email, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Gửi tin nhắn")
            }
            
            if (employee.isActive) {
                OutlinedButton(
                    onClick = { /* TODO: Deactivate */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFEF4444)
                    )
                ) {
                    Icon(Icons.Default.Close, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ngừng kích hoạt")
                }
            } else {
                Button(
                    onClick = { /* TODO: Activate */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF10B981)
                    )
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Kích hoạt lại")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ErrorScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lỗi") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Quay lại")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = Color.Gray
                )
                Text(
                    text = "Không tìm thấy nhân viên",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray
                )
                Button(onClick = onNavigateBack) {
                    Text("Quay lại")
                }
            }
        }
    }
}

// ==================== DATA CLASSES ====================

private data class InfoItem(
    val icon: ImageVector,
    val label: String,
    val value: String
)

// ==================== PREVIEW ====================

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EmployeeDetailScreenPreview() {
    MaterialTheme {
        EmployeeDetailScreen(employeeId = "1")
    }
}
