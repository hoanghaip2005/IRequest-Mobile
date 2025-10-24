using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace IRequest.Api.Models;

[Table("Requests")]
public class Request
{
    [Key]
    public int RequestId { get; set; }

    [Column(TypeName = "nvarchar(100)")]
    [Required(ErrorMessage = "Title is required")]
    [StringLength(100, MinimumLength = 3)]
    public string Title { get; set; } = string.Empty;

    [DataType(DataType.Text)]
    public string? Description { get; set; }

    [StringLength(500)]
    public string? AttachmentUrl { get; set; }

    [StringLength(255)]
    public string? AttachmentFileName { get; set; }

    [StringLength(100)]
    public string? AttachmentFileType { get; set; }

    public long? AttachmentFileSize { get; set; }

    public bool IsApproved { get; set; } = false;

    // Foreign Keys
    public int? StatusId { get; set; }
    [ForeignKey("StatusId")]
    public Status? Status { get; set; }

    public int? PriorityId { get; set; }
    [ForeignKey("PriorityId")]
    public Priority? Priority { get; set; }

    public int? WorkflowId { get; set; }
    [ForeignKey("WorkflowId")]
    public Workflow? Workflow { get; set; }

    [Required]
    public string UserId { get; set; } = string.Empty;
    [ForeignKey("UserId")]
    public AppUser? User { get; set; }

    public string? AssignedUserId { get; set; }
    [ForeignKey("AssignedUserId")]
    public AppUser? AssignedUser { get; set; }

    // Dates
    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public DateTime UpdatedAt { get; set; } = DateTime.UtcNow;
    public DateTime? ClosedAt { get; set; }
    public DateTime? ApprovedAt { get; set; }

    // Workflow tracking
    public int CurrentStepOrder { get; set; } = 1;

    [Required]
    [StringLength(50)]
    public string RoleId { get; set; } = "user";

    [StringLength(1000)]
    public string? Resolution { get; set; }

    [StringLength(200)]
    public string? RequestCode { get; set; }

    // Navigation properties
    public ICollection<Comment> Comments { get; set; } = new HashSet<Comment>();
    public ICollection<RequestHistory> Histories { get; set; } = new HashSet<RequestHistory>();
    public ICollection<RequestStar> Stars { get; set; } = new HashSet<RequestStar>();
}