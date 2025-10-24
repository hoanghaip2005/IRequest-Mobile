package com.project.irequest.presentation.ui

/**
 * UI Structure for IRequest Mobile App
 * Mapped from C# Web App Views and Areas
 * 
 * This file defines the screen structure and organization
 * based on the existing C# application functionality
 */

// =====================================
// 🔐 AUTHENTICATION SCREENS (Areas/Identity/Views/Account/)
// =====================================
object AuthScreens {
    /**
     * Login Screen - Login.cshtml
     * Features:
     * - Email/password login
     * - Remember me option
     * - External login (Google, Facebook) 
     * - Forgot password link
     * - Register link
     */
    const val LOGIN = "LoginScreen"
    
    /**
     * Register Screen - Register.cshtml  
     * Features:
     * - User registration form
     * - Email confirmation
     * - Password validation
     * - Department selection
     * - Terms acceptance
     */
    const val REGISTER = "RegisterScreen"
    
    /**
     * Forgot Password Screen - ForgotPassword.cshtml
     * Features:
     * - Email input for reset
     * - Send reset link
     * - Back to login
     */
    const val FORGOT_PASSWORD = "ForgotPasswordScreen"
    
    /**
     * External Login Screen - ExternalLoginConfirmation.cshtml
     * Features:
     * - OAuth completion
     * - Account linking
     * - Profile completion
     */
    const val EXTERNAL_LOGIN = "ExternalLoginScreen"
}

// =====================================
// 🏠 DASHBOARD & HOME SCREENS (Views/Home/ + Areas/IRequest/Views/Dashboard/)
// =====================================
object DashboardScreens {
    /**
     * Main Dashboard Screen - Index.cshtml + Dashboard views
     * Features:
     * - Statistics cards (Total, Pending, Completed, Overdue requests)
     * - Quick actions (Create Request, My Tasks)
     * - Recent requests tabs:
     *   • For You (assigned to user)
     *   • Recent (latest requests)
     *   • Assigned (user assigned others)
     *   • Starred (bookmarked)
     *   • Viewed (recently viewed)
     * - Department boards with request counts
     * - Recent activities timeline
     * - Performance metrics
     */
    const val HOME = "DashboardScreen"
    
    /**
     * Statistics Screen - UserPerformance.cshtml
     * Features:
     * - User performance charts
     * - Department statistics
     * - SLA compliance metrics
     * - Response time analytics
     * - Completion rates
     */
    const val STATISTICS = "StatisticsScreen"
    
    /**
     * Overdue Requests Screen - OverdueRequests.cshtml
     * Features:
     * - List of overdue requests
     * - Priority sorting
     * - Quick actions (Escalate, Reassign)
     * - Filters by department/assignee
     */
    const val OVERDUE_REQUESTS = "OverdueRequestsScreen"
    
    /**
     * Timeout Overview Screen - TimeoutOverview.cshtml
     * Features:
     * - SLA timeout warnings
     * - Critical requests alerts
     * - Action required items
     * - Escalation suggestions
     */
    const val TIMEOUT_OVERVIEW = "TimeoutOverviewScreen"
}

// =====================================
// 📋 REQUEST MANAGEMENT SCREENS (Areas/IRequest/Views/Request/)
// =====================================
object RequestScreens {
    /**
     * Request List Screen - Index.cshtml
     * Features:
     * - Paginated request list
     * - Search and filters:
     *   • Status filter
     *   • Priority filter  
     *   • Department filter
     *   • Date range filter
     *   • Assignee filter
     * - Sorting options
     * - Bulk actions
     * - Pull-to-refresh
     * - Infinite scroll
     */
    const val REQUEST_LIST = "RequestListScreen"
    
    /**
     * Create Request Screen - Create.cshtml
     * Features:
     * - Request form with validation
     * - Title and description inputs
     * - Priority selection
     * - Department/workflow selection
     * - File attachment upload
     * - Photo capture
     * - Draft saving
     * - Submit for approval
     */
    const val CREATE_REQUEST = "CreateRequestScreen"
    
    /**
     * Request Details Screen - Details.cshtml
     * Features:
     * - Full request information
     * - Status timeline/workflow progress
     * - Comments section with real-time updates
     * - File attachments viewer
     * - Action buttons (Approve, Reject, Edit, Star)
     * - Assignment actions
     * - History/audit trail
     * - Related requests
     */
    const val REQUEST_DETAILS = "RequestDetailsScreen"
    
    /**
     * Edit Request Screen - Edit.cshtml
     * Features:
     * - Editable request form
     * - Field-level permissions
     * - Change tracking
     * - Save draft/publish
     * - Validation errors
     */
    const val EDIT_REQUEST = "EditRequestScreen"
    
