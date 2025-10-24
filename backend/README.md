# IRequest Backend API

Backend API cho ứng dụng IRequest Mobile, sử dụng Azure services.

## Kiến trúc
- **.NET 8 Web API**: REST API backend
- **Azure SQL Database**: Database chính
- **Azure App Service**: Host API
- **Azure Storage**: File attachments
- **Azure Active Directory B2C**: Authentication
- **Azure Service Bus**: Real-time notifications
- **Azure Application Insights**: Monitoring

## Database Schema
Dựa trên cấu trúc từ ứng dụng C# hiện tại:

### Entities chính:
- **Users (AppUser)**: Người dùng với Azure AD B2C
- **Requests**: Yêu cầu laptop/thiết bị
- **Departments**: Phòng ban
- **Workflow/WorkflowSteps**: Quy trình duyệt
- **Priority/Status**: Mức độ ưu tiên và trạng thái
- **Comments**: Bình luận trên request
- **RequestHistory**: Lịch sử thay đổi
- **Notifications**: Thông báo real-time

## API Endpoints

### Authentication
- `POST /api/auth/login` - Đăng nhập
- `POST /api/auth/register` - Đăng ký
- `POST /api/auth/refresh` - Refresh token
- `POST /api/auth/logout` - Đăng xuất

### Requests
- `GET /api/requests` - Lấy danh sách requests
- `GET /api/requests/{id}` - Lấy chi tiết request
- `POST /api/requests` - Tạo request mới
- `PUT /api/requests/{id}` - Cập nhật request
- `DELETE /api/requests/{id}` - Xóa request
- `POST /api/requests/{id}/approve` - Duyệt request
- `POST /api/requests/{id}/reject` - Từ chối request

### Files
- `POST /api/files/upload` - Upload file attachment
- `GET /api/files/{id}` - Download file
- `DELETE /api/files/{id}` - Xóa file

### Departments
- `GET /api/departments` - Lấy danh sách phòng ban
- `GET /api/departments/{id}/users` - Lấy users theo phòng ban

### Workflow
- `GET /api/workflows` - Lấy danh sách workflows
- `GET /api/workflows/{id}/steps` - Lấy steps của workflow

### Notifications
- `GET /api/notifications` - Lấy thông báo của user
- `PUT /api/notifications/{id}/read` - Đánh dấu đã đọc
- WebSocket endpoint cho real-time notifications

## Setup và chạy

### Prerequisites
- .NET 8 SDK
- Azure subscription
- Azure CLI
- SQL Server (local) hoặc Azure SQL Database

### Configuration
1. Tạo `appsettings.json` với Azure connection strings
2. Setup Azure AD B2C tenant
3. Tạo Azure Storage account
4. Deploy database schema

### Run locally
```bash
cd backend
dotnet restore
dotnet run
```

### Deploy to Azure
```bash
az webapp up --name irequest-api --resource-group irequest-rg
```