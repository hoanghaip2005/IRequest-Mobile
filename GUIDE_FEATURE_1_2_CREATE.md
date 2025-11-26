# 📌 Feature 1, 2, Create Request - Tóm gọn để trả lời mọi câu hỏi

## 🎯 Nằm ở những file nào?

### Feature 1: My Requests (Danh sách request)
**File chính**: `app/src/main/java/com/example/irequest/presentation/ui/requests/MyRequestsScreen.kt`
- ~454 dòng code
- Hiển thị danh sách request mà user tạo

### Feature 2: Request Detail (Chi tiết request)
**File chính**: `app/src/main/java/com/example/irequest/presentation/ui/requests/RequestDetailScreen.kt`
- ~600 dòng code
- Hiển thị chi tiết 1 request + approve/reject

### Create Request (Tạo request mới)
**File chính**: `app/src/main/java/com/example/irequest/presentation/ui/requests/CreateRequestScreen.kt`
- ~400-500 dòng code (chuẩn bị)
- Form để người dùng tạo request mới

**File hỗ trợ (dùng chung)**:
- `MainScreen.kt` - Điều khiển navigation (chuyển screen)
- `AppDestinations.kt` - Định nghĩa các route (đường dẫn)
- `HomeScreenComplete.kt` - Home page, nút "Tạo request" link tới Create

---

## 💡 Sử dụng những kiến thức gì?

### 1. **Kotlin - Ngôn ngữ lập trình**
```kotlin
// Data classes - Lưu trữ dữ liệu request
data class MockRequest(
    val id: Int,
    val code: String,
    val title: String,
    val description: String,
    val status: String,
    val priority: String
)

// Lambdas - Xử lý click event
onClick = { onRequestClick(request.id) }
```
**Tại sao**: Đơn giản, an toàn (null safety), dễ hiểu

---

### 2. **Jetpack Compose - Vẽ giao diện**
```kotlin
@Composable
fun MyRequestsScreen() {
    // Compose functions định nghĩa UI
}

// Các components:
LazyColumn { }          // Danh sách cuộn (như ListView)
Card { }                // Thẻ chứa thông tin request
Button { }              // Nút click
TextField { }           // Input text (cho Create)
Scaffold { }            // Khung chính
```
**Tại sao**: Modern, tự động update UI khi data thay đổi, ít code

---

### 3. **State Management - Quản lý trạng thái**
```kotlin
// Khai báo state
var isLoading by remember { mutableStateOf(true) }
var requests by remember { mutableStateOf<List<MockRequest>>(emptyList()) }

// Khi state thay đổi → UI tự update
isLoading = false  // Compose tự vẽ lại
```
**Tại sao**: Khi dữ liệu thay đổi, giao diện tự cập nhật (không cần reload)

---

### 4. **Navigation - Chuyển giữa các screen**
```kotlin
// Navigate từ Home → Create Request
navController.navigate("main/create-request")

// Navigate từ Create → My Requests
navController.navigate("main/my-requests")
```
**Tại sao**: Quản lý flow giữa các screen một cách sạch

---

### 5. **Material Design 3 - Style đẹp**
```kotlin
// Components từ Material Design
Button(modifier = Modifier.fillMaxWidth())
Card(elevation = CardDefaults.cardElevation(4.dp))
TextField(modifier = Modifier.fillMaxWidth())
```
**Tại sao**: Giao diện professional, chuẩn Android

---

## 🔄 Hoạt động như nào? (Flow từ A đến Z)

### **User Flow**
```
1. [Mở app] → HomeScreen (trang chủ)
                ↓
2. [Click "Tạo request"] → CreateRequestScreen
                ↓
3. [Điền form] 
   - Title: "Laptop"
   - Description: "Cần laptop để dev"
   - Category: "Equipment"
   - Priority: "High"
                ↓
4. [Click "Submit"] → Tạo request mới (REQ-001)
                ↓
5. [Navigate tới My Requests] → MyRequestsScreen
                ↓
6. [Xem danh sách] → Thấy REQ-001 vừa tạo
                ↓
7. [Click REQ-001] → RequestDetailScreen
                ↓
8. [Xem chi tiết + Approve/Reject]
```

