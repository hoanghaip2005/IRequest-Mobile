# ❓ Câu hỏi thường gặp & Trả lời đầy đủ - Feature 1, 2, MyTasks

## 📌 Những câu thầy hay hỏi + Trả lời full code

---

## 🔴 FEATURE 1: MyRequestsScreen (Danh sách request)

### **Câu hỏi 1: MyRequestsScreen hiển thị những thông tin gì?**

**Trả lời**:
MyRequestsScreen hiển thị **danh sách tất cả request mà user tạo ra**, với thông tin:

```
┌─────────────────────────────────────┐
│ REQ-001: Laptop Request             │  ← Request code + title
│ 🔴 Pending | ⭐ High Priority      │  ← Status + Priority badges
│ Deadline: 2 days left                │  ← Thời gian còn lại
│ Assignee: Agent Smith                │  ← Người phụ trách
│ Last update: 2 hours ago             │  ← Thời gian cập nhật
└─────────────────────────────────────┘
```

**Dữ liệu cụ thể**:
- Request code (REQ-001, REQ-002, etc.)
- Title (tên request)
- Status: Pending, Approved, Rejected, In Progress
- Priority: High, Medium, Low
- Deadline
- Assignee (người được giao)
- Last update time
- Request ID (để click)

**Mock data code**:
```kotlin
data class MockRequest(
    val id: Int,
    val code: String,              // "REQ-001"
    val title: String,             // "Laptop Request"
    val description: String,
    val status: String,            // "Pending"
    val priority: String,          // "High"
    val category: String,
    val deadline: String,          // "2 days left"
    val assignee: String           // "Agent Smith"
)

val mockRequests = listOf(
    MockRequest(
        id = 1,
        code = "REQ-001",
        title = "Laptop Request",
        status = "Pending",
        priority = "High",
        deadline = "2 days left",
        assignee = "Agent Smith"
    ),
    // ... more requests
)
```

---

### **Câu hỏi 2: Dùng những kỹ thuật gì để hiển thị danh sách này?**

**Trả lời**: Dùng 5 kỹ thuật chính:

#### **1. LazyColumn - Danh sách cuộn**
```kotlin
LazyColumn(
    modifier = Modifier.fillMaxSize(),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
) {
    items(mockRequests) { request ->
        RequestCard(request = request)
    }
}
```
**Tại sao**: Chỉ hiển thị items visible trên màn hình → Tiết kiệm RAM

---

#### **2. Card - Mỗi request là 1 thẻ**
```kotlin
Card(
    modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onRequestClick),
    shape = RoundedCornerShape(12.dp),
    elevation = CardDefaults.cardElevation(4.dp)
) {
    // Nội dung request
}
```
**Tại sao**: Hiển thị đẹp, có shadow, dễ click

---

#### **3. Row & Column - Layout**
```kotlin
Column(
    modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
) {
    // Header
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("REQ-001")  // Code
        Text("Pending")  // Status
    }
    
    // Title
    Text("Laptop Request")
    
    // Info row
    Row {
        Badge("High")
        Badge("2 days left")
        Badge("Agent Smith")
    }
}
```
**Tại sao**: Căn lề, sắp xếp dữ liệu dễ nhìn

---

#### **4. Badges - Hiển thị trạng thái**
```kotlin
Surface(
    shape = RoundedCornerShape(6.dp),
    color = when (status) {
        "Pending" -> CustomRed.copy(alpha = 0.1f)
        "Approved" -> CustomGreen.copy(alpha = 0.1f)
        else -> CustomOrange.copy(alpha = 0.1f)
    }
) {
    Text(
        text = status,
        color = when (status) {
            "Pending" -> CustomRed
            "Approved" -> CustomGreen
            else -> CustomOrange
        }
    )
}
```
**Tại sao**: Dễ nhìn trạng thái, màu khác nhau theo priority/status

---

#### **5. State Management - Quản lý trạng thái**
```kotlin
var isRefreshing by remember { mutableStateOf(false) }
var isLoading by remember { mutableStateOf(true) }
var hasError by remember { mutableStateOf(false) }
var requests by remember { mutableStateOf<List<MockRequest>>(emptyList()) }

// Khi state thay đổi → LazyColumn tự recompose
if (isLoading) {
    CircularProgressIndicator()  // Show loading
} else if (hasError) {
    Text("Connection Error")     // Show error
} else {
    LazyColumn { ... }           // Show list
}
```
**Tại sao**: Tự động update UI, không cần reload