    /**
     * My Tasks Screen - MyTasks.cshtml  
     * Features:
     * - Requests assigned to current user
     * - Action required items
     * - Priority-based sorting
     * - Quick approve/reject
     * - Batch processing
     * - Due date alerts
     */
    const val MY_TASKS = "MyTasksScreen"
    
    /**
     * Completed Requests Screen - Completed.cshtml
     * Features:
     * - Archive of completed requests
     * - Search completed items
     * - Reopen functionality
     * - Export options
     * - Performance analytics
     */
    const val COMPLETED_REQUESTS = "CompletedRequestsScreen"
}

// =====================================
// 💬 COMMENTS & COMMUNICATION SCREENS (Areas/IRequest/Views/Comment/)
// =====================================
object CommentScreens {
    /**
     * Comments Thread Screen
     * Features:
     * - Real-time comment updates
     * - Rich text formatting
     * - File attachments in comments
     * - @mentions functionality
     * - Internal vs public comments
     * - Comment history
     * - Reply threads
     */
    const val COMMENTS_THREAD = "CommentsThreadScreen"
    
    /**
     * Add Comment Screen
     * Features:
     * - Comment composer
     * - Rich text editor
     * - File attachment
     * - Voice notes
     * - Photo/video capture
     * - Internal comment toggle
     */
    const val ADD_COMMENT = "AddCommentScreen"
}

// =====================================
// 🔄 WORKFLOW MANAGEMENT SCREENS (Areas/IRequest/Views/Workflow/ + WorkflowStep/)
// =====================================
object WorkflowScreens {
    /**
     * Workflow List Screen
     * Features:
     * - Available workflows
     * - Workflow templates
     * - Usage statistics
     * - Default workflow indication
     */
    const val WORKFLOW_LIST = "WorkflowListScreen"
    
    /**
     * Workflow Details Screen
     * Features:
     * - Workflow step visualization
     * - Step requirements
     * - Role permissions
     * - Approval hierarchy
     * - SLA targets per step
     */
    const val WORKFLOW_DETAILS = "WorkflowDetailsScreen"
    
    /**
     * Workflow Progress Screen
     * Features:
     * - Current request position
     * - Step completion status
     * - Next approvers
     * - Expected timeline
     * - Bottleneck identification
     */
    const val WORKFLOW_PROGRESS = "WorkflowProgressScreen"
}

// =====================================
// 👤 PROFILE & USER MANAGEMENT SCREENS (Areas/Identity/Views/Manage/)
// =====================================
object ProfileScreens {
    /**
     * Profile View Screen
     * Features:
     * - User information display
     * - Avatar/photo
     * - Contact information
     * - Department details
     * - Role information
     * - Activity summary
     */
    const val PROFILE_VIEW = "ProfileViewScreen"
    
    /**
     * Edit Profile Screen
     * Features:
     * - Editable user information
     * - Avatar upload/camera
     * - Contact details update
     * - Department change request
     * - Notification preferences
     */
    const val PROFILE_EDIT = "ProfileEditScreen"
    
    /**
     * Change Password Screen
     * Features:
     * - Current password verification
     * - New password input with rules
     * - Password strength indicator
     * - Biometric setup option
     */
    const val CHANGE_PASSWORD = "ChangePasswordScreen"
    
    /**
     * App Settings Screen
     * Features:
     * - Notification settings
     * - Theme selection (Dark/Light)
     * - Language preferences
     * - Cache management
     * - Sync settings
     * - Privacy options
     */
    const val SETTINGS = "SettingsScreen"
}

// =====================================
// 🔔 NOTIFICATION SCREENS
// =====================================
object NotificationScreens {
    /**
     * Notifications List Screen
     * Features:
     * - Push notification history
     * - Read/unread indicators
     * - Notification categories
     * - Mark as read/unread
     * - Clear all functionality
     * - Deep links to related content
     */
    const val NOTIFICATIONS_LIST = "NotificationsListScreen"
    
    /**
     * Notification Details Screen
     * Features:
     * - Full notification content
     * - Related request/comment
     * - Action buttons
     * - Share functionality
     */
    const val NOTIFICATION_DETAILS = "NotificationDetailsScreen"
}

// =====================================
// 🔍 SEARCH & FILTER SCREENS
// =====================================
object SearchScreens {
    /**
     * Global Search Screen
     * Features:
     * - Universal search across all content
     * - Search suggestions
     * - Recent searches
     * - Search filters
     * - Result categorization
     * - Voice search
     */
    const val GLOBAL_SEARCH = "GlobalSearchScreen"
    
