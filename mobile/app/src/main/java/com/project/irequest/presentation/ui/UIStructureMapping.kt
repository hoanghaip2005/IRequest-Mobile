package com.project.irequest.presentation.ui

/**
 * Screen Organization for IRequest Mobile App
 * 
 * This document outlines the complete UI structure mapped from the C# Web Application
 * Each screen corresponds to Views and Areas in the original ASP.NET MVC application
 */

// =====================================
// 📱 MOBILE APP NAVIGATION STRUCTURE
// =====================================

/**
 * Main Navigation Flow:
 * 
 * 1. SPLASH & ONBOARDING
 *    ├── SplashScreen
 *    ├── OnboardingScreen (for new users)
 *    └── BiometricSetupScreen
 * 
 * 2. AUTHENTICATION FLOW (Areas/Identity/Views/Account/)
 *    ├── LoginScreen ──────────────── Login.cshtml
 *    ├── RegisterScreen ────────────── Register.cshtml  
 *    ├── ForgotPasswordScreen ──────── ForgotPassword.cshtml
 *    ├── ResetPasswordScreen ───────── ResetPassword.cshtml
 *    └── ExternalLoginScreen ───────── ExternalLoginConfirmation.cshtml
 * 
 * 3. MAIN APP (Bottom Navigation)
 *    ├── 🏠 HOME TAB
 *    │   ├── DashboardScreen ──────── Views/Home/Index.cshtml + Dashboard views
 *    │   ├── StatisticsScreen ─────── UserPerformance.cshtml
 *    │   ├── OverdueRequestsScreen ── OverdueRequests.cshtml
 *    │   └── TimeoutOverviewScreen ── TimeoutOverview.cshtml
 *    │
 *    ├── 📋 REQUESTS TAB  
 *    │   ├── RequestListScreen ────── Areas/IRequest/Views/Request/Index.cshtml
 *    │   ├── MyTasksScreen ────────── MyTasks.cshtml
 *    │   ├── CompletedRequestsScreen ─ Completed.cshtml
 *    │   ├── CreateRequestScreen ──── Create.cshtml
 *    │   ├── RequestDetailsScreen ─── Details.cshtml
 *    │   └── EditRequestScreen ────── Edit.cshtml
 *    │
 *    ├── 🔔 NOTIFICATIONS TAB
 *    │   ├── NotificationsListScreen
 *    │   └── NotificationDetailsScreen
 *    │
 *    ├── 🔍 SEARCH TAB
 *    │   ├── GlobalSearchScreen
 *    │   └── AdvancedSearchScreen
 *    │
 *    └── 👤 PROFILE TAB
 *        ├── ProfileViewScreen ────── Areas/Identity/Views/Manage/
 *        ├── ProfileEditScreen
 *        ├── ChangePasswordScreen
 *        ├── SettingsScreen
 *        └── (Admin Screens if applicable)
 * 
 * 4. ADDITIONAL SCREENS (Modal/Full Screen)
 *    ├── CommentsThreadScreen ──────── Areas/IRequest/Views/Comment/
 *    ├── WorkflowDetailsScreen ─────── Areas/IRequest/Views/Workflow/
 *    ├── ChatRoomScreen ────────────── Views/Chat/
 *    ├── ReportsScreen ─────────────── Dashboard reports
 *    ├── AIAssistantScreen ─────────── Areas/IRequest/Views/AI/
 *    └── ErrorScreen ───────────────── Views/Error/
 */

// =====================================
// 📊 SCREEN-TO-C#-VIEW MAPPING
// =====================================

object ViewMapping {
    
    // Authentication Mapping
    val authMapping = mapOf(
        "LoginScreen" to "Areas/Identity/Views/Account/Login.cshtml",
        "RegisterScreen" to "Areas/Identity/Views/Account/Register.cshtml",
        "ForgotPasswordScreen" to "Areas/Identity/Views/Account/ForgotPassword.cshtml",
        "ResetPasswordScreen" to "Areas/Identity/Views/Account/ResetPassword.cshtml",
        "ExternalLoginScreen" to "Areas/Identity/Views/Account/ExternalLoginConfirmation.cshtml"
    )
    
