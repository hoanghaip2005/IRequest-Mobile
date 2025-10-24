using IRequest.Api.DTOs;

namespace IRequest.Api.Services;

public interface IAuthService
{
    Task<ApiResponse<AuthResponseDto>> LoginAsync(LoginDto loginDto);
    Task<ApiResponse<AuthResponseDto>> RegisterAsync(RegisterDto registerDto);
    Task<ApiResponse<AuthResponseDto>> RefreshTokenAsync(RefreshTokenDto refreshTokenDto);
    Task<ApiResponse<bool>> LogoutAsync(string userId);
    Task<ApiResponse<UserDto>> GetCurrentUserAsync(string userId);
    Task<ApiResponse<UserDto>> UpdateProfileAsync(string userId, UpdateUserDto updateUserDto);
    Task<ApiResponse<bool>> ChangePasswordAsync(string userId, ChangePasswordDto changePasswordDto);
}

public interface IRequestService
{
    Task<ApiResponse<PagedResponse<RequestDto>>> GetRequestsAsync(RequestFilterDto filter, string? userId = null);
    Task<ApiResponse<RequestDto>> GetRequestByIdAsync(int requestId, string? userId = null);
    Task<ApiResponse<RequestDto>> CreateRequestAsync(CreateRequestDto createRequestDto, string userId);
    Task<ApiResponse<RequestDto>> UpdateRequestAsync(int requestId, UpdateRequestDto updateRequestDto, string userId);
    Task<ApiResponse<bool>> DeleteRequestAsync(int requestId, string userId);
    Task<ApiResponse<RequestDto>> ApproveRequestAsync(int requestId, ApproveRequestDto approveRequestDto, string userId);
    Task<ApiResponse<bool>> ToggleStarAsync(int requestId, string userId);
    Task<ApiResponse<List<RequestHistoryDto>>> GetRequestHistoryAsync(int requestId);
}

public interface IFileService
{
    Task<ApiResponse<string>> UploadFileAsync(IFormFile file, string userId);
    Task<ApiResponse<byte[]>> DownloadFileAsync(string fileName);
    Task<ApiResponse<bool>> DeleteFileAsync(string fileName);
}

public interface INotificationService
{
    Task<ApiResponse<PagedResponse<NotificationDto>>> GetNotificationsAsync(string userId, int page = 1, int pageSize = 10);
    Task<ApiResponse<int>> GetUnreadCountAsync(string userId);
    Task<ApiResponse<bool>> MarkAsReadAsync(int notificationId, string userId);
    Task<ApiResponse<bool>> MarkAllAsReadAsync(string userId);
    Task SendNotificationAsync(string userId, string title, string message, string type = "info", int? requestId = null);
}

public interface IWorkflowService
{
    Task<ApiResponse<List<WorkflowDto>>> GetWorkflowsAsync();
    Task<ApiResponse<WorkflowDto>> GetWorkflowByIdAsync(int workflowId);
    Task<ApiResponse<List<WorkflowStepDto>>> GetWorkflowStepsAsync(int workflowId);
}

public interface IDepartmentService
{
    Task<ApiResponse<List<DepartmentDto>>> GetDepartmentsAsync();
    Task<ApiResponse<DepartmentDto>> GetDepartmentByIdAsync(int departmentId);
    Task<ApiResponse<List<UserDto>>> GetDepartmentUsersAsync(int departmentId);
}