    /**
     * Advanced Search Screen
     * Features:
     * - Multiple filter criteria
     * - Date range selection
     * - Status combinations
     * - User/department filters
     * - Save search queries
     * - Export search results
     */
    const val ADVANCED_SEARCH = "AdvancedSearchScreen"
}

// =====================================
// 👨‍💼 ADMIN SCREENS (Areas/Identity/Views/User/ + Role/)
// =====================================
object AdminScreens {
    /**
     * User Management Screen
     * Features:
     * - User list with search
     * - User status management
     * - Role assignment
     * - Department assignment
     * - Bulk user operations
     */
    const val USER_MANAGEMENT = "UserManagementScreen"
    
    /**
     * Role Management Screen  
     * Features:
     * - Role list and permissions
     * - Role creation/editing
     * - Permission matrix
     * - Role assignment overview
     */
    const val ROLE_MANAGEMENT = "RoleManagementScreen"
    
    /**
     * Department Management Screen
     * Features:
     * - Department hierarchy
     * - Manager assignments
     * - Department statistics
     * - Workflow assignments
     */
    const val DEPARTMENT_MANAGEMENT = "DepartmentManagementScreen"
}

// =====================================
// 📊 REPORTS & ANALYTICS SCREENS (Areas/IRequest/Views/Dashboard/ + Sla/)
// =====================================
object ReportScreens {
    /**
     * Reports Dashboard Screen
     * Features:
     * - Report categories
     * - Quick insights
     * - Trending metrics
     * - Export options
     */
    const val REPORTS_DASHBOARD = "ReportsDashboardScreen"
    
    /**
     * User Performance Report Screen - UserPerformance.cshtml
     * Features:
     * - Individual performance metrics
     * - Completion rates
     * - Response times
     * - Comparative analysis
     * - Performance trends
     */
    const val USER_PERFORMANCE = "UserPerformanceScreen"
    
    /**
     * Department Statistics Screen
     * Features:
     * - Department-wise analytics
     * - Request volume trends
     * - Resource utilization
     * - Performance comparisons
     */
    const val DEPARTMENT_STATS = "DepartmentStatsScreen"
    
    /**
     * SLA Reports Screen (Areas/IRequest/Views/Sla/)
     * Features:
     * - SLA compliance tracking
     * - Breach analysis
     * - Time-to-resolution metrics
     * - Escalation patterns
     */
    const val SLA_REPORTS = "SlaReportsScreen"
}

// =====================================
// 🤖 AI FEATURES SCREENS (Areas/IRequest/Views/AI/)
// =====================================
object AIScreens {
    /**
     * AI Assistant Screen
     * Features:
     * - Chatbot interface
     * - Smart suggestions
     * - Auto-categorization
     * - Priority recommendations
     * - Solution suggestions
     */
    const val AI_ASSISTANT = "AIAssistantScreen"
    
    /**
     * Smart Suggestions Screen
     * Features:
     * - ML-powered recommendations
     * - Similar request suggestions
     * - Assignment recommendations
     * - Process optimization tips
     */
    const val SMART_SUGGESTIONS = "SmartSuggestionsScreen"
}

// =====================================
// 💬 CHAT SCREENS (Views/Chat/)
// =====================================
object ChatScreens {
    /**
     * Chat List Screen
     * Features:
     * - Active chat rooms
     * - Recent conversations  
     * - Unread message indicators
     * - Search conversations
     */
    const val CHAT_LIST = "ChatListScreen"
    
    /**
     * Chat Room Screen
     * Features:
     * - Real-time messaging
     * - File sharing
     * - Message history
     * - Typing indicators
     * - Message reactions
     * - Voice messages
     */
    const val CHAT_ROOM = "ChatRoomScreen"
}

// =====================================
// 🛠️ UTILITY & ERROR SCREENS (Views/Error/)
// =====================================
object UtilityScreens {
    /**
     * Error Screen - Views/Error/
     * Features:
     * - Error message display
     * - Retry functionality
     * - Support contact
     * - Error reporting
     */
    const val ERROR = "ErrorScreen"
    
    /**
     * Access Denied Screen - AccessDenied.cshtml
     * Features:
     * - Permission explanation
     * - Contact admin option
     * - Alternative actions
     */
    const val ACCESS_DENIED = "AccessDeniedScreen"
    
    /**
     * Offline Mode Screen
     * Features:
     * - Offline indicator
     * - Cached content access
     * - Sync status
     * - Retry connection
     */
    const val OFFLINE_MODE = "OfflineModeScreen"
    
    /**
     * Loading Screen
     * Features:
     * - App initialization
     * - Data sync progress
     * - Splash screen
     */
    const val LOADING = "LoadingScreen"
}