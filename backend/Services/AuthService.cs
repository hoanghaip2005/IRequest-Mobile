using IRequest.Api.Data;
using IRequest.Api.DTOs;
using IRequest.Api.Models;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;

namespace IRequest.Api.Services;

public class AuthService : IAuthService
{
    private readonly UserManager<AppUser> _userManager;
    private readonly SignInManager<AppUser> _signInManager;
    private readonly AppDbContext _context;
    private readonly IConfiguration _configuration;
    private readonly ILogger<AuthService> _logger;

    public AuthService(
        UserManager<AppUser> userManager,
        SignInManager<AppUser> signInManager,
        AppDbContext context,
        IConfiguration configuration,
        ILogger<AuthService> logger)
    {
        _userManager = userManager;
        _signInManager = signInManager;
        _context = context;
        _configuration = configuration;
        _logger = logger;
    }

    public async Task<ApiResponse<AuthResponseDto>> LoginAsync(LoginDto loginDto)
    {
        try
        {
            var user = await _userManager.FindByEmailAsync(loginDto.Email);
            if (user == null || !user.IsActive)
            {
                return ApiResponse<AuthResponseDto>.ErrorResponse("Invalid email or password");
            }

            var result = await _signInManager.CheckPasswordSignInAsync(user, loginDto.Password, false);
            if (!result.Succeeded)
            {
                return ApiResponse<AuthResponseDto>.ErrorResponse("Invalid email or password");
            }

            var token = await GenerateJwtToken(user);
            var userDto = await MapUserToDto(user);

            var authResponse = new AuthResponseDto
            {
                Token = token,
                RefreshToken = GenerateRefreshToken(),
                Expiration = DateTime.UtcNow.AddDays(7),
                User = userDto
            };

            return ApiResponse<AuthResponseDto>.SuccessResponse(authResponse, "Login successful");
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "Error during login for email: {Email}", loginDto.Email);
            return ApiResponse<AuthResponseDto>.ErrorResponse("An error occurred during login");
        }
    }

    public async Task<ApiResponse<AuthResponseDto>> RegisterAsync(RegisterDto registerDto)
    {
        try
        {
            if (registerDto.Password != registerDto.ConfirmPassword)
            {
                return ApiResponse<AuthResponseDto>.ErrorResponse("Passwords do not match");
            }

            var existingUser = await _userManager.FindByEmailAsync(registerDto.Email);
            if (existingUser != null)
            {
                return ApiResponse<AuthResponseDto>.ErrorResponse("Email is already registered");
            }

            var user = new AppUser
            {
                UserName = registerDto.Email,
                Email = registerDto.Email,
                FullName = registerDto.FullName,
                EmployeeCode = registerDto.EmployeeCode,
                DepartmentId = registerDto.DepartmentId,
                PhoneNumber = registerDto.PhoneNumber,
                EmailConfirmed = true, // For simplicity, auto-confirm emails
                IsActive = true
            };

            var result = await _userManager.CreateAsync(user, registerDto.Password);
            if (!result.Succeeded)
            {
                var errors = result.Errors.Select(e => e.Description).ToList();
                return ApiResponse<AuthResponseDto>.ErrorResponse("Failed to create user", errors);
            }

            // Add default User role
            await _userManager.AddToRoleAsync(user, "User");

            var token = await GenerateJwtToken(user);
            var userDto = await MapUserToDto(user);

            var authResponse = new AuthResponseDto
            {
                Token = token,
                RefreshToken = GenerateRefreshToken(),
                Expiration = DateTime.UtcNow.AddDays(7),
                User = userDto
            };

            return ApiResponse<AuthResponseDto>.SuccessResponse(authResponse, "Registration successful");
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "Error during registration for email: {Email}", registerDto.Email);
            return ApiResponse<AuthResponseDto>.ErrorResponse("An error occurred during registration");
        }
    }

    public async Task<ApiResponse<AuthResponseDto>> RefreshTokenAsync(RefreshTokenDto refreshTokenDto)
    {
        // For simplicity, we'll validate the existing token and generate a new one
        // In production, you should store and validate refresh tokens properly
        try
        {
            var tokenHandler = new JwtSecurityTokenHandler();
            var key = Encoding.UTF8.GetBytes(_configuration["Jwt:Key"]!);

            var tokenValidationParameters = new TokenValidationParameters
            {
                ValidateIssuerSigningKey = true,
                IssuerSigningKey = new SymmetricSecurityKey(key),
                ValidateIssuer = true,
                ValidIssuer = _configuration["Jwt:Issuer"],
                ValidateAudience = true,
                ValidAudience = _configuration["Jwt:Audience"],
                ValidateLifetime = false, // Don't validate lifetime for refresh
                ClockSkew = TimeSpan.Zero
            };

            var principal = tokenHandler.ValidateToken(refreshTokenDto.Token, tokenValidationParameters, out var validatedToken);
            var userId = principal.FindFirst(ClaimTypes.NameIdentifier)?.Value;

            if (string.IsNullOrEmpty(userId))
            {
                return ApiResponse<AuthResponseDto>.ErrorResponse("Invalid token");
            }

            var user = await _userManager.FindByIdAsync(userId);
            if (user == null || !user.IsActive)
            {
                return ApiResponse<AuthResponseDto>.ErrorResponse("User not found or inactive");
            }

            var newToken = await GenerateJwtToken(user);
            var userDto = await MapUserToDto(user);

            var authResponse = new AuthResponseDto
            {
                Token = newToken,
                RefreshToken = GenerateRefreshToken(),
                Expiration = DateTime.UtcNow.AddDays(7),
                User = userDto
            };

            return ApiResponse<AuthResponseDto>.SuccessResponse(authResponse, "Token refreshed successfully");
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "Error during token refresh");
            return ApiResponse<AuthResponseDto>.ErrorResponse("Invalid token");
        }
    }

    public async Task<ApiResponse<bool>> LogoutAsync(string userId)
    {
        try
        {
            // In a real application, you might want to blacklist the token or remove refresh tokens
            await _signInManager.SignOutAsync();
            return ApiResponse<bool>.SuccessResponse(true, "Logout successful");
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "Error during logout for user: {UserId}", userId);
            return ApiResponse<bool>.ErrorResponse("An error occurred during logout");
        }
    }

    public async Task<ApiResponse<UserDto>> GetCurrentUserAsync(string userId)
    {
        try
        {
            var user = await _context.Users
                .Include(u => u.Department)
                .FirstOrDefaultAsync(u => u.Id == userId);

            if (user == null)
            {
                return ApiResponse<UserDto>.ErrorResponse("User not found");
            }

            var userDto = await MapUserToDto(user);
            return ApiResponse<UserDto>.SuccessResponse(userDto);
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "Error getting current user: {UserId}", userId);
            return ApiResponse<UserDto>.ErrorResponse("An error occurred while getting user information");
        }
    }

    public async Task<ApiResponse<UserDto>> UpdateProfileAsync(string userId, UpdateUserDto updateUserDto)
    {
        try
        {
            var user = await _userManager.FindByIdAsync(userId);
            if (user == null)
            {
                return ApiResponse<UserDto>.ErrorResponse("User not found");
            }

            user.FullName = updateUserDto.FullName ?? user.FullName;
            user.PhoneNumber = updateUserDto.PhoneNumber ?? user.PhoneNumber;
            user.BirthDate = updateUserDto.BirthDate ?? user.BirthDate;
            user.DepartmentId = updateUserDto.DepartmentId ?? user.DepartmentId;
            user.UpdatedAt = DateTime.UtcNow;

            var result = await _userManager.UpdateAsync(user);
            if (!result.Succeeded)
            {
                var errors = result.Errors.Select(e => e.Description).ToList();
                return ApiResponse<UserDto>.ErrorResponse("Failed to update profile", errors);
            }

            var userDto = await MapUserToDto(user);
            return ApiResponse<UserDto>.SuccessResponse(userDto, "Profile updated successfully");
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "Error updating profile for user: {UserId}", userId);
            return ApiResponse<UserDto>.ErrorResponse("An error occurred while updating profile");
        }
    }

    public async Task<ApiResponse<bool>> ChangePasswordAsync(string userId, ChangePasswordDto changePasswordDto)
    {
        try
        {
            if (changePasswordDto.NewPassword != changePasswordDto.ConfirmNewPassword)
            {
                return ApiResponse<bool>.ErrorResponse("New passwords do not match");
            }

            var user = await _userManager.FindByIdAsync(userId);
            if (user == null)
            {
                return ApiResponse<bool>.ErrorResponse("User not found");
            }

            var result = await _userManager.ChangePasswordAsync(user, changePasswordDto.CurrentPassword, changePasswordDto.NewPassword);
            if (!result.Succeeded)
            {
                var errors = result.Errors.Select(e => e.Description).ToList();
                return ApiResponse<bool>.ErrorResponse("Failed to change password", errors);
            }

            return ApiResponse<bool>.SuccessResponse(true, "Password changed successfully");
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "Error changing password for user: {UserId}", userId);
            return ApiResponse<bool>.ErrorResponse("An error occurred while changing password");
        }
    }

    private async Task<string> GenerateJwtToken(AppUser user)
    {
        var roles = await _userManager.GetRolesAsync(user);
        var claims = new List<Claim>
        {
            new Claim(ClaimTypes.NameIdentifier, user.Id),
            new Claim(ClaimTypes.Name, user.UserName!),
            new Claim(ClaimTypes.Email, user.Email!),
            new Claim("fullName", user.FullName ?? ""),
            new Claim("employeeCode", user.EmployeeCode ?? "")
        };

        foreach (var role in roles)
        {
            claims.Add(new Claim(ClaimTypes.Role, role));
        }

        var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_configuration["Jwt:Key"]!));
        var creds = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);
        var expiry = DateTime.UtcNow.AddDays(int.Parse(_configuration["Jwt:ExpireDays"] ?? "7"));

        var token = new JwtSecurityToken(
            issuer: _configuration["Jwt:Issuer"],
            audience: _configuration["Jwt:Audience"],
            claims: claims,
            expires: expiry,
            signingCredentials: creds
        );

        return new JwtSecurityTokenHandler().WriteToken(token);
    }

    private string GenerateRefreshToken()
    {
        return Guid.NewGuid().ToString();
    }

    private async Task<UserDto> MapUserToDto(AppUser user)
    {
        var roles = await _userManager.GetRolesAsync(user);

        // Load department if not already loaded
        if (user.Department == null && user.DepartmentId.HasValue)
        {
            user.Department = await _context.Departments.FindAsync(user.DepartmentId.Value);
        }

        return new UserDto
        {
            Id = user.Id,
            Email = user.Email!,
            UserName = user.UserName!,
            FullName = user.FullName,
            EmployeeCode = user.EmployeeCode,
            PhoneNumber = user.PhoneNumber,
            Avatar = user.Avatar,
            BirthDate = user.BirthDate,
            DepartmentId = user.DepartmentId,
            Department = user.Department != null ? new DepartmentDto
            {
                DepartmentId = user.Department.DepartmentId,
                Name = user.Department.Name,
                Code = user.Department.Code
            } : null,
            Roles = roles.ToList(),
            CreatedAt = user.CreatedAt,
            IsActive = user.IsActive
        };
    }
}