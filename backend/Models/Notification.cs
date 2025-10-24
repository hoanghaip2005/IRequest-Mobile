using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace IRequest.Api.Models;

[Table("Notifications")]
public class Notification
{
    [Key]
    public int NotificationId { get; set; }

    [Required]
    [StringLength(200)]
    public string Title { get; set; } = string.Empty;

    [Required]
    [StringLength(1000)]
    public string Message { get; set; } = string.Empty;

    [StringLength(50)]
    public string Type { get; set; } = "info"; // info, warning, error, success

    public bool IsRead { get; set; } = false;

    [StringLength(500)]
    public string? ActionUrl { get; set; }

    // Foreign Keys
    [Required]
    public string UserId { get; set; } = string.Empty;
    [ForeignKey("UserId")]
    public AppUser User { get; set; } = null!;

    public int? RequestId { get; set; }
    [ForeignKey("RequestId")]
    public Request? Request { get; set; }

    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public DateTime? ReadAt { get; set; }
}