---

#### **6. PullToRefresh - Kéo để làm mới**
```kotlin
PullToRefreshBox(
    isRefreshing = isRefreshing,
    onRefresh = {
        isRefreshing = true
        // Simulate API call
        delay(2000)
        isRefreshing = false
    }
) {
    LazyColumn { ... }
}
```
**Tại sao**: UX tốt, user thường dùng pull-to-refresh

---

#### **7. Navigation - Click vào item**
```kotlin
Card(
    modifier = Modifier.clickable(onClick = {
        onRequestClick(request.id)  // Callback
        navController.navigate("main/request-detail/${request.id}")
    })
)
```
**Tại sao**: Navigate tới detail screen khi user click

---

### **Câu hỏi 3: Code hoạt động thế nào từ đầu đến cuối?**

**Trả lời**: Flow chi tiết:

```
Step 1: Khởi tạo state
────────────────────
var requests by remember { mutableStateOf(mockRequests) }
→ Lưu danh sách request vào state

Step 2: Render LazyColumn
──────────────────────────
LazyColumn {
    items(requests) { request ->
        RequestCard(request)
    }
}
→ Hiển thị từng request là 1 card

Step 3: RequestCard hiển thị từng trường
─────────────────────────────────────────
RequestCard(request) = {
    Text(request.code)          // "REQ-001"
    Text(request.title)         // "Laptop Request"
    Badge(request.status)       // 🔴 Pending
    Badge(request.priority)     // ⭐ High
    Text(request.deadline)      // "2 days left"
    Text(request.assignee)      // "Agent Smith"
}

Step 4: User kéo (pull-to-refresh)
────────────────────────────────────
isRefreshing = true
  ↓ (2 giây)
isRefreshing = false
requests = mockRequests (updated)
  ↓
LazyColumn tự recompose
  ↓
Danh sách refresh

Step 5: User click 1 request
────────────────────────────
Card(
    clickable = {
        navController.navigate("main/request-detail/1")
    }
)
  ↓
Navigate tới RequestDetailScreen
```

---

## 🟡 FEATURE 2: RequestDetailScreen (Chi tiết request)

### **Câu hỏi 4: RequestDetailScreen hiển thị những thông tin gì?**

**Trả lời**:
RequestDetailScreen hiển thị **chi tiết đầy đủ của 1 request**:

```
┌──────────────────────────────────┐
│ REQ-001: Laptop Request  [←]     │  ← Request code + title + back btn
├──────────────────────────────────┤
│ Status: 🔴 Pending               │  ← Status badge
│ Priority: ⭐ High                │  ← Priority badge
│ Deadline: 2 days left             │  ← Deadline
├──────────────────────────────────┤
│ Description:                     │  ← Mô tả chi tiết
│ Need new laptop for dev work     │
│ Specs: 16GB RAM, SSD, i7        │
├──────────────────────────────────┤
│ Requester: Admin User            │  ← Người tạo
│ Assignee: Agent Smith            │  ← Người phụ trách
├──────────────────────────────────┤
│ Timeline:                        │  ← Lịch sử trạng thái
│ 2024-11-26 Created by Admin     │
│ 2024-11-26 Assigned to Smith    │
│ 2024-11-26 Awaiting approval    │
├──────────────────────────────────┤
│ [Overview] [Comments] [History]  │  ← Tabs
│                                  │
│ Tab content displayed here       │
├──────────────────────────────────┤
│ [Edit] [Reject] [Approve]        │  ← Action buttons
└──────────────────────────────────┘
```

**Dữ liệu cụ thể**:
- Request code, title
- Status, priority
- Deadline
- Description
- Requester (người tạo)
- Assignee (người được giao)
- Timeline entries (lịch sử thay đổi)
- Comment count
- Attachment count

**Mock data code**:
```kotlin
data class HistoryEntry(
    val date: String,
    val action: String,
    val actor: String
)

val currentRequest = MockRequest(
    id = 1,
    code = "REQ-001",
    title = "Laptop Request",
    status = "Pending",
    priority = "High",
    deadline = "2 days left",
    requester = "Admin User",
    assignee = "Agent Smith",
    timeline = listOf(
        HistoryEntry("2024-11-26", "Created", "Admin"),
        HistoryEntry("2024-11-26", "Assigned to Smith", "Admin"),
        HistoryEntry("2024-11-26", "Awaiting approval", "System")
    ),
    comments = 2,
    attachments = 1
)
```

