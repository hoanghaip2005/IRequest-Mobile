package com.project.irequest.presentation.ui.employees

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.irequest.presentation.theme.PrimaryBlue

/**
 * Màn hình quản lý nhân viên
 * Hiển thị danh sách nhân viên với các thông tin cơ bản
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeesScreen(
    onNavigateBack: () -> Unit = {},
    onEmployeeClick: (Employee) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    
    // Danh sách nhân viên mẫu
    val employees = remember {
        getSampleEmployees()
    }
    
    // Lọc nhân viên theo tìm kiếm và bộ lọc
    val filteredEmployees = employees.filter { employee ->
        val matchesSearch = employee.name.contains(searchQuery, ignoreCase = true) ||
                employee.email.contains(searchQuery, ignoreCase = true) ||
                employee.department.contains(searchQuery, ignoreCase = true)
        val matchesFilter = selectedFilter == "All" || employee.department == selectedFilter
        matchesSearch && matchesFilter
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Quản lý Nhân viên",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Quay lại",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryBlue,
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { /* TODO: Thêm nhân viên mới */ }) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Thêm nhân viên",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Thêm nhân viên */ },
                containerColor = PrimaryBlue
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Thêm nhân viên",
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(padding)
        ) {
            // Search bar
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            
            // Filter chips
            FilterChips(
                selectedFilter = selectedFilter,
                onFilterSelected = { selectedFilter = it },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            
            // Thống kê
            EmployeeStats(
                total = employees.size,
                active = employees.count { it.isActive },
                modifier = Modifier.padding(16.dp)
            )
            
            // Danh sách nhân viên
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredEmployees) { employee ->
                    EmployeeCard(
                        employee = employee,
                        onClick = { onEmployeeClick(employee) }
                    )
                }
                
                if (filteredEmployees.isEmpty()) {
                    item {
                        EmptyState(
                            message = if (searchQuery.isEmpty()) {
                                "Chưa có nhân viên nào"
                            } else {
                                "Không tìm thấy nhân viên phù hợp"
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = { Text("Tìm kiếm nhân viên...") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Tìm kiếm")
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Clear, contentDescription = "Xóa")
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White
        )
    )
}

@Composable
private fun FilterChips(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val filters = listOf("All", "IT", "HR", "Sales", "Marketing", "Finance")
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEach { filter ->
            FilterChip(
                selected = selectedFilter == filter,
                onClick = { onFilterSelected(filter) },
                label = { Text(filter) },
                leadingIcon = if (selectedFilter == filter) {
                    {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                } else null
            )
        }
    }
}

@Composable
private fun EmployeeStats(
    total: Int,
    active: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            title = "Tổng số",
            value = total.toString(),
            icon = Icons.Default.Person,
            color = PrimaryBlue,
            modifier = Modifier.weight(1f)
        )
        StatCard(
            title = "Đang làm việc",
            value = active.toString(),
            icon = Icons.Default.CheckCircle,
            color = Color(0xFF10B981),
            modifier = Modifier.weight(1f)
        )
        StatCard(
            title = "Nghỉ việc",
            value = (total - active).toString(),
            icon = Icons.Default.Close,
            color = Color(0xFFF59E0B),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium.copy(
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
private fun EmployeeCard(
    employee: Employee,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(getAvatarColor(employee.name)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = employee.name.take(2).uppercase(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = employee.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = employee.position,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.Gray
                    )
                    Text(
                        text = employee.department,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
            
            // Status badge
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (employee.isActive) {
                    Color(0xFF10B981).copy(alpha = 0.1f)
                } else {
                    Color(0xFFF59E0B).copy(alpha = 0.1f)
                }
            ) {
                Text(
                    text = if (employee.isActive) "Active" else "Inactive",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = if (employee.isActive) {
                        Color(0xFF10B981)
                    } else {
                        Color(0xFFF59E0B)
                    }
                )
            }
        }
    }
}

@Composable
private fun EmptyState(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = Color.Gray
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
        }
    }
}

// ==================== DATA MODELS ====================

data class Employee(
    val id: String,
    val name: String,
    val email: String,
    val position: String,
    val department: String,
    val isActive: Boolean = true,
    val phone: String = "",
    val joinDate: String = ""
)

// ==================== HELPER FUNCTIONS ====================

internal fun getSampleEmployees(): List<Employee> {
    return listOf(
        Employee(
            id = "1",
            name = "Nguyễn Văn An",
            email = "an.nguyen@company.com",
            position = "Senior Developer",
            department = "IT",
            phone = "0901234567",
            joinDate = "2020-01-15"
        ),
        Employee(
            id = "2",
            name = "Trần Thị Bình",
            email = "binh.tran@company.com",
            position = "HR Manager",
            department = "HR",
            phone = "0901234568",
            joinDate = "2019-03-20"
        ),
        Employee(
            id = "3",
            name = "Lê Văn Cường",
            email = "cuong.le@company.com",
            position = "Sales Executive",
            department = "Sales",
            phone = "0901234569",
            joinDate = "2021-06-10"
        ),
        Employee(
            id = "4",
            name = "Phạm Thị Dung",
            email = "dung.pham@company.com",
            position = "Marketing Specialist",
            department = "Marketing",
            phone = "0901234570",
            joinDate = "2020-09-05"
        ),
        Employee(
            id = "5",
            name = "Hoàng Văn Em",
            email = "em.hoang@company.com",
            position = "Accountant",
            department = "Finance",
            phone = "0901234571",
            joinDate = "2018-12-01"
        ),
        Employee(
            id = "6",
            name = "Vũ Thị Hoa",
            email = "hoa.vu@company.com",
            position = "UI/UX Designer",
            department = "IT",
            phone = "0901234572",
            joinDate = "2021-02-14"
        ),
        Employee(
            id = "7",
            name = "Đỗ Văn Kiên",
            email = "kien.do@company.com",
            position = "Project Manager",
            department = "IT",
            isActive = false,
            phone = "0901234573",
            joinDate = "2017-05-20"
        ),
        Employee(
            id = "8",
            name = "Bùi Thị Lan",
            email = "lan.bui@company.com",
            position = "Recruitment Specialist",
            department = "HR",
            phone = "0901234574",
            joinDate = "2020-11-11"
        )
    )
}

internal fun getAvatarColor(name: String): Color {
    val colors = listOf(
        Color(0xFF6366F1), // Indigo
        Color(0xFF8B5CF6), // Purple
        Color(0xFFEC4899), // Pink
        Color(0xFF10B981), // Green
        Color(0xFF3B82F6), // Blue
        Color(0xFFF59E0B), // Orange
        Color(0xFFEF4444), // Red
        Color(0xFF14B8A6)  // Teal
    )
    return colors[kotlin.math.abs(name.hashCode()) % colors.size]
}

// ==================== PREVIEW ====================

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EmployeesScreenPreview() {
    MaterialTheme {
        EmployeesScreen()
    }
}
