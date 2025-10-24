package com.project.irequest.data.remote.dto

import com.google.gson.annotations.SerializedName

// Auth DTOs
data class LoginDto(
    val email: String,
    val password: String
)

data class RegisterDto(
    val email: String,
    val password: String,
    val confirmPassword: String,
    val fullName: String,
    val employeeCode: String? = null,
    val departmentId: Int? = null,
    val phoneNumber: String? = null
)

data class AuthResponseDto(
    val token: String,
    val refreshToken: String,
    val expiration: String,
    val user: UserDto
)

data class RefreshTokenDto(
    val token: String,
    val refreshToken: String
)

// User DTOs
data class UserDto(
    val id: String,
    val email: String,
    val userName: String,
    val fullName: String?,
    val employeeCode: String?,
    val phoneNumber: String?,
    val avatar: String?,
    val birthDate: String?,
    val departmentId: Int?,
    val department: DepartmentDto?,
    val roles: List<String>,
    val createdAt: String,
    val isActive: Boolean
)

data class UpdateUserDto(
    val fullName: String?,
    val phoneNumber: String?,
    val birthDate: String?,
    val departmentId: Int?
)

data class ChangePasswordDto(
    val currentPassword: String,
    val newPassword: String,
    val confirmNewPassword: String
)

// Request DTOs
data class RequestDto(
    val requestId: Int,
    val title: String,
    val description: String?,
    val attachmentUrl: String?,
    val attachmentFileName: String?,
    val isApproved: Boolean,
    val statusId: Int?,
    val status: StatusDto?,
    val priorityId: Int?,
    val priority: PriorityDto?,
    val workflowId: Int?,
    val workflow: WorkflowDto?,
    val userId: String,
    val user: UserDto?,
    val assignedUserId: String?,
    val assignedUser: UserDto?,
    val createdAt: String,
    val updatedAt: String,
    val closedAt: String?,
    val approvedAt: String?,
    val currentStepOrder: Int,
    val resolution: String?,
    val requestCode: String?,
    val comments: List<CommentDto>,
    val isStarred: Boolean,
    val commentsCount: Int
)

data class CreateRequestDto(
    val title: String,
    val description: String?,
    val priorityId: Int?,
    val workflowId: Int?
)

data class UpdateRequestDto(
    val title: String?,
    val description: String?,
    val priorityId: Int?,
    val statusId: Int?,
    val assignedUserId: String?,
    val resolution: String?
)

data class ApproveRequestDto(
    val approve: Boolean,
    val comment: String?,
    val assignToUserId: String?
)

// Master Data DTOs
data class DepartmentDto(
    val departmentId: Int,
    val name: String,
    val description: String?,
    val code: String?,
    val managerId: String?,
    val manager: UserDto?,
    val isActive: Boolean,
    val createdAt: String,
    val usersCount: Int
)

data class PriorityDto(
    val priorityId: Int,
    val name: String,
    val description: String?,
    val level: Int,
    val color: String?,
    val slaTarget: Double?,
    val isActive: Boolean
)

data class StatusDto(
    val statusId: Int,
    val name: String,
    val description: String?,
    val color: String?,
    val isActive: Boolean,
    val isFinalStatus: Boolean
)

data class WorkflowDto(
    val workflowId: Int,
    val name: String,
    val description: String?,
    val isActive: Boolean,
    val isDefault: Boolean,
    val steps: List<WorkflowStepDto>
)

data class WorkflowStepDto(
    val workflowStepId: Int,
    val name: String,
    val description: String?,
    val order: Int,
    val requiredRole: String,
    val requireComment: Boolean,
    val isOptional: Boolean,
    val isActive: Boolean
)

// Comment DTOs
data class CommentDto(
    val commentId: Int,
    val content: String,
    val isInternal: Boolean,
    val requestId: Int,
    val userId: String,
    val user: UserDto?,
    val createdAt: String,
    val updatedAt: String
)

data class CreateCommentDto(
    val content: String,
    val isInternal: Boolean = false
)

// Notification DTOs
data class NotificationDto(
    val notificationId: Int,
    val title: String,
    val message: String,
    val type: String,
    val isRead: Boolean,
    val actionUrl: String?,
    val requestId: Int?,
    val createdAt: String,
    val readAt: String?
)

// API Response DTOs
data class ApiResponse<T>(
    val success: Boolean,
    val message: String?,
    val data: T?,
    val errors: List<String>?
)

data class PagedResponse<T>(
    val data: List<T>,
    val totalCount: Int,
    val page: Int,
    val pageSize: Int,
    val totalPages: Int,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean
)