---

### **Câu hỏi 5: Dùng những kỹ thuật gì để hiển thị detail?**

**Trả lời**: Dùng 6 kỹ thuật:

#### **1. TabRow & Tab - Tab navigation**
```kotlin
var selectedTab by remember { mutableIntStateOf(0) }

TabRow(selectedTabIndex = selectedTab) {
    Tab(
        selected = selectedTab == 0,
        onClick = { selectedTab = 0 },
        text = { Text("Overview") }
    )
    Tab(
        selected = selectedTab == 1,
        onClick = { selectedTab = 1 },
        text = { Text("Comments") }
    )
    Tab(
        selected = selectedTab == 2,
        onClick = { selectedTab = 2 },
        text = { Text("History") }
    )
}

// Hiển thị content theo tab
when (selectedTab) {
    0 -> OverviewContent()
    1 -> CommentsContent()
    2 -> HistoryContent()
}
```
**Tại sao**: Quản lý multiple content, user chọn tab

---

#### **2. Badges & Colors - Hiển thị trạng thái**
```kotlin
val statusColor = when (status) {
    "Pending" -> CustomRed
    "Approved" -> CustomGreen
    "In Progress" -> CustomOrange
}

Surface(
    shape = RoundedCornerShape(6.dp),
    color = statusColor.copy(alpha = 0.1f)
) {
    Text(
        text = status,
        color = statusColor
    )
}
```
**Tại sao**: User nhanh chóng hiểu trạng thái

---

#### **3. Timeline - Lịch sử thay đổi**
```kotlin
LazyColumn {
    items(currentRequest.timeline) { entry ->
        TimelineItem(
            date = entry.date,       // "2024-11-26"
            action = entry.action,   // "Created"
            actor = entry.actor      // "Admin"
        )
    }
}

@Composable
fun TimelineItem(date: String, action: String, actor: String) {
    Row {
        Circle()  // Timeline dot
        Column {
            Text("$action by $actor")
            Text(date)
        }
    }
}
```
**Tại sao**: User thấy quá trình xử lý request

---

#### **4. AlertDialog - Xác nhận action**
```kotlin
var showApprovalDialog by remember { mutableStateOf(false) }

Button(onClick = { showApprovalDialog = true }) {
    Text("Approve")
}

if (showApprovalDialog) {
    AlertDialog(
        onDismissRequest = { showApprovalDialog = false },
        title = { Text("Approve Task") },
        text = { Text("Are you sure?") },
        confirmButton = {
            TextButton(onClick = {
                onQuickApprove(requestId)
                showApprovalDialog = false
            }) {
                Text("Approve")
            }
        }
    )
}
```
**Tại sao**: Confirm trước khi approve (tránh nhầm)

---

#### **5. State Management - Tab switching**
```kotlin
var selectedTab by remember { mutableIntStateOf(0) }

// Khi selectedTab thay đổi → Content tự update
when (selectedTab) {
    0 -> OverviewContent()
    1 -> CommentsContent()
    2 -> HistoryContent()
}
```
**Tại sao**: Tab content tự động update không cần reload

---

#### **6. Navigation - Back button**
```kotlin
TopAppBar(
    navigationIcon = {
        IconButton(onClick = {
            navController.popBackStack()  // Go back
        }) {
            Icon(Icons.Default.ArrowBack)
        }
    }
)
```
**Tại sao**: User quay lại MyRequestsScreen

---

### **Câu hỏi 6: Code hoạt động thế nào từ đầu đến cuối?**

**Trả lời**: Flow chi tiết:

