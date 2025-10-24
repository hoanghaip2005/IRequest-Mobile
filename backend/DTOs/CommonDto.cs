namespace IRequest.Api.DTOs;

public class CommentDto
{
    public int CommentId { get; set; }
    public string Content { get; set; } = string.Empty;
    public bool IsInternal { get; set; }
    public int RequestId { get; set; }
    public string UserId { get; set; } = string.Empty;
    public UserDto? User { get; set; }
    public DateTime CreatedAt { get; set; }
    public DateTime UpdatedAt { get; set; }
}

public class CreateCommentDto
{
    public string Content { get; set; } = string.Empty;
    public bool IsInternal { get; set; } = false;
}

public class NotificationDto
{
    public int NotificationId { get; set; }
    public string Title { get; set; } = string.Empty;
    public string Message { get; set; } = string.Empty;
    public string Type { get; set; } = string.Empty;
    public bool IsRead { get; set; }
    public string? ActionUrl { get; set; }
    public int? RequestId { get; set; }
    public DateTime CreatedAt { get; set; }
    public DateTime? ReadAt { get; set; }
}

public class RequestHistoryDto
{
    public int RequestHistoryId { get; set; }
    public string Action { get; set; } = string.Empty;
    public string? Details { get; set; }
    public string? OldValue { get; set; }
    public string? NewValue { get; set; }
    public int RequestId { get; set; }
    public string UserId { get; set; } = string.Empty;
    public UserDto? User { get; set; }
    public DateTime CreatedAt { get; set; }
}