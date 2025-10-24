namespace IRequest.Api.DTOs;

public class RequestDto
{
    public int RequestId { get; set; }
    public string Title { get; set; } = string.Empty;
    public string? Description { get; set; }
    public string? AttachmentUrl { get; set; }
    public string? AttachmentFileName { get; set; }
    public bool IsApproved { get; set; }
    public int? StatusId { get; set; }
    public StatusDto? Status { get; set; }
    public int? PriorityId { get; set; }
    public PriorityDto? Priority { get; set; }
    public int? WorkflowId { get; set; }
    public WorkflowDto? Workflow { get; set; }
    public string UserId { get; set; } = string.Empty;
    public UserDto? User { get; set; }
    public string? AssignedUserId { get; set; }
    public UserDto? AssignedUser { get; set; }
    public DateTime CreatedAt { get; set; }
    public DateTime UpdatedAt { get; set; }
    public DateTime? ClosedAt { get; set; }
    public DateTime? ApprovedAt { get; set; }
    public int CurrentStepOrder { get; set; }
    public string? Resolution { get; set; }
    public string? RequestCode { get; set; }
    public List<CommentDto> Comments { get; set; } = new List<CommentDto>();
    public bool IsStarred { get; set; }
    public int CommentsCount { get; set; }
}

public class CreateRequestDto
{
    public string Title { get; set; } = string.Empty;
    public string? Description { get; set; }
    public int? PriorityId { get; set; }
    public int? WorkflowId { get; set; }
    public IFormFile? AttachmentFile { get; set; }
}

public class UpdateRequestDto
{
    public string? Title { get; set; }
    public string? Description { get; set; }
    public int? PriorityId { get; set; }
    public int? StatusId { get; set; }
    public string? AssignedUserId { get; set; }
    public string? Resolution { get; set; }
}

public class ApproveRequestDto
{
    public bool Approve { get; set; }
    public string? Comment { get; set; }
    public string? AssignToUserId { get; set; }
}

public class RequestFilterDto
{
    public int Page { get; set; } = 1;
    public int PageSize { get; set; } = 10;
    public string? Search { get; set; }
    public int? StatusId { get; set; }
    public int? PriorityId { get; set; }
    public int? DepartmentId { get; set; }
    public string? AssignedUserId { get; set; }
    public string? CreatedBy { get; set; }
    public DateTime? FromDate { get; set; }
    public DateTime? ToDate { get; set; }
    public string SortBy { get; set; } = "CreatedAt";
    public string SortOrder { get; set; } = "desc";
    public bool? OnlyStarred { get; set; }
    public bool? OnlyMyRequests { get; set; }
}