```
Step 1: Nhận requestId từ navigation
──────────────────────────────────────
RequestDetailScreen(requestId = 1)
  ↓
Tìm request với id=1 từ mockRequests
  ↓
currentRequest = MockRequest(id=1, code="REQ-001", ...)

Step 2: Render header
────────────────────
Text(currentRequest.code + currentRequest.title)
Text(currentRequest.status)  // 🔴 Pending
Text(currentRequest.priority)  // ⭐ High
Text(currentRequest.deadline)  // 2 days left

Step 3: Render description
───────────────────────────
Text(currentRequest.description)
Text("Requester: ${currentRequest.requester}")
Text("Assignee: ${currentRequest.assignee}")

Step 4: Render timeline
──────────────────────
LazyColumn {
    items(currentRequest.timeline) { entry ->
        TimelineItem(entry)
    }
}
→ Hiển thị lịch sử

Step 5: Render tabs
──────────────────
TabRow(selectedTabIndex = selectedTab)
  ↓
User click "Comments" tab
  ↓
selectedTab = 1
  ↓
when (selectedTab) {
    0 -> OverviewContent()
    1 -> CommentsContent()  ← Show comments
    2 -> HistoryContent()
}

Step 6: User click "Approve"
─────────────────────────────
Button(onClick = { showApprovalDialog = true })
  ↓
showApprovalDialog = true
  ↓
AlertDialog appears
  ↓
User click "Approve" in dialog
  ↓
onQuickApprove(requestId)  ← Callback
showApprovalDialog = false
  ↓
Dialog closes

Step 7: User click back button
──────────────────────────────
IconButton(onClick = {
    navController.popBackStack()
})
  ↓
Navigate back to MyRequestsScreen
```

---

## 🟠 MY TASKS: MyTasksScreen (Work center)

### **Câu hỏi 7: MyTasksScreen hiển thị những thông tin gì?**

**Trả lời**:
MyTasksScreen hiển thị **4 tabs công việc**: To Process, Waiting, Processed, My Requests

#### **Tab 1: To Process (Cần xử lý) - 8 tasks**
```
┌──────────────────────────────────┐
│ TASK-001                REQ-001  │  ← Task ID + Request ID
├──────────────────────────────────┤
│ Approve Laptop Request for...    │  ← Task title
├──────────────────────────────────┤
│ 🔵 Manager Approval | 🔴 High   │  ← Workflow step + Priority
│                       2 days left│  ← Deadline
├──────────────────────────────────┤
│ [Reject]          [Approve]      │  ← Quick action buttons
└──────────────────────────────────┘
```

**Thông tin hiển thị**:
- Task code (TASK-001, TASK-002)
- Request code (REQ-001, REQ-002)
- Task title (mô tả công việc)
- Workflow step (Manager Approval)
- Priority (High, Medium, Low)
- Deadline (X days left)
- Quick buttons (Approve, Reject) - chỉ tab này có

---

#### **Tab 2: Waiting (Chờ duyệt) - 3 tasks**
```
┌──────────────────────────────────┐
│ TASK-002                REQ-002  │  ← Task ID + Request ID
├──────────────────────────────────┤
│ Waiting for Department Head...   │  ← Task title
├──────────────────────────────────┤
│ 🟢 Department Head Review        │  ← Workflow step
│                        Waiting    │  ← Status
├──────────────────────────────────┤
│ (No action buttons)              │  ← Chỉ view, không action
└──────────────────────────────────┘
```

**Thông tin hiển thị**:
- Task ID, Request ID
- Task title
- Workflow step
- Status (Waiting) - không có deadline

---

#### **Tab 3: Processed (Đã xử lý) - 10 tasks**
```
┌──────────────────────────────────┐
│ TASK-003                REQ-003  │
├──────────────────────────────────┤
│ Processed: Office Supply Request │
├──────────────────────────────────┤
│ 🟢 Completed                     │
│                    Completed 5 days ago
├──────────────────────────────────┤
│ (No action buttons)              │
└──────────────────────────────────┘
```

**Thông tin hiển thị**:
- Task ID, Request ID
- Task title
- Status (Completed)
- Completed date

---

#### **Tab 4: My Requests (Request của tôi) - 5 requests**
```
┌──────────────────────────────────┐
│ TASK-004                REQ-004  │
├──────────────────────────────────┤
│ My Request: New Equipment        │
├──────────────────────────────────┤
│ 🟡 In Progress                   │
│                      1 day ago    │
├──────────────────────────────────┤
│ (No action buttons)              │
└──────────────────────────────────┘
```

**Thông tin hiển thị**:
- Task ID, Request ID
- Title
- Status
- Creation date

---

### **Câu hỏi 8: Dùng những kỹ thuật gì để hiển thị MyTasks?**

**Trả lời**: Dùng 8 kỹ thuật:

#### **1. TabRow với Badge count**
```kotlin
var selectedTab by remember { mutableIntStateOf(0) }

val tabs = listOf(
    Pair("To Process", 8),      // Tab name + count
    Pair("Waiting", 3),
    Pair("Processed", 0),
    Pair("My Requests", 5)
)

TabRow(selectedTabIndex = selectedTab) {
    tabs.forEachIndexed { index, (title, count) ->
        Tab(
            selected = selectedTab == index,
            onClick = { selectedTab = index },
            text = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(title)
                    if (count > 0) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Badge {
                            Text(count.toString())  // "8", "3", etc.
                        }
                    }
                }
            }
        )
    }
}
```
**Tại sao**: User thấy ngay có bao nhiêu task cần xử lý

