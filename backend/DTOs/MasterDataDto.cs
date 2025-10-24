namespace IRequest.Api.DTOs;

public class DepartmentDto
{
    public int DepartmentId { get; set; }
    public string Name { get; set; } = string.Empty;
    public string? Description { get; set; }
    public string? Code { get; set; }
    public string? ManagerId { get; set; }
    public UserDto? Manager { get; set; }
    public bool IsActive { get; set; }
    public DateTime CreatedAt { get; set; }
    public int UsersCount { get; set; }
}

public class PriorityDto
{
    public int PriorityId { get; set; }
    public string Name { get; set; } = string.Empty;
    public string? Description { get; set; }
    public int Level { get; set; }
    public string? Color { get; set; }
    public decimal? SlaTarget { get; set; }
    public bool IsActive { get; set; }
}

public class StatusDto
{
    public int StatusId { get; set; }
    public string Name { get; set; } = string.Empty;
    public string? Description { get; set; }
    public string? Color { get; set; }
    public bool IsActive { get; set; }
    public bool IsFinalStatus { get; set; }
}

public class WorkflowDto
{
    public int WorkflowId { get; set; }
    public string Name { get; set; } = string.Empty;
    public string? Description { get; set; }
    public bool IsActive { get; set; }
    public bool IsDefault { get; set; }
    public List<WorkflowStepDto> Steps { get; set; } = new List<WorkflowStepDto>();
}

public class WorkflowStepDto
{
    public int WorkflowStepId { get; set; }
    public string Name { get; set; } = string.Empty;
    public string? Description { get; set; }
    public int Order { get; set; }
    public string RequiredRole { get; set; } = string.Empty;
    public bool RequireComment { get; set; }
    public bool IsOptional { get; set; }
    public bool IsActive { get; set; }
}