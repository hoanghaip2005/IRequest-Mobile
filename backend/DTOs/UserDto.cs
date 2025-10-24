namespace IRequest.Api.DTOs;

public class UserDto
{
    public string Id { get; set; } = string.Empty;
    public string Email { get; set; } = string.Empty;
    public string UserName { get; set; } = string.Empty;
    public string? FullName { get; set; }
    public string? EmployeeCode { get; set; }
    public string? PhoneNumber { get; set; }
    public string? Avatar { get; set; }
    public DateTime? BirthDate { get; set; }
    public int? DepartmentId { get; set; }
    public DepartmentDto? Department { get; set; }
    public List<string> Roles { get; set; } = new List<string>();
    public DateTime CreatedAt { get; set; }
    public bool IsActive { get; set; }
}

public class UpdateUserDto
{
    public string? FullName { get; set; }
    public string? PhoneNumber { get; set; }
    public DateTime? BirthDate { get; set; }
    public int? DepartmentId { get; set; }
}

public class ChangePasswordDto
{
    public string CurrentPassword { get; set; } = string.Empty;
    public string NewPassword { get; set; } = string.Empty;
    public string ConfirmNewPassword { get; set; } = string.Empty;
}