---

#### **2. Dynamic task count theo tab**
```kotlin
val taskCount = when (selectedTab) {
    0 -> 8   // To Process
    1 -> 3   // Waiting
    2 -> 10  // Processed
    3 -> 5   // My Requests
    else -> 0
}

LazyColumn {
    items(taskCount) { index ->
        TaskListItem(...)
    }
}
```
**Tại sao**: Mỗi tab hiển thị số item khác nhau

---

#### **3. Conditional quick action buttons**
```kotlin
@Composable
fun TaskListItem(
    showQuickActions: Boolean = false,
    onQuickApprove: () -> Unit = {},
    onQuickReject: () -> Unit = {}
) {
    Card {
        // ... task info ...
        
        // Chỉ show buttons nếu showQuickActions = true
        if (showQuickActions) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(onClick = onQuickReject) {
                    Icon(Icons.Default.Close)
                    Text("Reject")
                }
                
                Button(onClick = onQuickApprove) {
                    Icon(Icons.Default.CheckCircle)
                    Text("Approve")
                }
            }
        }
    }
}

// Gọi:
TaskListItem(
    showQuickActions = selectedTab == 0,  // Chỉ tab "To Process" có
    ...
)
```
**Tại sao**: Tab "To Process" cần nút action, tabs khác không

---

#### **4. Priority badge với color khác**
```kotlin
val priorityColor = when (priority) {
    "High" -> CustomRed
    "Medium" -> CustomOrange
    else -> CustomGreen
}

Surface(
    shape = RoundedCornerShape(6.dp),
    color = priorityColor.copy(alpha = 0.1f)
) {
    Text(
        text = priority,
        color = priorityColor
    )
}
```
**Tại sao**: User nhanh chóng nhận biết độ ưu tiên

---

#### **5. Workflow step badge**
```kotlin
val workflowStep = when (selectedTab) {
    0 -> "Manager Approval"
    1 -> "Department Head Review"
    2 -> "Completed"
    3 -> "In Progress"
    else -> "Unknown"
}

Surface(
    shape = RoundedCornerShape(6.dp),
    color = PrimaryBlue.copy(alpha = 0.1f)
) {
    Text(
        text = workflowStep,
        color = PrimaryBlue
    )
}
```
**Tại sao**: User thấy task ở step nào trong quy trình

---

#### **6. State management cho dialogs**
```kotlin
var showApprovalDialog by remember { mutableStateOf(false) }
var showRejectDialog by remember { mutableStateOf(false) }
var selectedTaskId by remember { mutableStateOf("") }
var rejectReason by remember { mutableStateOf("") }

// Khi user click "Approve" button:
Button(onClick = {
    selectedTaskId = "TASK-001"
    showApprovalDialog = true  // Dialog appear
})

// Dialog:
if (showApprovalDialog) {
    AlertDialog(
        title = { Text("Approve Task") },
        text = { Text("Sure?") },
        confirmButton = {
            TextButton(onClick = {
                onQuickApprove(selectedTaskId)
                showApprovalDialog = false  // Dialog close
            }) {
                Text("Approve")
            }
        }
    )
}
```
**Tại sao**: Confirm trước approve, reject cần nhập lý do

---

#### **7. Rejection reason input**
```kotlin
var rejectReason by remember { mutableStateOf("") }

if (showRejectDialog) {
    AlertDialog(
        title = { Text("Reject Task") },
        text = {
            Column {
                Text("Reason:")
                TextField(
                    value = rejectReason,
                    onValueChange = { newValue -> 
                        rejectReason = newValue  // State thay đổi
                    },
                    maxLines = 5
                )
            }
        },
        confirmButton = {
            TextButton(
                enabled = rejectReason.isNotEmpty(),  // Button disable nếu trống
                onClick = {
                    onQuickReject(selectedTaskId)
                    showRejectDialog = false
                    rejectReason = ""  // Clear
                }
            ) {
                Text("Reject")
            }
        }
    )
}
```
**Tại sao**: Reject cần lý do (validation)

---

