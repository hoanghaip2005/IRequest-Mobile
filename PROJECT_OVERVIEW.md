# IRequest - Complete Project Overview

Dự án IRequest bao gồm 3 phần chính:
1. **C# Web Application** (Existing - không thay đổi)
2. **Backend API** (.NET 8 Web API với Azure integration)
3. **Mobile App** (Android Kotlin với Jetpack Compose)

## 🏗️ Tổng quan Kiến trúc

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   C# Web App    │    │   Backend API   │    │   Mobile App    │
│   (Existing)    │◄──►│   (.NET 8)      │◄──►│   (Kotlin)      │
│                 │    │                 │    │                 │
│ • ASP.NET MVC   │    │ • Web API       │    │ • Jetpack       │
│ • Identity      │    │ • Azure Services│    │   Compose       │
│ • SQL Server    │    │ • JWT Auth      │    │ • Retrofit      │
│ • SignalR       │    │ • SignalR Hub   │    │ • Room DB       │
└─────────────────┘    └─────────────────┘    └─────────────────┘
        │                       │                       │
        └───────────────────────┼───────────────────────┘
                                │
                    ┌─────────────────┐
                    │ Azure Services  │
                    │                 │
                    │ • SQL Database  │
                    │ • Blob Storage  │
                    │ • App Service   │
                    │ • AD B2C        │
                    │ • Service Bus   │
                    │ • Insights      │
                    └─────────────────┘
```

## 📂 Cấu trúc Project

```
D:\Program Files\Workspace\
├── C#/                           # Ứng dụng C# hiện tại (KHÔNG THAY ĐỔI)
│   └── Request/
│       ├── Controllers/
│       ├── Models/
│       ├── Views/
│       └── ...
├── backend/                      # Backend API mới (.NET 8)
│   ├── Controllers/              # API Controllers
│   ├── Models/                   # Entity Models
│   ├── DTOs/                     # Data Transfer Objects
│   ├── Services/                 # Business Logic Services
│   ├── Data/                     # DbContext & Migrations
│   ├── Hubs/                     # SignalR Hubs
│   ├── appsettings.json          # Configuration
│   └── Program.cs                # Application entry point
└── mobile/                       # Android Mobile App
    └── app/
        └── src/main/java/com/project/irequest/
            ├── data/             # Data Layer (Repository, API, DB)
            ├── domain/           # Business Logic Layer
            ├── presentation/     # UI Layer (Compose, ViewModels)
            ├── di/               # Dependency Injection
            └── util/             # Utilities
```

## 🚀 Lộ trình Development

### Phase 1: Backend API Setup (HOÀN THÀNH ✅)
- [x] Tạo .NET 8 Web API project
- [x] Configure Azure services integration
- [x] Setup Entity Framework với SQL Server
- [x] JWT Authentication implementation
- [x] Basic CRUD APIs cho tất cả entities
- [x] SignalR Hub cho real-time notifications

### Phase 2: Mobile App Foundation (HOÀN THÀNH ✅)
- [x] Android project setup với Jetpack Compose
- [x] Dependency injection với Hilt
- [x] Network layer với Retrofit
- [x] Local database với Room
- [x] Navigation setup
- [x] Basic UI structure

### Phase 3: Integration & Testing (TIẾP THEO)
- [ ] Complete API endpoints implementation
- [ ] Mobile app UI screens development
- [ ] Authentication flow integration
- [ ] Real-time features với SignalR
- [ ] File upload/download functionality
- [ ] Offline sync implementation

### Phase 4: Deployment & Production (CUỐI CÙNG)
- [ ] Azure App Service deployment cho API
- [ ] Mobile app testing & optimization
- [ ] Performance monitoring setup
- [ ] User acceptance testing
- [ ] Production deployment

## 🔧 Technology Stack

### Backend API (.NET 8):
- **Framework**: ASP.NET Core Web API
- **Authentication**: JWT với Azure AD B2C
- **Database**: Azure SQL Database + Entity Framework Core
- **Real-time**: SignalR
- **Storage**: Azure Blob Storage
- **Monitoring**: Application Insights
- **Hosting**: Azure App Service

### Mobile App (Android):
- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Architecture**: MVVM + Clean Architecture
- **DI**: Hilt
- **Network**: Retrofit + OkHttp
- **Database**: Room
- **Real-time**: SignalR Android Client

### Integration Points:
- **Shared Database**: Azure SQL với synchronized schema
- **File Storage**: Azure Blob Storage
- **Authentication**: JWT tokens shared giữa web và mobile
- **Real-time**: SignalR hubs cho notifications

## 🔄 Data Flow

### Authentication Flow:
1. User login từ mobile app
2. Backend API validates với Azure AD B2C
3. JWT token được trả về
4. Token được dùng cho tất cả API calls
5. Token tự động refresh khi expire

### Request Management Flow:
1. User tạo request từ mobile
2. API lưu vào Azure SQL Database
3. SignalR notification được gửi đến relevant users
4. Web app và mobile app nhận real-time updates
5. Workflow processing diễn ra trong API

### File Handling Flow:
1. Mobile app upload files via API
2. Files được lưu trong Azure Blob Storage
3. File URLs được lưu trong database
4. Both web và mobile có thể access files

## 📱 Key Features Implementation

### Authentication & Authorization:
- JWT-based authentication
- Role-based access control
- Azure AD B2C integration
- Token refresh mechanism

### Request Management:
- CRUD operations cho requests
- Workflow management
- Comments system
- File attachments
- Star/favorite functionality

### Real-time Features:
- Instant notifications
- Status updates
- Live comments
- Presence indication

### Offline Support (Mobile):
- Local data caching
- Offline request creation
- Sync khi có network
- Conflict resolution

## 🔒 Security Considerations

### API Security:
- JWT token validation
- CORS configuration
- Rate limiting
- Input validation
- SQL injection prevention

### Mobile Security:
- Certificate pinning
- Local data encryption
- Secure token storage
- Obfuscated API keys

### Azure Security:
- Network security groups
- Key Vault integration
- Managed identity
- Backup encryption

## 📊 Monitoring & Analytics

### Backend Monitoring:
- Application Insights telemetry
- Performance counters
- Error tracking
- Usage analytics

### Mobile Monitoring:
- Crash reporting
- Performance monitoring
- User analytics
- Network monitoring

## 🔄 Next Steps

1. **Complete Backend Implementation**:
   ```bash
   cd backend
   dotnet restore
   dotnet ef database update
   dotnet run
   ```

2. **Develop Mobile UI**:
   ```bash
   cd mobile
   ./gradlew build
   ./gradlew installDebug
   ```

3. **Setup Azure Resources**:
   - Azure SQL Database
   - App Service
   - Blob Storage
   - Application Insights

4. **Testing & Deployment**:
   - Unit testing
   - Integration testing
   - Performance testing
   - Production deployment

---

**Kết quả**: Bạn sẽ có một hệ thống hoàn chỉnh với 3 components:
- ✅ **C# Web App**: Giữ nguyên không đổi
- ✅ **Backend API**: Modern .NET 8 API với Azure integration  
- ✅ **Mobile App**: Native Android app với Kotlin & Compose

Tất cả đều share data qua Azure services và có real-time synchronization!