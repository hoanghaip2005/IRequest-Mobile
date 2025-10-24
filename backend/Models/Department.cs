using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace IRequest.Api.Models;

[Table("Departments")]
public class Department
{
    [Key]
    public int DepartmentId { get; set; }

    [Required]
    [StringLength(100)]
    public string Name { get; set; } = string.Empty;

    [StringLength(500)]
    public string? Description { get; set; }

    [StringLength(50)]
    public string? Code { get; set; }

    public string? ManagerId { get; set; }
    [ForeignKey("ManagerId")]
    public AppUser? Manager { get; set; }

    public bool IsActive { get; set; } = true;

    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public DateTime UpdatedAt { get; set; } = DateTime.UtcNow;

    // Navigation properties
    public ICollection<AppUser> Users { get; set; } = new HashSet<AppUser>();
}