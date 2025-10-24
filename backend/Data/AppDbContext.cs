using IRequest.Api.Models;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace IRequest.Api.Data;

public class AppDbContext : IdentityDbContext<AppUser>
{
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options)
    {
    }

    public DbSet<Request> Requests { get; set; }
    public DbSet<Department> Departments { get; set; }
    public DbSet<Priority> Priorities { get; set; }
    public DbSet<Status> Statuses { get; set; }
    public DbSet<Workflow> Workflows { get; set; }
    public DbSet<WorkflowStep> WorkflowSteps { get; set; }
    public DbSet<Comment> Comments { get; set; }
    public DbSet<RequestHistory> RequestHistories { get; set; }
    public DbSet<RequestStar> RequestStars { get; set; }
    public DbSet<Notification> Notifications { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);

        // Rename Identity tables to remove AspNet prefix
        foreach (var entityType in modelBuilder.Model.GetEntityTypes())
        {
            var tableName = entityType.GetTableName();
            if (!string.IsNullOrEmpty(tableName) && tableName.StartsWith("AspNet"))
            {
                entityType.SetTableName(tableName.Substring(6));
            }
        }

        // Configure AppUser
        modelBuilder.Entity<AppUser>(entity =>
        {
            entity.ToTable("Users");

            // One-to-many: Department -> Users
            entity.HasOne(u => u.Department)
                  .WithMany(d => d.Users)
                  .HasForeignKey(u => u.DepartmentId)
                  .OnDelete(DeleteBehavior.SetNull);

            // One-to-many: User -> Requests (as requester)
            entity.HasMany(u => u.Requests)
                  .WithOne(r => r.User)
                  .HasForeignKey(r => r.UserId)
                  .OnDelete(DeleteBehavior.Restrict);

            // One-to-many: User -> Requests (as assignee)
            entity.HasMany(u => u.AssignedRequests)
                  .WithOne(r => r.AssignedUser)
                  .HasForeignKey(r => r.AssignedUserId)
                  .OnDelete(DeleteBehavior.SetNull);
        });

        // Configure Request
        modelBuilder.Entity<Request>(entity =>
        {
            entity.HasKey(r => r.RequestId);

            // Foreign key relationships
            entity.HasOne(r => r.Status)
                  .WithMany(s => s.Requests)
                  .HasForeignKey(r => r.StatusId)
                  .OnDelete(DeleteBehavior.SetNull);

            entity.HasOne(r => r.Priority)
                  .WithMany(p => p.Requests)
                  .HasForeignKey(r => r.PriorityId)
                  .OnDelete(DeleteBehavior.SetNull);

            entity.HasOne(r => r.Workflow)
                  .WithMany(w => w.Requests)
                  .HasForeignKey(r => r.WorkflowId)
                  .OnDelete(DeleteBehavior.SetNull);

            // One-to-many: Request -> Comments
            entity.HasMany(r => r.Comments)
                  .WithOne(c => c.Request)
                  .HasForeignKey(c => c.RequestId)
                  .OnDelete(DeleteBehavior.Cascade);

            // One-to-many: Request -> RequestHistories
            entity.HasMany(r => r.Histories)
                  .WithOne(h => h.Request)
                  .HasForeignKey(h => h.RequestId)
                  .OnDelete(DeleteBehavior.Cascade);

            // One-to-many: Request -> RequestStars
            entity.HasMany(r => r.Stars)
                  .WithOne(s => s.Request)
                  .HasForeignKey(s => s.RequestId)
                  .OnDelete(DeleteBehavior.Cascade);
        });

        // Configure Department
        modelBuilder.Entity<Department>(entity =>
        {
            entity.HasKey(d => d.DepartmentId);

            // Self-referencing: Department -> Manager (User)
            entity.HasOne(d => d.Manager)
                  .WithMany()
                  .HasForeignKey(d => d.ManagerId)
                  .OnDelete(DeleteBehavior.SetNull);
        });

        // Configure Priority
        modelBuilder.Entity<Priority>(entity =>
        {
            entity.HasKey(p => p.PriorityId);

            // Configure decimal precision
            entity.Property(p => p.SlaTarget)
                  .HasColumnType("decimal(5,2)");
            entity.Property(p => p.WarningThreshold)
                  .HasColumnType("decimal(5,2)");
            entity.Property(p => p.CriticalThreshold)
                  .HasColumnType("decimal(5,2)");
        });

        // Configure Status
        modelBuilder.Entity<Status>(entity =>
        {
            entity.HasKey(s => s.StatusId);
        });

        // Configure Workflow
        modelBuilder.Entity<Workflow>(entity =>
        {
            entity.HasKey(w => w.WorkflowId);

            // One-to-many: Workflow -> WorkflowSteps
            entity.HasMany(w => w.Steps)
                  .WithOne(s => s.Workflow)
                  .HasForeignKey(s => s.WorkflowId)
                  .OnDelete(DeleteBehavior.Cascade);
        });

        // Configure WorkflowStep
        modelBuilder.Entity<WorkflowStep>(entity =>
        {
            entity.HasKey(ws => ws.WorkflowStepId);
        });

        // Configure Comment
        modelBuilder.Entity<Comment>(entity =>
        {
            entity.HasKey(c => c.CommentId);

            entity.HasOne(c => c.User)
                  .WithMany(u => u.Comments)
                  .HasForeignKey(c => c.UserId)
                  .OnDelete(DeleteBehavior.Restrict);
        });

        // Configure RequestHistory
        modelBuilder.Entity<RequestHistory>(entity =>
        {
            entity.HasKey(rh => rh.RequestHistoryId);

            entity.HasOne(rh => rh.User)
                  .WithMany(u => u.RequestHistories)
                  .HasForeignKey(rh => rh.UserId)
                  .OnDelete(DeleteBehavior.Restrict);
        });

        // Configure RequestStar
        modelBuilder.Entity<RequestStar>(entity =>
        {
            entity.HasKey(rs => rs.RequestStarId);

            // Unique constraint: one star per user per request
            entity.HasIndex(rs => new { rs.RequestId, rs.UserId })
                  .IsUnique();

            entity.HasOne(rs => rs.User)
                  .WithMany()
                  .HasForeignKey(rs => rs.UserId)
                  .OnDelete(DeleteBehavior.Cascade);
        });

        // Configure Notification
        modelBuilder.Entity<Notification>(entity =>
        {
            entity.HasKey(n => n.NotificationId);

            entity.HasOne(n => n.User)
                  .WithMany()
                  .HasForeignKey(n => n.UserId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(n => n.Request)
                  .WithMany()
                  .HasForeignKey(n => n.RequestId)
                  .OnDelete(DeleteBehavior.SetNull);
        });
    }
}