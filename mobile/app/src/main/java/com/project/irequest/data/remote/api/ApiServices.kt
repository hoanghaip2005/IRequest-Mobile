package com.project.irequest.data.remote.api

import com.project.irequest.data.remote.dto.ApiResponse
import com.project.irequest.data.remote.dto.ApproveRequestDto
import com.project.irequest.data.remote.dto.AuthResponseDto
import com.project.irequest.data.remote.dto.ChangePasswordDto
import com.project.irequest.data.remote.dto.CommentDto
import com.project.irequest.data.remote.dto.CreateCommentDto
import com.project.irequest.data.remote.dto.CreateRequestDto
import com.project.irequest.data.remote.dto.DepartmentDto
import com.project.irequest.data.remote.dto.LoginDto
import com.project.irequest.data.remote.dto.NotificationDto
import com.project.irequest.data.remote.dto.PagedResponse
import com.project.irequest.data.remote.dto.PriorityDto
import com.project.irequest.data.remote.dto.RefreshTokenDto
import com.project.irequest.data.remote.dto.RegisterDto
import com.project.irequest.data.remote.dto.RequestDto
import com.project.irequest.data.remote.dto.StatusDto
import com.project.irequest.data.remote.dto.UpdateRequestDto
import com.project.irequest.data.remote.dto.UpdateUserDto
import com.project.irequest.data.remote.dto.UserDto
import com.project.irequest.data.remote.dto.WorkflowDto
import com.project.irequest.data.remote.dto.WorkflowStepDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body loginDto: LoginDto): Response<ApiResponse<AuthResponseDto>>
    
    @POST("auth/register")
    suspend fun register(@Body registerDto: RegisterDto): Response<ApiResponse<AuthResponseDto>>
    
    @POST("auth/refresh")
    suspend fun refreshToken(@Body refreshTokenDto: RefreshTokenDto): Response<ApiResponse<AuthResponseDto>>
    
    @POST("auth/logout")
    suspend fun logout(): Response<ApiResponse<Boolean>>
    
    @GET("auth/me")
    suspend fun getCurrentUser(): Response<ApiResponse<UserDto>>
    
    @PUT("auth/profile")
    suspend fun updateProfile(@Body updateUserDto: UpdateUserDto): Response<ApiResponse<UserDto>>
    
    @PUT("auth/change-password")
    suspend fun changePassword(@Body changePasswordDto: ChangePasswordDto): Response<ApiResponse<Boolean>>
}

interface RequestApi {
    @GET("requests")
    suspend fun getRequests(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 10,
        @Query("search") search: String? = null,
        @Query("statusId") statusId: Int? = null,
        @Query("priorityId") priorityId: Int? = null,
        @Query("departmentId") departmentId: Int? = null,
        @Query("assignedUserId") assignedUserId: String? = null,
        @Query("createdBy") createdBy: String? = null,
        @Query("fromDate") fromDate: String? = null,
        @Query("toDate") toDate: String? = null,
        @Query("sortBy") sortBy: String = "CreatedAt",
        @Query("sortOrder") sortOrder: String = "desc",
        @Query("onlyStarred") onlyStarred: Boolean? = null,
        @Query("onlyMyRequests") onlyMyRequests: Boolean? = null
    ): Response<ApiResponse<PagedResponse<RequestDto>>>
    
    @GET("requests/{id}")
    suspend fun getRequestById(@Path("id") requestId: Int): Response<ApiResponse<RequestDto>>
    
    @POST("requests")
    suspend fun createRequest(@Body createRequestDto: CreateRequestDto): Response<ApiResponse<RequestDto>>
    
    @PUT("requests/{id}")
    suspend fun updateRequest(
        @Path("id") requestId: Int,
        @Body updateRequestDto: UpdateRequestDto
    ): Response<ApiResponse<RequestDto>>
    
    @DELETE("requests/{id}")
    suspend fun deleteRequest(@Path("id") requestId: Int): Response<ApiResponse<Boolean>>
    
    @POST("requests/{id}/approve")
    suspend fun approveRequest(
        @Path("id") requestId: Int,
        @Body approveRequestDto: ApproveRequestDto
    ): Response<ApiResponse<RequestDto>>
    
    @POST("requests/{id}/star")
    suspend fun toggleStar(@Path("id") requestId: Int): Response<ApiResponse<Boolean>>
    
    @GET("requests/{id}/history")
    suspend fun getRequestHistory(@Path("id") requestId: Int): Response<ApiResponse<List<RequestHistoryDto>>>
    
    @POST("requests/{id}/comments")
    suspend fun addComment(
        @Path("id") requestId: Int,
        @Body createCommentDto: CreateCommentDto
    ): Response<ApiResponse<CommentDto>>
}

interface MasterDataApi {
    @GET("departments")
    suspend fun getDepartments(): Response<ApiResponse<List<DepartmentDto>>>
    
    @GET("departments/{id}")
    suspend fun getDepartmentById(@Path("id") departmentId: Int): Response<ApiResponse<DepartmentDto>>
    
    @GET("departments/{id}/users")
    suspend fun getDepartmentUsers(@Path("id") departmentId: Int): Response<ApiResponse<List<UserDto>>>
    
    @GET("priorities")
    suspend fun getPriorities(): Response<ApiResponse<List<PriorityDto>>>
    
    @GET("statuses")
    suspend fun getStatuses(): Response<ApiResponse<List<StatusDto>>>
    
    @GET("workflows")
    suspend fun getWorkflows(): Response<ApiResponse<List<WorkflowDto>>>
    
    @GET("workflows/{id}")
    suspend fun getWorkflowById(@Path("id") workflowId: Int): Response<ApiResponse<WorkflowDto>>
    
    @GET("workflows/{id}/steps")
    suspend fun getWorkflowSteps(@Path("id") workflowId: Int): Response<ApiResponse<List<WorkflowStepDto>>>
}

interface NotificationApi {
    @GET("notifications")
    suspend fun getNotifications(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): Response<ApiResponse<PagedResponse<NotificationDto>>>
    
    @GET("notifications/unread-count")
    suspend fun getUnreadCount(): Response<ApiResponse<Int>>
    
    @PUT("notifications/{id}/read")
    suspend fun markAsRead(@Path("id") notificationId: Int): Response<ApiResponse<Boolean>>
    
    @PUT("notifications/mark-all-read")
    suspend fun markAllAsRead(): Response<ApiResponse<Boolean>>
}

// Request History DTO (missing from previous file)
data class RequestHistoryDto(
    val requestHistoryId: Int,
    val action: String,
    val details: String?,
    val oldValue: String?,
    val newValue: String?,
    val requestId: Int,
    val userId: String,
    val user: UserDto?,
    val createdAt: String
)