---

## 📱 Giao diện chi tiết

### **Feature 1: MyRequestsScreen**
```
┌─────────────────────────────────┐
│ My Requests      [↑] [←]        │  ← Header + icons
├─────────────────────────────────┤
│ REQ-001: Laptop Request         │  ← Card item 1
│ 🔴 Pending | ⭐ High           │     Status + Priority
│ Deadline: 2 days left           │     Deadline info
├─────────────────────────────────┤
│ REQ-002: Office Supply          │  ← Card item 2
│ 🟡 In Progress | 🟠 Medium     │
│ Deadline: 5 days left           │
├─────────────────────────────────┤
│ REQ-003: Leave Request          │  ← Card item 3
│ 🟢 Approved | 🟢 Low           │
│ Completed on time               │
└─────────────────────────────────┘

Thành phần:
- TopAppBar: Tiêu đề + back button
- LazyColumn: Danh sách các card (cuộn được)
- Card: Mỗi request là một card
- Text: Hiển thị tiêu đề, status, deadline
```

**Mã Kotlin**:
```kotlin
@Composable
fun MyRequestsScreen(
    navController: NavController
) {
    LazyColumn {
        items(mockRequests) { request ->
            RequestCard(
                request = request,
                onClick = { 
                    // Click → navigate to detail
                    navController.navigate("main/request-detail/${request.id}")
                }
            )
        }
    }
}
```

---

### **Feature 2: RequestDetailScreen**
```
┌──────────────────────────────────┐
│ Request Detail            [←]    │  ← Header
├──────────────────────────────────┤
│ REQ-001: Laptop Request          │  ← Tiêu đề
│ Status: 🔴 Pending              │
│ Priority: ⭐ High               │
│ Deadline: 2 days left            │
├──────────────────────────────────┤
│ Need a new laptop for dev work   │  ← Mô tả
│ Specs: 16GB RAM, SSD, i7        │
├──────────────────────────────────┤
│ Timeline:                        │  ← Lịch sử
│ 2024-11-26 Created by Admin     │
│ 2024-11-26 Assigned to Smith    │
├──────────────────────────────────┤
│ [Overview] [Comments] [History]  │  ← Tabs
│                                  │
│ Tab content displayed here       │
├──────────────────────────────────┤
│ [Edit] [Reject] [Approve]        │  ← Buttons
└──────────────────────────────────┘
```

**Mã Kotlin**:
```kotlin
@Composable
fun RequestDetailScreen(
    requestId: Int,
    navController: NavController
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    
    Card {
        Text("REQ-${requestId}: Laptop Request")  // Tiêu đề
        Text("Status: Pending")                    // Status
        Text("Priority: High")                     // Priority
    }
    
    TabRow(selectedTabIndex = selectedTab) {
        Tab(text = { Text("Overview") })
        Tab(text = { Text("Comments") })
        Tab(text = { Text("History") })
    }
    
    Button(onClick = { /* Approve logic */ }) {
        Text("Approve")
    }
}
```

---

### **Create Request (Form)**
```
┌──────────────────────────────────┐
│ Create Request           [←]     │  ← Header
├──────────────────────────────────┤
│ Title *                          │  ← Input field
│ ┌──────────────────────────────┐│
│ │ Laptop for development      ││
│ └──────────────────────────────┘│
├──────────────────────────────────┤
│ Description *                    │  ← Input field
│ ┌──────────────────────────────┐│
│ │ Need new laptop with:        ││
│ │ - 16GB RAM                   ││
│ │ - SSD                        ││
│ │ - i7 processor               ││
│ └──────────────────────────────┘│
├──────────────────────────────────┤
│ Category *                       │  ← Dropdown
│ [Equipment              ▼]       │
├──────────────────────────────────┤
│ Priority *                       │  ← Dropdown
│ [High                   ▼]       │
├──────────────────────────────────┤
│ [Cancel]               [Submit]  │  ← Buttons
└──────────────────────────────────┘
```

