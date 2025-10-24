using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace IRequest.Api.Models;

[Table("Comments")]
public class Comment
{
    [Key]
    public int CommentId { get; set; }

    [Required]
    [StringLength(1000)]
    public string Content { get; set; } = string.Empty;

    public bool IsInternal { get; set; } = false; // True for internal comments (not visible to requester)

    // Foreign Keys
    [Required]
    public int RequestId { get; set; }
    [ForeignKey("RequestId")]
    public Request Request { get; set; } = null!;

    [Required]
    public string UserId { get; set; } = string.Empty;
    [ForeignKey("UserId")]
    public AppUser User { get; set; } = null!;

    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public DateTime UpdatedAt { get; set; } = DateTime.UtcNow;
}

[Table("RequestHistories")]
public class RequestHistory
{
    [Key]
    public int RequestHistoryId { get; set; }

    [Required]
    [StringLength(100)]
    public string Action { get; set; } = string.Empty; // Created, Updated, Approved, Rejected, etc.

    [StringLength(1000)]
    public string? Details { get; set; }

    [StringLength(100)]
    public string? OldValue { get; set; }

    [StringLength(100)]
    public string? NewValue { get; set; }

    // Foreign Keys
    [Required]
    public int RequestId { get; set; }
    [ForeignKey("RequestId")]
    public Request Request { get; set; } = null!;

    [Required]
    public string UserId { get; set; } = string.Empty;
    [ForeignKey("UserId")]
    public AppUser User { get; set; } = null!;

    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
}

[Table("RequestStars")]
public class RequestStar
{
    [Key]
    public int RequestStarId { get; set; }

    // Foreign Keys
    [Required]
    public int RequestId { get; set; }
    [ForeignKey("RequestId")]
    public Request Request { get; set; } = null!;

    [Required]
    public string UserId { get; set; } = string.Empty;
    [ForeignKey("UserId")]
    public AppUser User { get; set; } = null!;

    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
}