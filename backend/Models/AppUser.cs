using Microsoft.AspNetCore.Identity;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace IRequest.Api.Models;

public class AppUser : IdentityUser
{
    [Column(TypeName = "nvarchar")]
    [StringLength(400)]
    public string? HomeAddress { get; set; }

    [Column(TypeName = "nvarchar")]
    [StringLength(400)]
    public string? Avatar { get; set; }

    [DataType(DataType.Date)]
    public DateTime? BirthDate { get; set; }

    public int? DepartmentId { get; set; }

    [ForeignKey("DepartmentId")]
    public Department? Department { get; set; }

    [StringLength(200)]
    public string? FullName { get; set; }

    [StringLength(20)]
    public string? EmployeeCode { get; set; }

    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public DateTime UpdatedAt { get; set; } = DateTime.UtcNow;
    public bool IsActive { get; set; } = true;

    // Navigation properties
    public ICollection<Request> Requests { get; set; } = new HashSet<Request>();
    public ICollection<Request> AssignedRequests { get; set; } = new HashSet<Request>();
    public ICollection<Comment> Comments { get; set; } = new HashSet<Comment>();
    public ICollection<RequestHistory> RequestHistories { get; set; } = new HashSet<RequestHistory>();
}