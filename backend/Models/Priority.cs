using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace IRequest.Api.Models;

[Table("Priorities")]
public class Priority
{
    [Key]
    public int PriorityId { get; set; }

    [Required]
    [StringLength(50)]
    public string Name { get; set; } = string.Empty;

    [StringLength(500)]
    public string? Description { get; set; }

    [Range(1, 10)]
    public int Level { get; set; } = 1; // 1 = Low, 5 = Medium, 10 = High

    [StringLength(7)] // For hex color codes like #FF0000
    public string? Color { get; set; }

    [Column(TypeName = "decimal(5,2)")]
    public decimal? SlaTarget { get; set; } // SLA target in hours

    [Column(TypeName = "decimal(5,2)")]
    public decimal? WarningThreshold { get; set; } // Warning threshold percentage

    [Column(TypeName = "decimal(5,2)")]
    public decimal? CriticalThreshold { get; set; } // Critical threshold percentage

    public bool IsActive { get; set; } = true;

    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public DateTime UpdatedAt { get; set; } = DateTime.UtcNow;

    // Navigation properties
    public ICollection<Request> Requests { get; set; } = new HashSet<Request>();
}

[Table("Statuses")]
public class Status
{
    [Key]
    public int StatusId { get; set; }

    [Required]
    [StringLength(50)]
    public string Name { get; set; } = string.Empty;

    [StringLength(500)]
    public string? Description { get; set; }

    [StringLength(7)] // For hex color codes
    public string? Color { get; set; }

    public bool IsActive { get; set; } = true;
    public bool IsFinalStatus { get; set; } = false; // True for Completed, Rejected, etc.

    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public DateTime UpdatedAt { get; set; } = DateTime.UtcNow;

    // Navigation properties
    public ICollection<Request> Requests { get; set; } = new HashSet<Request>();
}