#### **8. TopAppBar với filter button**
```kotlin
Scaffold(
    topBar = {
        TopAppBar(
            title = {
                Column {
                    Text("My Tasks", style = titleLarge)
                    Text("Work Center", style = bodySmall)
                }
            },
            actions = {
                IconButton(onClick = { /* Open filter */ }) {
                    Icon(Icons.Filled.Settings, "Filter tasks")
                }
            }
        )
    },
    floatingActionButton = {
        FloatingActionButton(onClick = onCreateRequest) {
            Icon(Icons.Default.Add)
        }
    }
)
```
**Tại sao**: Header + filter + button "Create" dễ access

---

### **Câu hỏi 9: Code hoạt động thế nào từ đầu đến cuối?**

**Trả lời**: Flow chi tiết:

```
Step 1: Initialize
─────────────────
var selectedTab by remember { mutableIntStateOf(0) }  // "To Process" tab
var showApprovalDialog by remember { mutableStateOf(false) }
var showRejectDialog by remember { mutableStateOf(false) }
var selectedTaskId by remember { mutableStateOf("") }
var rejectReason by remember { mutableStateOf("") }

Step 2: Render TabRow
────────────────────
TabRow với 4 tabs:
- [To Process (8)] ← selectedTab = 0
- [Waiting (3)]
- [Processed (0)]
- [My Requests (5)]

Step 3: Render LazyColumn
────────────────────────
val taskCount = 8  // "To Process" tab
items(8) { index ->
    TaskListItem(
        taskId = "TASK-001",
        title = "Approve Laptop Request for...",
        workflowStep = "Manager Approval",
        priority = "High",
        dueDate = "2 days left",
        showQuickActions = true,  // "To Process" has buttons
        ...
    )
}
→ Hiển thị 8 task cards

Step 4: Each TaskListItem
────────────────────────
TaskListItem(
    Card {
        Row {
            Text("TASK-001")  // Task ID
            Text("REQ-001")   // Request ID
        }
        Text("Approve Laptop...")  // Title
        Row {
            Badge("Manager Approval")  // Workflow step
            Badge("High")              // Priority
            Text("2 days left")        // Deadline
        }
        
        // Buttons (chỉ "To Process" tab có)
        Row {
            OutlinedButton(onClick = {
                selectedTaskId = "TASK-001"
                rejectReason = ""
                showRejectDialog = true
            }) {
                Icon(Icons.Default.Close)
                Text("Reject")
            }
            
            Button(onClick = {
                selectedTaskId = "TASK-001"
                showApprovalDialog = true
            }) {
                Icon(Icons.Default.CheckCircle)
                Text("Approve")
            }
        }
    }
)

Step 5: User click "Approve" button
───────────────────────────────────
onClick = {
    selectedTaskId = "TASK-001"
    showApprovalDialog = true
}
  ↓
showApprovalDialog = true
  ↓
if (showApprovalDialog) {
    AlertDialog(
        title = "Approve Task",
        text = "Sure approve TASK-001?",
        confirmButton = { "Approve" }
    )
}
  ↓
Dialog hiển thị

Step 6: User click "Approve" in dialog
──────────────────────────────────────
TextButton(onClick = {
    onQuickApprove("TASK-001")  // Callback trigger
    showApprovalDialog = false
})
  ↓
onQuickApprove("TASK-001")  // Xử lý (print log)
showApprovalDialog = false
  ↓
Dialog closes

Step 7: User click "Reject" button
──────────────────────────────────
onClick = {
    selectedTaskId = "TASK-001"
    rejectReason = ""
    showRejectDialog = true
}
  ↓
showRejectDialog = true
rejectReason = ""
  ↓
if (showRejectDialog) {
    AlertDialog(
        title = "Reject Task",
        text = {
            TextField(
                value = rejectReason,
                onValueChange = { newValue ->
                    rejectReason = newValue  // User nhập lý do
                }
            )
        },
        confirmButton = {
            TextButton(
                enabled = rejectReason.isNotEmpty(),
                onClick = {
                    onQuickReject("TASK-001")
                    showRejectDialog = false
                    rejectReason = ""
                }
            )
        }
    )
}
  ↓
Dialog hiển thị, có TextField

Step 8: User nhập lý do vào TextField
──────────────────────────────────────
TextField(
    value = rejectReason,
    onValueChange = { newValue ->
        rejectReason = newValue  // "Laptop model not approved"
    }
)
  ↓
rejectReason = "Laptop model not approved"
  ↓
Compose recompose
  ↓
Approve button in dialog becomes enabled

Step 9: User click "Reject" in dialog
──────────────────────────────────────
TextButton(onClick = {
    onQuickReject("TASK-001")  // Callback
    showRejectDialog = false
    rejectReason = ""  // Clear
})
  ↓
onQuickReject("TASK-001")  // Xử lý (print log)
showRejectDialog = false
rejectReason = ""
  ↓
Dialog closes

Step 10: User click "Waiting" tab
─────────────────────────────────
Tab(
    onClick = { selectedTab = 1 }
)
  ↓
selectedTab = 1
  ↓
Compose recompose
  ↓
LazyColumn items(3) instead of items(8)
  ↓
val taskCount = 3  // "Waiting" tab
ShowQuickActions = false  // Hide buttons
  ↓
Hiển thị 3 task cards, không có button
```