**Mã Kotlin**:
```kotlin
@Composable
fun CreateRequestScreen(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Equipment") }
    var priority by remember { mutableStateOf("High") }
    
    Column {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") }
        )
        
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") }
        )
        
        Button(onClick = {
            // Tạo request với dữ liệu
            val newRequest = MockRequest(
                id = 4,
                code = "REQ-004",
                title = title,
                description = description,
                status = "NEW",
                priority = priority,
                category = category
            )
            // Navigate tới My Requests
            navController.navigate("main/my-requests")
        }) {
            Text("Submit")
        }
    }
}
```

---

## 🎬 Kỹ thuật chính - Cách nó hoạt động

### **1. State Management (Quản lý trạng thái)**
```kotlin
// Ví dụ: User nhập "Laptop" vào TextField

var title by remember { mutableStateOf("") }

TextField(
    value = title,              // Giá trị hiện tại
    onValueChange = { newText ->
        title = newText         // State thay đổi
    }
)

// Khi title thay đổi → Compose tự vẽ lại TextField
// Không cần gọi refresh thủ công
```

### **2. Navigation (Chuyển screen)**
```kotlin
// Từ Home sang Create Request
navController.navigate("main/create-request")

// Từ Create sang My Requests
navController.navigate("main/my-requests")

// Từ My Requests sang Request Detail
navController.navigate("main/request-detail/1")  // ID = 1
```

### **3. Passing Data (Truyền dữ liệu)**
```kotlin
// Trong CreateRequestScreen:
val newRequest = MockRequest(
    id = 4,
    code = "REQ-004",
    title = "Laptop",
    description = "...",
    status = "NEW",
    priority = "High"
)

// Mock data list cập nhật (trong real app, gọi API)
mockRequests.add(newRequest)

// Navigate tới My Requests
navController.navigate("main/my-requests")
// → Thấy REQ-004 trong danh sách
```

### **4. Click Handling (Xử lý click)**
```kotlin
Button(onClick = {
    // Tạo request
    val newRequest = ...
    
    // Navigate
    navController.navigate("main/my-requests")
}) {
    Text("Submit")
}
```

---

## ❓ Câu hỏi thường gặp & Câu trả lời ngắn gọn

### **Q1: Feature 1 và 2 khác gì?**
A:
- **Feature 1** = Danh sách (list)
- **Feature 2** = Chi tiết 1 item (detail)
- **Feature 3 (Create)** = Form để tạo mới

### **Q2: Dữ liệu ở đâu?**
A: Hiện tại dùng mock data (giả dữ liệu). Real app sẽ gọi API để lấy từ server.

### **Q3: Khi user nhập title, title được lưu ở đâu?**
A: Lưu trong `state` (mutableStateOf). Khi user click Submit, lấy giá trị state tạo request.

### **Q4: Approve/Reject button hoạt động thế nào?**
A: Click → Show dialog (hỏi "chắc không?") → Click "Confirm" → Trigger callback → Print log. Real app sẽ gọi API.

### **Q5: Làm sao user biết request vừa tạo nằm ở đâu trong danh sách?**
A: Sau click Submit → navigate tới My Requests → REQ-004 (mới tạo) sẽ hiển thị ở đầu danh sách (sort by newest).

### **Q6: LazyColumn là gì?**
A: Danh sách cuộn được. Chỉ hiển thị items visible trên màn hình (tiết kiệm RAM).

### **Q7: TabRow là gì?**
A: Tab navigation. Click tab → Hiển thị content khác (Overview/Comments/History).

### **Q8: Modifier.fillMaxWidth() là gì?**
A: Style để element chiếm toàn bộ chiều rộng màn hình.