    // Dashboard Mapping  
    val dashboardMapping = mapOf(
        "DashboardScreen" to "Views/Home/Index.cshtml + Areas/IRequest/Views/Dashboard/",
        "StatisticsScreen" to "Areas/IRequest/Views/Dashboard/UserPerformance.cshtml",
        "OverdueRequestsScreen" to "Areas/IRequest/Views/Dashboard/OverdueRequests.cshtml",
        "TimeoutOverviewScreen" to "Areas/IRequest/Views/Dashboard/TimeoutOverview.cshtml"
    )
    
    // Request Management Mapping
    val requestMapping = mapOf(
        "RequestListScreen" to "Areas/IRequest/Views/Request/Index.cshtml",
        "CreateRequestScreen" to "Areas/IRequest/Views/Request/Create.cshtml", 
        "RequestDetailsScreen" to "Areas/IRequest/Views/Request/Details.cshtml",
        "EditRequestScreen" to "Areas/IRequest/Views/Request/Edit.cshtml",
        "MyTasksScreen" to "Areas/IRequest/Views/Request/MyTasks.cshtml",
        "CompletedRequestsScreen" to "Areas/IRequest/Views/Request/Completed.cshtml"
    )
    
    // Comments Mapping
    val commentMapping = mapOf(
        "CommentsThreadScreen" to "Areas/IRequest/Views/Comment/",
        "AddCommentScreen" to "Areas/IRequest/Views/Request/_CommentListPartial.cshtml"
    )
    
    // Workflow Mapping
    val workflowMapping = mapOf(
        "WorkflowListScreen" to "Areas/IRequest/Views/Workflow/Index.cshtml",
        "WorkflowDetailsScreen" to "Areas/IRequest/Views/Workflow/Details.cshtml",
        "WorkflowStepsScreen" to "Areas/IRequest/Views/WorkflowStep/"
    )
    
    // Profile & User Management Mapping
    val profileMapping = mapOf(
        "ProfileViewScreen" to "Areas/Identity/Views/Manage/Index.cshtml",
        "ProfileEditScreen" to "Areas/Identity/Views/Manage/Edit.cshtml",
        "ChangePasswordScreen" to "Areas/Identity/Views/Manage/ChangePassword.cshtml"
    )
    
    // Admin Mapping
    val adminMapping = mapOf(
        "UserManagementScreen" to "Areas/Identity/Views/User/",
        "RoleManagementScreen" to "Areas/Identity/Views/Role/",
        "DepartmentManagementScreen" to "Areas/IRequest/Views/Department/"
    )
    
    // Master Data Mapping
    val masterDataMapping = mapOf(
        "PrioritiesScreen" to "Areas/IRequest/Views/Priority/",
        "StatusesScreen" to "Areas/IRequest/Views/Status/",
        "DepartmentsScreen" to "Areas/IRequest/Views/Department/"
    )
    
    // Reports Mapping
    val reportsMapping = mapOf(
        "UserPerformanceScreen" to "Areas/IRequest/Views/Dashboard/UserPerformance.cshtml",
        "DepartmentStatsScreen" to "Areas/IRequest/Views/Dashboard/UserDetailedPerformance.cshtml",
        "SlaReportsScreen" to "Areas/IRequest/Views/Sla/"
    )
    
    // AI Features Mapping
    val aiMapping = mapOf(
        "AIAssistantScreen" to "Areas/IRequest/Views/AI/",
        "SmartSuggestionsScreen" to "Areas/IRequest/Views/AI/"
    )
    
    // Chat Mapping
    val chatMapping = mapOf(
        "ChatListScreen" to "Views/Chat/",
        "ChatRoomScreen" to "Views/Chat/ + SignalR Hub"
    )
    
    // Error & Utility Mapping
    val utilityMapping = mapOf(
        "ErrorScreen" to "Views/Error/",
        "AccessDeniedScreen" to "Areas/Identity/Views/Account/AccessDenied.cshtml"
    )
}

// =====================================
// 🎨 UI COMPONENT ORGANIZATION
// =====================================

object ComponentStructure {
    