---

## 📊 Tóm tắt so sánh 3 features

| Khía cạnh | Feature 1 (MyRequests) | Feature 2 (RequestDetail) | MyTasks (4 tabs) |
|-----------|----------------------|---------------------------|-----------------|
| **Hiển thị** | Danh sách request | Chi tiết 1 request | 4 loại task/request |
| **Components** | LazyColumn, Card | Card, TabRow, Timeline | LazyColumn, Card, Badge |
| **State** | requests, isLoading, hasError | selectedTab, timeline | selectedTab, showDialog, rejectReason |
| **Action** | Click item → Detail | Approve/Reject | Approve/Reject nhanh |
| **Dialog** | Không | Có (Approve/Reject) | Có (Approve/Reject) |
| **Badge** | Status, Priority | Status, Priority | Workflow step, Priority |
| **Buttons** | Không | Approve/Reject/Edit | Approve/Reject (chỉ tab 1) |
| **Mục đích** | Xem list | Xem detail | Làm việc |

---

## ❓ Những câu hỏi khác thầy hay hỏi

### **Q: Sự khác biệt giữa Alert Dialog và Bottom Sheet?**
A:
```kotlin
// AlertDialog - popup giữa màn hình
AlertDialog(
    title = { Text("Approve") },
    onDismissRequest = { ... }
)

// BottomSheetDialogFragment - slide up từ dưới (chưa dùng)
ModalBottomSheet(
    onDismissRequest = { ... }
) {
    // Content
}
```

---

### **Q: Làm sao state thay đổi mà UI tự update?**
A:
```kotlin
var rejectReason by remember { mutableStateOf("") }

// Khi state thay đổi:
TextField(
    onValueChange = { newValue ->
        rejectReason = newValue  // State = "Laptop not approved"
    }
)

// Compose thấy state thay đổi → tự recompose → UI update
// Không cần gọi setState() hay reload
```

---

### **Q: Tại sao dùng remember?**
A:
```kotlin
// Không dùng remember:
var count = 0
Button(onClick = { count++ })
Text(count)
// count reset về 0 mỗi lần recompose

// Dùng remember:
var count by remember { mutableStateOf(0) }
Button(onClick = { count++ })
Text(count)
// count giữ giá trị qua các lần recompose
```

---

### **Q: Tiles vs Column vs Row là gì?**
A:
```kotlin
Column {
    // Vertical layout (dọc)
    Text("A")
    Text("B")
    Text("C")
}
// Output:
// A
// B
// C

Row {
    // Horizontal layout (ngang)
    Text("A")
    Text("B")
    Text("C")
}
// Output: A B C

LazyColumn {
    // Column nhưng cuộn được, tiết kiệm RAM
    items(1000) { index ->
        Text("Item $index")
    }
}
```

---

### **Q: onClick vs onValueChange là gì?**
A:
```kotlin
// onClick - xử lý khi click
Button(onClick = {
    // Code khi user click
})

// onValueChange - xử lý khi text thay đổi
TextField(
    onValueChange = { newValue ->
        // Code khi user nhập/xóa text
        rejectReason = newValue
    }
)
```

---

### **Q: Modifier.fillMaxWidth() vs weight(1f)?**
A:
```kotlin
// fillMaxWidth - chiếm toàn bộ chiều rộng
Button(modifier = Modifier.fillMaxWidth())
// Output: [========Button========]

// weight(1f) - chia đều trong Row/Column
Row {
    Button(modifier = Modifier.weight(1f))  // 50%
    Button(modifier = Modifier.weight(1f))  // 50%
}
// Output: [Button1][Button2]
```

---