### **Q9: onClick = { } là cái gì?**
A: Lambda function. Xử lý sự kiện click. Giống như addEventListener trong JavaScript.

### **Q10: Mock data là gì?**
A: Dữ liệu giả để test. Thay vì gọi API, dùng hardcoded data.

### **Q11: CreateRequestScreen chỉnh thế nào?**
A: Tương tự Feature 1/2. Tạo mới file, import Compose components, define states, build UI.

### **Q12: Real app làm gì khác vs mock?**
A: Thay `mockRequests` bằng API call. Ví dụ:
```kotlin
// Mock:
val requests = mockRequests

// Real:
val requests = repository.getMyRequests()  // Gọi API
```

### **Q13: File navigation (MainScreen, AppDestinations) là gì?**
A:
- **MainScreen** = Container chứa tất cả screens, định nghĩa routes
- **AppDestinations** = Danh sách route string ("main/my-requests", etc.)

### **Q14: Tại sao dùng remember { mutableStateOf() }?**
A: `remember` = nhớ giá trị khi recompose, `mutableStateOf` = state có thể thay đổi.

### **Q15: Compose recompose là gì?**
A: Vẽ lại UI khi state thay đổi. Tự động, không cần gọi refresh.

### **Q16: Approve/Reject data đi đâu?**
A: Hiện tại chỉ print log. Real app gọi API: repository.approveRequest(requestId).

### **Q17: User có thể back từ Create tới Home không?**
A: Có. Back button sẽ navigate lại tới Home (navController.popBackStack()).

### **Q18: TextField là gì?**
A: Input field. User gõ text vào. Giống input type="text" trong HTML.

### **Q19: Status badges (🔴 Pending) là gì?**
A: Icon + text để hiển thị trạng thái. Red cho Pending, Green cho Approved, etc.

### **Q20: Làm sao test Create Request?**
A: Nhập title/description → Click Submit → Kiểm tra REQ-004 xuất hiện trong My Requests.

---

## 📋 Tóm tắt Files

| File | Dòng code | Chức năng |
|------|-----------|----------|
| **MyRequestsScreen.kt** | ~454 | Danh sách request |
| **RequestDetailScreen.kt** | ~600 | Chi tiết request |
| **CreateRequestScreen.kt** | ~400-500 | Form tạo request |
| **MainScreen.kt** | ~514 | Navigation container |
| **AppDestinations.kt** | ~326 | Route definitions |
| **HomeScreenComplete.kt** | ~248 | Home page |

**TOTAL**: ~3,000+ lines (tất cả theo best practices)

---

## 🎯 Kiến thức tối thiểu để hiểu

1. **Kotlin data classes** = Lưu trữ dữ liệu
2. **Composable functions** = Vẽ UI
3. **State (remember, mutableStateOf)** = Quản lý dữ liệu UI
4. **Click handlers (onClick)** = Xử lý click
5. **Navigation** = Chuyển screen
6. **Components** (TextField, Button, Card, etc.) = UI blocks

Không cần hiểu sâu. Chỉ cần biết basics là trả lời được mọi câu hỏi.

---

## ✅ Sẵn sàng trả lời gì?

Có thể trả lời mọi câu hỏi liên quan tới:
- ✅ Feature 1 hoạt động như nào
- ✅ Feature 2 hoạt động như nào
- ✅ Create Request làm gì
- ✅ Dữ liệu flow từ đâu tới đâu
- ✅ Tại sao dùng technology X
- ✅ Nằm ở file nào
- ✅ State management hoạt động thế nào
- ✅ Approve/Reject làm gì
- ✅ Mock vs Real API khác gì
- ✅ Từng dòng code làm gì

**Hạn chế**: 
- Không hỏi về API server (chưa có)
- Không hỏi về database (chưa có)
- Không hỏi về authentication (chưa implement)

---

**Version**: 1.0  
**Date**: 2024-11-26  
**Status**: ✅ Ready to answer any question about Feature 1, 2, Create Request
