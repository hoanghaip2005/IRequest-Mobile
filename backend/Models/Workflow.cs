using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace IRequest.Api.Models;

[Table("Workflows")]
public class Workflow
{
    [Key]
    public int WorkflowId { get; set; }

    [Required]
    [StringLength(100)]
    public string Name { get; set; } = string.Empty;

    [StringLength(500)]
    public string? Description { get; set; }

    public bool IsActive { get; set; } = true;
    public bool IsDefault { get; set; } = false;

    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public DateTime UpdatedAt { get; set; } = DateTime.UtcNow;

    // Navigation properties
    public ICollection<WorkflowStep> Steps { get; set; } = new HashSet<WorkflowStep>();
    public ICollection<Request> Requests { get; set; } = new HashSet<Request>();
}

[Table("WorkflowSteps")]
public class WorkflowStep
{
    [Key]
    public int WorkflowStepId { get; set; }

    [Required]
    [StringLength(100)]
    public string Name { get; set; } = string.Empty;

    [StringLength(500)]
    public string? Description { get; set; }

    [Range(1, 100)]
    public int Order { get; set; } = 1;

    [Required]
    [StringLength(50)]
    public string RequiredRole { get; set; } = string.Empty; // Role required to process this step

    public bool RequireComment { get; set; } = false;
    public bool IsOptional { get; set; } = false;
    public bool IsActive { get; set; } = true;

    // Foreign Key
    public int WorkflowId { get; set; }
    [ForeignKey("WorkflowId")]
    public Workflow Workflow { get; set; } = null!;

    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public DateTime UpdatedAt { get; set; } = DateTime.UtcNow;
}