using IRequest.Api.Models;
using Microsoft.AspNetCore.Identity;

namespace IRequest.Api.Data;

public static class SeedData
{
    public static async Task Initialize(AppDbContext context, UserManager<AppUser> userManager, RoleManager<IdentityRole> roleManager)
    {
        // Seed Roles
        await SeedRoles(roleManager);

        // Seed Departments
        await SeedDepartments(context);

        // Seed Priorities
        await SeedPriorities(context);

        // Seed Statuses
        await SeedStatuses(context);

        // Seed Workflows
        await SeedWorkflows(context);

        // Seed Users
        await SeedUsers(userManager, context);

        await context.SaveChangesAsync();
    }

    private static async Task SeedRoles(RoleManager<IdentityRole> roleManager)
    {
        string[] roleNames = { "Administrator", "Manager", "User", "IT Support" };

        foreach (var roleName in roleNames)
        {
            if (!await roleManager.RoleExistsAsync(roleName))
            {
                await roleManager.CreateAsync(new IdentityRole(roleName));
            }
        }
    }

    private static async Task SeedDepartments(AppDbContext context)
    {
        if (!context.Departments.Any())
        {
            var departments = new[]
            {
                new Department { Name = "Information Technology", Code = "IT", Description = "IT Department" },
                new Department { Name = "Human Resources", Code = "HR", Description = "HR Department" },
                new Department { Name = "Finance", Code = "FIN", Description = "Finance Department" },
                new Department { Name = "Operations", Code = "OPS", Description = "Operations Department" },
                new Department { Name = "Marketing", Code = "MKT", Description = "Marketing Department" }
            };

            context.Departments.AddRange(departments);
        }
    }

    private static async Task SeedPriorities(AppDbContext context)
    {
        if (!context.Priorities.Any())
        {
            var priorities = new[]
            {
                new Priority { Name = "Low", Level = 1, Color = "#28a745", SlaTarget = 72, Description = "Low priority - 3 days SLA" },
                new Priority { Name = "Medium", Level = 5, Color = "#ffc107", SlaTarget = 48, Description = "Medium priority - 2 days SLA" },
                new Priority { Name = "High", Level = 8, Color = "#fd7e14", SlaTarget = 24, Description = "High priority - 1 day SLA" },
                new Priority { Name = "Critical", Level = 10, Color = "#dc3545", SlaTarget = 4, Description = "Critical priority - 4 hours SLA" }
            };

            context.Priorities.AddRange(priorities);
        }
    }

    private static async Task SeedStatuses(AppDbContext context)
    {
        if (!context.Statuses.Any())
        {
            var statuses = new[]
            {
                new Status { Name = "Draft", Color = "#6c757d", Description = "Request is being prepared" },
                new Status { Name = "Submitted", Color = "#007bff", Description = "Request has been submitted" },
                new Status { Name = "In Review", Color = "#ffc107", Description = "Request is being reviewed" },
                new Status { Name = "Approved", Color = "#28a745", Description = "Request has been approved" },
                new Status { Name = "In Progress", Color = "#17a2b8", Description = "Request is being processed" },
                new Status { Name = "Completed", Color = "#28a745", IsFinalStatus = true, Description = "Request has been completed" },
                new Status { Name = "Rejected", Color = "#dc3545", IsFinalStatus = true, Description = "Request has been rejected" },
                new Status { Name = "Cancelled", Color = "#6c757d", IsFinalStatus = true, Description = "Request has been cancelled" }
            };

            context.Statuses.AddRange(statuses);
        }
    }

    private static async Task SeedWorkflows(AppDbContext context)
    {
        if (!context.Workflows.Any())
        {
            var workflow = new Workflow
            {
                Name = "Standard IT Request",
                Description = "Standard workflow for IT equipment requests",
                IsDefault = true
            };

            context.Workflows.Add(workflow);
            await context.SaveChangesAsync();

            var steps = new[]
            {
                new WorkflowStep { WorkflowId = workflow.WorkflowId, Name = "Manager Approval", Order = 1, RequiredRole = "Manager", Description = "Manager must approve the request" },
                new WorkflowStep { WorkflowId = workflow.WorkflowId, Name = "IT Review", Order = 2, RequiredRole = "IT Support", Description = "IT team reviews technical requirements" },
                new WorkflowStep { WorkflowId = workflow.WorkflowId, Name = "Procurement", Order = 3, RequiredRole = "Administrator", Description = "Procurement of equipment" },
                new WorkflowStep { WorkflowId = workflow.WorkflowId, Name = "Delivery", Order = 4, RequiredRole = "IT Support", Description = "Equipment delivery and setup" }
            };

            context.WorkflowSteps.AddRange(steps);
        }
    }

    private static async Task SeedUsers(UserManager<AppUser> userManager, AppDbContext context)
    {
        // Create admin user
        if (await userManager.FindByEmailAsync("admin@irequest.com") == null)
        {
            var itDepartment = context.Departments.First(d => d.Code == "IT");

            var adminUser = new AppUser
            {
                UserName = "admin@irequest.com",
                Email = "admin@irequest.com",
                EmailConfirmed = true,
                FullName = "System Administrator",
                EmployeeCode = "ADM001",
                DepartmentId = itDepartment.DepartmentId,
                IsActive = true
            };

            var result = await userManager.CreateAsync(adminUser, "Admin@123");
            if (result.Succeeded)
            {
                await userManager.AddToRoleAsync(adminUser, "Administrator");
            }
        }

        // Create test manager
        if (await userManager.FindByEmailAsync("manager@irequest.com") == null)
        {
            var hrDepartment = context.Departments.First(d => d.Code == "HR");

            var managerUser = new AppUser
            {
                UserName = "manager@irequest.com",
                Email = "manager@irequest.com",
                EmailConfirmed = true,
                FullName = "Test Manager",
                EmployeeCode = "MGR001",
                DepartmentId = hrDepartment.DepartmentId,
                IsActive = true
            };

            var result = await userManager.CreateAsync(managerUser, "Manager@123");
            if (result.Succeeded)
            {
                await userManager.AddToRoleAsync(managerUser, "Manager");
            }
        }

        // Create test user
        if (await userManager.FindByEmailAsync("user@irequest.com") == null)
        {
            var finDepartment = context.Departments.First(d => d.Code == "FIN");

            var testUser = new AppUser
            {
                UserName = "user@irequest.com",
                Email = "user@irequest.com",
                EmailConfirmed = true,
                FullName = "Test User",
                EmployeeCode = "USR001",
                DepartmentId = finDepartment.DepartmentId,
                IsActive = true
            };

            var result = await userManager.CreateAsync(testUser, "User@123");
            if (result.Succeeded)
            {
                await userManager.AddToRoleAsync(testUser, "User");
            }
        }
    }
}