package com.project.irequest.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val email: String,
    val userName: String,
    val fullName: String?,
    val employeeCode: String?,
    val phoneNumber: String?,
    val avatar: String?,
    val birthDate: String?,
    val departmentId: Int?,
    val departmentName: String?,
    val roles: String, // JSON string of roles
    val createdAt: String,
    val isActive: Boolean
)

@Entity(tableName = "requests")
data class RequestEntity(
    @PrimaryKey
    val requestId: Int,
    val title: String,
    val description: String?,
    val attachmentUrl: String?,
    val attachmentFileName: String?,
    val isApproved: Boolean,
    val statusId: Int?,
    val statusName: String?,
    val statusColor: String?,
    val priorityId: Int?,
    val priorityName: String?,
    val priorityColor: String?,
    val priorityLevel: Int?,
    val workflowId: Int?,
    val workflowName: String?,
    val userId: String,
    val userFullName: String?,
    val assignedUserId: String?,
    val assignedUserFullName: String?,
    val createdAt: String,
    val updatedAt: String,
    val closedAt: String?,
    val approvedAt: String?,
    val currentStepOrder: Int,
    val resolution: String?,
    val requestCode: String?,
    val isStarred: Boolean,
    val commentsCount: Int,
    val isSynced: Boolean = true,
    val lastSyncAt: String?
)

@Entity(tableName = "departments")
data class DepartmentEntity(
    @PrimaryKey
    val departmentId: Int,
    val name: String,
    val description: String?,
    val code: String?,
    val managerId: String?,
    val managerName: String?,
    val isActive: Boolean,
    val createdAt: String,
    val usersCount: Int
)

@Entity(tableName = "priorities")
data class PriorityEntity(
    @PrimaryKey
    val priorityId: Int,
    val name: String,
    val description: String?,
    val level: Int,
    val color: String?,
    val slaTarget: Double?,
    val isActive: Boolean
)

@Entity(tableName = "statuses")
data class StatusEntity(
    @PrimaryKey
    val statusId: Int,
    val name: String,
    val description: String?,
    val color: String?,
    val isActive: Boolean,
    val isFinalStatus: Boolean
)

@Entity(tableName = "workflows")
data class WorkflowEntity(
    @PrimaryKey
    val workflowId: Int,
    val name: String,
    val description: String?,
    val isActive: Boolean,
    val isDefault: Boolean
)

@Entity(tableName = "workflow_steps")
data class WorkflowStepEntity(
    @PrimaryKey
    val workflowStepId: Int,
    val workflowId: Int,
    val name: String,
    val description: String?,
    val order: Int,
    val requiredRole: String,
    val requireComment: Boolean,
    val isOptional: Boolean,
    val isActive: Boolean
)

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey
    val commentId: Int,
    val content: String,
    val isInternal: Boolean,
    val requestId: Int,
    val userId: String,
    val userFullName: String?,
    val userAvatar: String?,
    val createdAt: String,
    val updatedAt: String,
    val isSynced: Boolean = true
)

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey
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