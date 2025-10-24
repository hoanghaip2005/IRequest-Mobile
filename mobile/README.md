# IRequest Mobile - Android Kotlin App

Ứng dụng mobile Android cho hệ thống IRequest, được xây dựng bằng Kotlin và Jetpack Compose.

## 🏗️ Kiến trúc

### Technology Stack:
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM + Clean Architecture
- **Dependency Injection**: Hilt
- **Networking**: Retrofit + OkHttp
- **Local Database**: Room
- **Navigation**: Compose Navigation
- **Image Loading**: Coil
- **Async**: Kotlin Coroutines + Flow
- **Local Storage**: DataStore Preferences

### Project Structure:
```
app/src/main/java/com/project/irequest/
├── data/                          # Data layer
│   ├── local/                     # Local data sources
│   │   ├── entity/               # Room entities
│   │   ├── dao/                  # Room DAOs
│   │   └── database/             # Room database
│   ├── remote/                   # Remote data sources
│   │   ├── api/                  # Retrofit API interfaces
│   │   └── dto/                  # API DTOs
│   ├── repository/               # Repository implementations
│   └── mapper/                   # Data mappers
├── domain/                       # Domain layer
│   ├── model/                    # Domain models
│   ├── repository/              # Repository interfaces
│   └── usecase/                 # Use cases
├── presentation/                 # Presentation layer
│   ├── ui/                      # UI screens
│   │   ├── auth/               # Authentication screens
│   │   ├── request/            # Request screens
│   │   ├── profile/            # Profile screens
│   │   └── notification/       # Notification screens
│   ├── viewmodel/              # ViewModels
│   ├── navigation/             # Navigation setup
│   └── component/              # Reusable UI components
├── di/                          # Dependency injection modules
└── util/                       # Utilities and extensions
```

## 🚀 Tính năng chính

### 1. Authentication
- [x] Login/Register
- [x] JWT Token management
- [x] Auto logout on token expiry
- [x] Biometric authentication (planned)

### 2. Request Management
- [x] View requests list với pagination
- [x] Create new request
- [x] View request details
- [x] Comment on requests
- [x] Star/unstar requests
- [x] Filter và search requests
- [x] Request status tracking

### 3. Workflow Management
- [x] View workflow steps
- [x] Approve/reject requests
- [x] Track request progress
- [x] Role-based actions

### 4. Real-time Features
- [x] Push notifications
- [x] Real-time status updates
- [x] SignalR integration

### 5. Offline Support
- [x] Cache requests locally
- [x] Offline viewing
- [x] Sync khi có network

## 📱 UI/UX Features

### Design System:
- Material Design 3
- Dark/Light theme support
- Responsive layout
- Accessibility compliance

### Key Screens:
1. **Login/Register Screen**
2. **Dashboard**: Overview requests và notifications
3. **Request List**: Danh sách requests với filter
4. **Request Detail**: Chi tiết request với comments
5. **Create Request**: Form tạo request mới
6. **Profile**: Thông tin user và settings
7. **Notifications**: Danh sách thông báo

## 🔧 Setup và Development

### Prerequisites:
- Android Studio Hedgehog (2023.1.1) hoặc mới hơn
- JDK 11
- Android SDK 24+
- Backend API running

### Configuration:
1. **API Configuration**:
   ```kotlin
   // In build.gradle.kts (Module: app)
   debug {
       buildConfigField("String", "BASE_URL", "\"http://10.0.2.2:5000/api/\"")
   }
   release {
       buildConfigField("String", "BASE_URL", "\"https://your-api-domain.azurewebsites.net/api/\"")
   }
   ```

2. **Database**: Room database tự động tạo khi app khởi động

### Build và Run:
```bash
# Clone project
git clone <repository-url>
cd mobile

# Build project
./gradlew build

# Run on device/emulator
./gradlew installDebug
```

## 🔄 Integration với Backend

### API Endpoints:
- **Authentication**: `/api/auth/*`
- **Requests**: `/api/requests/*`
- **Master Data**: `/api/departments/*`, `/api/priorities/*`, etc.
- **Notifications**: `/api/notifications/*`
- **Files**: `/api/files/*`

### Real-time Connection:
- SignalR Hub: `/notificationHub`
- Auto-reconnection khi network available
- Background sync cho offline changes

## 🔒 Security Features

1. **Token Management**:
   - JWT tokens stored in encrypted DataStore
   - Auto refresh tokens
   - Secure logout với token invalidation

2. **Network Security**:
   - Certificate pinning
   - Request/response encryption
   - API key protection

3. **Local Data**:
   - Room database với encryption
   - Secure key storage
   - Data obfuscation

## 📊 Performance Optimizations

1. **Network**:
   - Request caching với OkHttp
   - Pagination cho large datasets
   - Image caching với Coil

2. **Memory**:
   - Lazy loading với Compose
   - ViewHolder pattern tối ưu
   - Memory leak prevention

3. **Database**:
   - Indexed queries
   - Background threading
   - Incremental sync

## 🧪 Testing Strategy

### Unit Tests:
- Repository layer testing
- ViewModel testing
- Use case testing

### Integration Tests:
- API integration tests
- Database tests
- End-to-end flow tests

### UI Tests:
- Compose UI testing
- Navigation testing
- Accessibility testing

## 📦 Build và Deployment

### Gradle Tasks:
```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Run tests
./gradlew test

# Generate test coverage
./gradlew jacocoTestReport
```

### CI/CD Pipeline:
- GitHub Actions integration
- Automated testing
- Release deployment to Azure App Center

## 🔧 Development Guidelines

### Code Style:
- Kotlin coding conventions
- ktlint cho code formatting
- detekt cho static analysis

### Git Workflow:
- Feature branch strategy
- Code review required
- Automated testing pipeline

### Performance Monitoring:
- Firebase Performance
- Crash reporting với Firebase Crashlytics
- Analytics integration

---

**Note**: Đây là mobile app đi kèm với backend .NET Web API. Cả hai đều kết nối Azure services để đồng bộ dữ liệu với ứng dụng C# web hiện tại.