### **Q: Làm sao validate TextField?**
A:
```kotlin
var rejectReason by remember { mutableStateOf("") }

TextField(
    value = rejectReason,
    onValueChange = { newValue ->
        rejectReason = newValue
    },
    isError = rejectReason.isEmpty(),  // Highlight nếu trống
    supportingText = {
        if (rejectReason.isEmpty()) {
            Text("Reason is required")
        }
    }
)

Button(
    enabled = rejectReason.isNotEmpty(),  // Disable nếu trống
    onClick = { ... }
)
```

---

### **Q: Badge vs Surface - khi nào dùng?**
A:
```kotlin
// Badge - nhỏ, số lượng (count)
BadgedBox(
    badge = { Badge { Text("5") } }
)

// Surface - lớn hơn, background color
Surface(
    shape = RoundedCornerShape(6.dp),
    color = Color.Blue.copy(alpha = 0.1f)
) {
    Text("High Priority")
}
```

---

### **Q: Làm sao di chuyển giữa tab mà không reload?**
A:
```kotlin
var selectedTab by remember { mutableIntStateOf(0) }

// Tab 1 content
when (selectedTab) {
    0 -> Content1()
    1 -> Content2()
    2 -> Content3()
}

// Khi click tab:
Tab(onClick = { selectedTab = 1 })

// selectedTab = 1 → when khác branch → show Content2()
// Compose chỉ recompose phần content, không reload tab
```

---

### **Q: Làm sao pass data từ cha tới con?**
A:
```kotlin
// Parent:
MyTasksScreen(
    onQuickApprove = { taskId ->
        println("Approve $taskId")
    }
)

// Child:
@Composable
fun TaskListItem(
    onQuickApprove: () -> Unit = {}
) {
    Button(onClick = onQuickApprove)
}
```

---

### **Q: Callback là gì?**
A:
```kotlin
// Parent định nghĩa callback:
fun MyTasksScreen(
    onQuickApprove: (String) -> Unit = {}
) {
    TaskListItem(
        onQuickApprove = {
            onQuickApprove("TASK-001")  // Pass up
        }
    )
}

// Khi gọi MyTasksScreen:
MyTasksScreen(
    onQuickApprove = { taskId ->
        println("User approved $taskId")  // Handle here
    }
)
```

---

### **Q: Mock data là gì? Tại sao dùng?**
A:
```kotlin
// Mock data - giả dữ liệu
val mockRequests = listOf(
    MockRequest(id = 1, code = "REQ-001", ...),
    MockRequest(id = 2, code = "REQ-002", ...),
)

// Tại sao:
✓ Dev UI không cần chờ backend
✓ Test dễ dàng
✓ Không phụ thuộc API

// Real app:
val requests = repository.getRequests()  // API call
```

---

### **Q: Compose Recompose là gì?**
A:
```
User action (click, input)
  ↓
State thay đổi
  ↓
Compose detect state change
  ↓
Recompose (vẽ lại) → chỉ vẽ lại phần thay đổi
  ↓
UI update

Ví dụ:
rejectReason = ""
User nhập: "Laptop not approved"
rejectReason = "Laptop not approved"
  ↓
Compose thấy rejectReason thay đổi
  ↓
Recompose TextField
  ↓
Approve button becomes enabled
```

---

## ✅ Checklist trả lời thầy

Có thể trả lời được câu hỏi nào?

- ✅ MyRequestsScreen hiển thị gì
- ✅ RequestDetailScreen hiển thị gì
- ✅ MyTasks (4 tabs) hiển thị gì
- ✅ Dùng kỹ thuật gì (LazyColumn, TabRow, Badge, Dialog, State)
- ✅ Code hoạt động như nào (step by step)
- ✅ Approve/Reject dialog hoạt động sao
- ✅ Reject reason validation sao
- ✅ Callback là gì, tại sao dùng
- ✅ Remember là gì, tại sao dùng
- ✅ Recompose là gì
- ✅ Mock data là gì
- ✅ Columns vs Row vs LazyColumn
- ✅ Badge vs Surface
- ✅ AlertDialog hoạt động sao
- ✅ Tab switching hoạt động sao

**Hạn chế**:
- ❌ API server (chưa có)
- ❌ Database (chưa có)
- ❌ Authentication (chưa làm)
- ❌ Performance optimization

---

**Version**: 1.0  
**Date**: 2024-11-27  
**Status**: ✅ Ready to answer all questions from teachers
