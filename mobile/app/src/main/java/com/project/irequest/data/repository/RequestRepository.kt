package com.project.irequest.data.repository

import com.project.irequest.data.remote.dto.RequestDto
import com.project.irequest.data.remote.dto.StatusDto
import com.project.irequest.data.remote.dto.PriorityDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Mock Repository for Request Management
 * In production, this would call a Retrofit API
 */
interface IRequestRepository {
    fun getMyRequests(userId: String): Flow<List<RequestDto>>
    fun getRequestDetail(requestId: Int): Flow<RequestDto>
    fun createRequest(requestDto: RequestDto): Flow<RequestDto>
    fun updateRequest(requestId: Int, requestDto: RequestDto): Flow<RequestDto>
    fun getTaskList(userId: String): Flow<List<RequestDto>>
    fun approveRequest(requestId: Int, comment: String? = null): Flow<Boolean>
    fun rejectRequest(requestId: Int, reason: String): Flow<Boolean>
}

class RequestRepositoryImpl : IRequestRepository {
    override fun getMyRequests(userId: String): Flow<List<RequestDto>> = flowOf(
        listOf(
            RequestDto(
                requestId = 1,
                title = "Laptop Request",
                description = "Need a new laptop for development",
                attachmentUrl = null,
                attachmentFileName = null,
                isApproved = false,
                statusId = 1,
                status = StatusDto(1, "Pending", "Awaiting approval", "#FFA500", true, false),
                priorityId = 2,
                priority = PriorityDto(2, "High", "High priority", 2, "#FF6B6B", 8.0, true),
                workflowId = 1,
                workflow = null,
                userId = userId,
                user = null,
                assignedUserId = null,
                assignedUser = null,
                createdAt = "2024-11-26T10:00:00",
                updatedAt = "2024-11-26T10:00:00",
                closedAt = null,
                approvedAt = null,
                currentStepOrder = 1,
                resolution = null,
                requestCode = "REQ-001",
                comments = emptyList(),
                isStarred = false,
                commentsCount = 0
            ),
            RequestDto(
                requestId = 2,
                title = "Office Supply Request",
                description = "Need office supplies and stationery",
                attachmentUrl = null,
                attachmentFileName = null,
                isApproved = false,
                statusId = 2,
                status = StatusDto(2, "In Progress", "Being processed", "#3498DB", true, false),
                priorityId = 3,
                priority = PriorityDto(3, "Medium", "Medium priority", 1, "#F39C12", 24.0, true),
                workflowId = 2,
                workflow = null,
                userId = userId,
                user = null,
                assignedUserId = "agent-001",
                assignedUser = null,
                createdAt = "2024-11-25T14:30:00",
                updatedAt = "2024-11-26T09:00:00",
                closedAt = null,
                approvedAt = null,
                currentStepOrder = 2,
                resolution = null,
                requestCode = "REQ-002",
                comments = emptyList(),
                isStarred = true,
                commentsCount = 2
            ),
            RequestDto(
                requestId = 3,
                title = "Leave Request",
                description = "Annual leave from Nov 27-30",
                attachmentUrl = null,
                attachmentFileName = null,
                isApproved = true,
                statusId = 3,
                status = StatusDto(3, "Approved", "Approved", "#27AE60", true, false),
                priorityId = 1,
                priority = PriorityDto(1, "Low", "Low priority", 0, "#95A5A6", 48.0, true),
                workflowId = 3,
                workflow = null,
                userId = userId,
                user = null,
                assignedUserId = "approver-001",
                assignedUser = null,
                createdAt = "2024-11-20T08:00:00",
                updatedAt = "2024-11-22T16:30:00",
                closedAt = null,
                approvedAt = "2024-11-22T16:30:00",
                currentStepOrder = 3,
                resolution = "Approved for 4 days",
                requestCode = "REQ-003",
                comments = emptyList(),
                isStarred = false,
                commentsCount = 1
            )
        )
    )

    override fun getRequestDetail(requestId: Int): Flow<RequestDto> = flowOf(
        RequestDto(
            requestId = requestId,
            title = "Detailed Request #$requestId",
            description = "This is a detailed description of request $requestId",
            attachmentUrl = null,
            attachmentFileName = null,
            isApproved = false,
            statusId = 1,
            status = StatusDto(1, "Pending", "Awaiting approval", "#FFA500", true, false),
            priorityId = 2,
            priority = PriorityDto(2, "High", "High priority", 2, "#FF6B6B", 8.0, true),
            workflowId = 1,
            workflow = null,
            userId = "user-001",
            user = null,
            assignedUserId = null,
            assignedUser = null,
            createdAt = "2024-11-26T10:00:00",
            updatedAt = "2024-11-26T10:00:00",
            closedAt = null,
            approvedAt = null,
            currentStepOrder = 1,
            resolution = null,
            requestCode = "REQ-${String.format("%03d", requestId)}",
            comments = emptyList(),
            isStarred = false,
            commentsCount = 0
        )
    )

    override fun createRequest(requestDto: RequestDto): Flow<RequestDto> = flowOf(
        requestDto.copy(requestId = System.currentTimeMillis().toInt())
    )

    override fun updateRequest(requestId: Int, requestDto: RequestDto): Flow<RequestDto> =
        flowOf(requestDto.copy(requestId = requestId))

    override fun getTaskList(userId: String): Flow<List<RequestDto>> = flowOf(
        listOf(
            RequestDto(
                requestId = 101,
                title = "Approve Laptop Request for Nguyen Van A",
                description = "Review and approve the laptop request",
                attachmentUrl = null,
                attachmentFileName = null,
                isApproved = false,
                statusId = 1,
                status = StatusDto(1, "Pending", "Awaiting approval", "#FFA500", true, false),
                priorityId = 2,
                priority = PriorityDto(2, "High", "High priority", 2, "#FF6B6B", 8.0, true),
                workflowId = 1,
                workflow = null,
                userId = "user-001",
                user = null,
                assignedUserId = userId,
                assignedUser = null,
                createdAt = "2024-11-26T10:00:00",
                updatedAt = "2024-11-26T10:00:00",
                closedAt = null,
                approvedAt = null,
                currentStepOrder = 1,
                resolution = null,
                requestCode = "REQ-101",
                comments = emptyList(),
                isStarred = false,
                commentsCount = 0
            ),
            RequestDto(
                requestId = 102,
                title = "Approve Office Supply Request",
                description = "Review and approve the office supply request",
                attachmentUrl = null,
                attachmentFileName = null,
                isApproved = false,
                statusId = 1,
                status = StatusDto(1, "Pending", "Awaiting approval", "#FFA500", true, false),
                priorityId = 3,
                priority = PriorityDto(3, "Medium", "Medium priority", 1, "#F39C12", 24.0, true),
                workflowId = 2,
                workflow = null,
                userId = "user-002",
                user = null,
                assignedUserId = userId,
                assignedUser = null,
                createdAt = "2024-11-25T14:30:00",
                updatedAt = "2024-11-26T09:00:00",
                closedAt = null,
                approvedAt = null,
                currentStepOrder = 1,
                resolution = null,
                requestCode = "REQ-102",
                comments = emptyList(),
                isStarred = false,
                commentsCount = 0
            )
        )
    )

    override fun approveRequest(requestId: Int, comment: String?): Flow<Boolean> =
        flowOf(true)

    override fun rejectRequest(requestId: Int, reason: String): Flow<Boolean> =
        flowOf(true)
}