    /**
     * Common UI Components (Reusable across screens)
     */
    val commonComponents = listOf(
        // Layout Components
        "TopAppBar",           // Navigation bar with title, actions
        "BottomNavigation",    // Main tab navigation
        "DrawerMenu",          // Side navigation drawer
        "FloatingActionButton", // FAB for primary actions
        
        // List Components  
        "RequestListItem",     // Individual request in list
        "CommentListItem",     // Comment item display
        "NotificationItem",    // Notification list item
        "UserListItem",        // User in lists
        
        // Input Components
        "IRequestTextField",   // Styled text input
        "IRequestDropdown",    // Dropdown/spinner
        "IRequestDatePicker",  // Date selection
        "IRequestFilePicker",  // File attachment picker
        "IRequestImagePicker", // Image/camera picker
        
        // Display Components
        "StatusChip",          // Status indicator chip
        "PriorityBadge",       // Priority level badge
        "UserAvatar",          // User profile image
        "AttachmentPreview",   // File attachment preview
        "WorkflowStepper",     // Progress indicator
        
        // Action Components
        "RequestActionButtons", // Approve/Reject/Edit actions
        "ShareButton",         // Share functionality
        "StarButton",          // Bookmark/favorite
        "CommentActionBar",    // Comment actions
        
        // Dialog Components
        "ConfirmationDialog",  // Yes/No confirmations
        "LoadingDialog",       // Progress indicator
        "ErrorDialog",         // Error message display
        "InfoDialog",          // Information display
        
        // Empty State Components
        "EmptyRequestsList",   // No requests found
        "EmptyNotifications",  // No notifications
        "EmptySearch",         // No search results
        "OfflineIndicator"     // Network offline state
    )
    
    /**
     * Screen-Specific Components
     */
    val screenSpecificComponents = mapOf(
        "Dashboard" to listOf(
            "StatisticsCards",     // Overview metrics
            "QuickActions",        // Primary action buttons
            "RecentRequestsTabs",  // Tab layout for request categories
            "DepartmentBoards",    // Department overview cards
            "ActivityTimeline"     // Recent activities list
        ),
        
        "RequestDetails" to listOf(
            "RequestHeader",       // Title, status, priority
            "RequestContent",      // Description, attachments
            "WorkflowProgress",    // Current workflow step
            "CommentsSection",     // Comments thread
            "ActionToolbar"        // Bottom action buttons
        ),
        
        "CreateRequest" to listOf(
            "RequestForm",         // Main input form
            "AttachmentSection",   // File/image attachments
            "PrioritySelector",    // Priority level picker
            "WorkflowSelector",    // Workflow template picker
            "ValidationSummary"    // Form validation errors
        ),
        
        "Profile" to listOf(
            "ProfileHeader",       // User info display
            "ProfileImage",        // Avatar with edit option
            "ContactInfo",         // Contact details
            "DepartmentInfo",      // Department and role
            "SettingsSection"      // App preferences
        ),
        
        "Search" to listOf(
            "SearchBar",           // Search input with suggestions
            "FilterPanel",         // Advanced filter options
            "SearchResults",       // Results list with categories
            "RecentSearches",      // Search history
            "SavedSearches"        // Bookmarked searches
        ),
        
        "Notifications" to listOf(
            "NotificationFilter",  // Filter by type/status
            "NotificationGroup",   // Grouped by date/type
            "NotificationActions", // Mark read/clear actions
            "DeepLinkHandler"      // Navigate to related content
        )
    )
}

// =====================================
// 🔄 STATE MANAGEMENT STRUCTURE  
// =====================================

object StateStructure {
    
    /**
     * Screen State Classes (corresponding to C# ViewModels)
     */
    val screenStates = listOf(
        // Auth States
        "LoginState",              // Login form state
        "RegisterState",           // Registration form state
        
        // Dashboard States  
        "DashboardState",          // Maps to DashboardViewModel.cs
        "StatisticsState",         // Performance metrics state
        
        // Request States
        "RequestListState",        // Request list with filters
        "RequestDetailsState",     // Single request details
        "CreateRequestState",      // Request creation form
        "MyTasksState",           // User's assigned tasks
        
        // Profile States
        "ProfileState",            // User profile data
        "SettingsState",          // App settings
        
        // Common States
        "NotificationState",       // Notifications data
        "SearchState",            // Search results and filters
        "WorkflowState",          // Workflow data
        "LoadingState",           // Loading indicators
        "ErrorState"              // Error handling
    )
    
    /**
     * ViewModels (Business Logic Layer)
     */
    val viewModels = listOf(
        "AuthViewModel",           // Authentication logic
        "DashboardViewModel",      // Dashboard data and actions
        "RequestViewModel",        // Request management
        "ProfileViewModel",        // Profile and settings
        "NotificationViewModel",   // Notifications handling
        "SearchViewModel",         // Search and filtering
        "WorkflowViewModel",       // Workflow operations
        "ChatViewModel",           // Chat functionality
        "AdminViewModel"           // Admin operations
    )
}