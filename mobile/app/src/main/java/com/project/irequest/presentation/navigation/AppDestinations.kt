
package com.project.irequest.presentation.navigation

/**
 * Navigation Destinations for IRequest Mobile App
 * 
 * This file defines all navigation destinations mapped from the C# Web Application structure
 * Routes correspond to Controllers and Actions in ASP.NET MVC
 */

object AppDestinations {    
    
    // =====================================
    // 🔐 AUTHENTICATION DESTINATIONS
    // =====================================
    
    object Auth {
        const val SPLASH = "splash"
        const val ONBOARDING = "onboarding" 
        const val LOGIN = "auth/login"                    // → HomeController.Login()
        const val REGISTER = "auth/register"              // → AccountController.Register()
        const val FORGOT_PASSWORD = "auth/forgot"         // → AccountController.ForgotPassword()
        const val RESET_PASSWORD = "auth/reset/{token}"   // → AccountController.ResetPassword()
        const val EXTERNAL_LOGIN = "auth/external"        // → AccountController.ExternalLogin()
        const val BIOMETRIC_SETUP = "auth/biometric"     // New mobile feature
        
        fun resetPasswordRoute(token: String) = "auth/reset/$token"
    }
    
    // =====================================  
    // 🏠 MAIN NAVIGATION (Bottom Tabs)
    // =====================================
    
    object Main {
        const val HOME = "main/home"           // Bottom navigation root
        const val MY_TASKS = "main/my-tasks"   // Bottom navigation root (Work Center)
        const val ALERTS = "main/alerts"       // Bottom navigation root (Notifications + Chat + SLA)
        const val PROFILE = "main/profile"     // Bottom navigation root
        
        // Quick Navigation Routes
        const val REPORTS = "main/reports"     // Analytics dashboard
        const val BOARD = "main/board"         // Kanban board
        const val ROADMAP = "main/roadmap"     // Project timeline & milestones
        const val CALENDAR = "main/calendar"   // Request schedule & events
        
        // Legacy routes (kept for backward compatibility)
        @Deprecated("Use MY_TASKS instead")
        const val REQUESTS = "main/requests"   
        @Deprecated("Use ALERTS instead")
        const val NOTIFICATIONS = "main/notifications"
        @Deprecated("Search integrated into other screens")
        const val SEARCH = "main/search"
    }
    
    // =====================================
    // 🏠 HOME/DASHBOARD DESTINATIONS  
    // =====================================
    
    object Home {
        const val DASHBOARD = "home/dashboard"               // → HomeController.Index()
        const val STATISTICS = "home/statistics"             // → DashboardController.UserPerformance()
        const val OVERDUE_REQUESTS = "home/overdue"          // → DashboardController.OverdueRequests()
        const val TIMEOUT_OVERVIEW = "home/timeout"          // → DashboardController.TimeoutOverview()  
        const val DEPARTMENT_PERFORMANCE = "home/dept-perf"  // → DashboardController.UserDetailedPerformance()
        const val SLA_OVERVIEW = "home/sla"                 // → SlaController.Index()
    }
    
    // =====================================
    // 📋 REQUESTS DESTINATIONS
    // =====================================
    
    object Requests {
        const val LIST = "requests/list"                     // → LaptopRequestController.Index()
        const val MY_TASKS = "requests/my-tasks"             // → LaptopRequestController.MyTasks()
        const val COMPLETED = "requests/completed"           // → LaptopRequestController.Completed()
        const val CREATE = "requests/create"                 // → LaptopRequestController.Create()
        const val DETAILS = "requests/{requestId}/details"   // → LaptopRequestController.Details(id)
        const val EDIT = "requests/{requestId}/edit"         // → LaptopRequestController.Edit(id)
        const val DELETE = "requests/{requestId}/delete"     // → LaptopRequestController.Delete(id)
        
        // Request Actions
        const val APPROVE = "requests/{requestId}/approve"   // → LaptopRequestController.Approve(id)
        const val REJECT = "requests/{requestId}/reject"     // → LaptopRequestController.Reject(id)
        const val FORWARD = "requests/{requestId}/forward"   // → LaptopRequestController.Forward(id)
        const val RETURN = "requests/{requestId}/return"     // → LaptopRequestController.Return(id)
        
        // Helper functions for parameterized routes
        fun detailsRoute(requestId: String) = "requests/$requestId/details"
        fun editRoute(requestId: String) = "requests/$requestId/edit"  
        fun deleteRoute(requestId: String) = "requests/$requestId/delete"
        fun approveRoute(requestId: String) = "requests/$requestId/approve"
        fun rejectRoute(requestId: String) = "requests/$requestId/reject"
        fun forwardRoute(requestId: String) = "requests/$requestId/forward"
        fun returnRoute(requestId: String) = "requests/$requestId/return"
    }
    
    // =====================================
    // 💬 COMMENTS DESTINATIONS  
    // =====================================
    
    object Comments {
        const val LIST = "requests/{requestId}/comments"           // → CommentController.Index()
        const val CREATE = "requests/{requestId}/comments/create"  // → CommentController.Create()
        const val EDIT = "comments/{commentId}/edit"              // → CommentController.Edit(id)
        const val DELETE = "comments/{commentId}/delete"          // → CommentController.Delete(id)
        const val THREAD = "comments/{commentId}/thread"          // Comment thread view
        
        fun listRoute(requestId: String) = "requests/$requestId/comments"
        fun createRoute(requestId: String) = "requests/$requestId/comments/create"
        fun editRoute(commentId: String) = "comments/$commentId/edit"
        fun deleteRoute(commentId: String) = "comments/$commentId/delete"
        fun threadRoute(commentId: String) = "comments/$commentId/thread"
    }
    
    // =====================================
    // 🔄 WORKFLOW DESTINATIONS
    // =====================================
    
    object Workflow {
        const val LIST = "workflows"                              // → WorkflowController.Index()
        const val DETAILS = "workflows/{workflowId}/details"      // → WorkflowController.Details(id)
        const val STEPS = "workflows/{workflowId}/steps"          // → WorkflowStepController.Index()
        const val STEP_DETAILS = "workflows/{workflowId}/steps/{stepId}" // → WorkflowStepController.Details()
        
        fun detailsRoute(workflowId: String) = "workflows/$workflowId/details"
        fun stepsRoute(workflowId: String) = "workflows/$workflowId/steps"
        fun stepDetailsRoute(workflowId: String, stepId: String) = "workflows/$workflowId/steps/$stepId"
    }
    
    // =====================================
    // 🔔 NOTIFICATIONS DESTINATIONS
    // =====================================
    
    object Notifications {
        const val LIST = "notifications"                          // New mobile feature
        const val DETAILS = "notifications/{notificationId}"      // New mobile feature  
        const val SETTINGS = "notifications/settings"            // Notification preferences
        const val MARK_READ = "notifications/{notificationId}/read"
        const val MARK_ALL_READ = "notifications/mark-all-read"
        
        fun detailsRoute(notificationId: String) = "notifications/$notificationId"
        fun markReadRoute(notificationId: String) = "notifications/$notificationId/read"
    }
    
    // =====================================
    // 🔍 SEARCH DESTINATIONS  
    // =====================================
    
    object Search {
        const val GLOBAL = "search"                               // Global search
        const val ADVANCED = "search/advanced"                   // Advanced search filters
        const val RESULTS = "search/results?q={query}"           // Search results
        const val SAVED_SEARCHES = "search/saved"               // Saved search queries
        const val SEARCH_HISTORY = "search/history"             // Recent searches
        
        fun resultsRoute(query: String) = "search/results?q=$query"
    }
    
    // =====================================
    // 👤 PROFILE DESTINATIONS
    // =====================================
    
    object Profile {
        const val VIEW = "profile"                               // → ProfileController.Index()
        const val EDIT = "profile/edit"                         // → ProfileController.Edit()  
        const val CHANGE_PASSWORD = "profile/change-password"   // → ProfileController.ChangePassword()
        const val SETTINGS = "profile/settings"                // App settings
        const val SECURITY = "profile/security"                // Security settings
        const val PRIVACY = "profile/privacy"                  // Privacy settings
        const val ABOUT = "profile/about"                      // About app
        const val HELP = "profile/help"                        // Help & support
        const val LOGOUT = "profile/logout"                    // → AccountController.Logout()
        
        // Detail Pages
        const val PERSONAL_INFO = "profile/personal-info"        // Personal information detail
        const val TERMS_OF_SERVICE = "profile/terms"            // Terms of service
        const val PRIVACY_POLICY = "profile/privacy-policy"     // Privacy policy
        const val DATA_PRIVACY = "profile/data-privacy"         // Data privacy policy
        const val SECURITY_PRIVACY = "profile/security-privacy" // Security and privacy
        const val SUPPORT_HELP = "profile/support"              // Support and help
    }
    
    // =====================================
    // 💬 CHAT DESTINATIONS
    // =====================================
    
    object Chat {
        const val ChatListScreen = "chat/rooms"                     // → ChatController.Index()
        const val ChatDetailScreen = "chat/rooms/{roomId}"                  // → ChatController.Room(id)
        const val CREATE_ROOM = "chat/create-room"             // → ChatController.CreateRoom()
        const val ROOM_SETTINGS = "chat/rooms/{roomId}/settings" // Room management
        
        fun roomRoute(roomId: String) = "chat/rooms/$roomId"
        fun roomSettingsRoute(roomId: String) = "chat/rooms/$roomId/settings"
    }
    
    // =====================================
    // 📊 REPORTS DESTINATIONS
    // =====================================
    
    object Reports {
        const val DASHBOARD = "reports/dashboard"               // Reports overview
        const val USER_PERFORMANCE = "reports/user-performance" // → DashboardController.UserPerformance()
        const val DEPARTMENT_STATS = "reports/department"      // → DashboardController.UserDetailedPerformance()
        const val SLA_REPORTS = "reports/sla"                 // → SlaController Reports
        const val EXPORT = "reports/export/{reportType}"       // Export functionality
        
        fun exportRoute(reportType: String) = "reports/export/$reportType"
    }
    
    // =====================================
    // 🤖 AI FEATURES DESTINATIONS
    // =====================================
    
    object AI {
        const val ASSISTANT = "ai/assistant"                    // AI assistant chat
        const val SUGGESTIONS = "ai/suggestions"               // Smart suggestions
        const val ANALYTICS = "ai/analytics"                   // AI-powered analytics
        const val SETTINGS = "ai/settings"                     // AI preferences
    }
    
    // =====================================
    // ⚙️ ADMIN DESTINATIONS  
    // =====================================
    
    object Admin {
        const val DASHBOARD = "admin/dashboard"                 // Admin overview
        
        // User Management
        const val USERS = "admin/users"                        // → UserController.Index()
        const val USER_DETAILS = "admin/users/{userId}"        // → UserController.Details(id)
        const val USER_EDIT = "admin/users/{userId}/edit"      // → UserController.Edit(id)
        const val USER_CREATE = "admin/users/create"          // → UserController.Create()
        
        // Role Management  
        const val ROLES = "admin/roles"                        // → RoleController.Index()
        const val ROLE_DETAILS = "admin/roles/{roleId}"        // → RoleController.Details(id)
        const val ROLE_EDIT = "admin/roles/{roleId}/edit"      // → RoleController.Edit(id)
        const val ROLE_CREATE = "admin/roles/create"          // → RoleController.Create()
        
        // Department Management
        const val DEPARTMENTS = "admin/departments"            // → DepartmentController.Index()
        const val DEPT_DETAILS = "admin/departments/{deptId}"  // → DepartmentController.Details(id)
        const val DEPT_EDIT = "admin/departments/{deptId}/edit" // → DepartmentController.Edit(id)
        const val DEPT_CREATE = "admin/departments/create"    // → DepartmentController.Create()
        
        // Master Data Management
        const val PRIORITIES = "admin/priorities"              // → PriorityController.Index()
        const val STATUSES = "admin/statuses"                 // → StatusController.Index()
        const val WORKFLOWS = "admin/workflows"               // → WorkflowController.Index()
        
        // System Settings
        const val SETTINGS = "admin/settings"                 // System configuration
        const val LOGS = "admin/logs"                         // System logs
        const val MAINTENANCE = "admin/maintenance"           // System maintenance
        
        // Helper functions
        fun userDetailsRoute(userId: String) = "admin/users/$userId"
        fun userEditRoute(userId: String) = "admin/users/$userId/edit"
        fun roleDetailsRoute(roleId: String) = "admin/roles/$roleId"
        fun roleEditRoute(roleId: String) = "admin/roles/$roleId/edit"
        fun deptDetailsRoute(deptId: String) = "admin/departments/$deptId"
        fun deptEditRoute(deptId: String) = "admin/departments/$deptId/edit"
    }
    
    // =====================================
    // ❌ ERROR DESTINATIONS
    // =====================================
    
    object Error {
        const val GENERAL = "error"                            // → ErrorController.Index()
        const val NOT_FOUND = "error/404"                     // → ErrorController.NotFound()
        const val ACCESS_DENIED = "error/403"                 // → ErrorController.AccessDenied()
        const val SERVER_ERROR = "error/500"                  // → ErrorController.ServerError()
        const val OFFLINE = "error/offline"                   // Network offline
        const val MAINTENANCE = "error/maintenance"           // App maintenance
    }
    
    // =====================================
    // 🔧 UTILITY DESTINATIONS
    // =====================================
    
    object Utility {
        const val PRIVACY_POLICY = "utility/privacy"          // Privacy policy
        const val TERMS_OF_SERVICE = "utility/terms"         // Terms of service
        const val CONTACT_SUPPORT = "utility/support"        // Contact support
        const val APP_INFO = "utility/app-info"              // App information
        const val DEVELOPER_OPTIONS = "utility/dev-options"   // Debug options (dev builds)
    }
}

/**
 * Navigation Graph Structure
 * 
 * This represents the hierarchical navigation structure:
 * 
 * Root
 * ├── AuthGraph (Login, Register, etc.)
 * ├── MainGraph (Bottom Navigation)
 * │   ├── HomeGraph (Dashboard, Statistics)  
 * │   ├── RequestsGraph (List, Create, Details)
 * │   ├── NotificationsGraph
 * │   ├── SearchGraph
 * │   └── ProfileGraph
 * ├── AdminGraph (Admin features)
 * ├── ChatGraph (Real-time chat)
 * ├── ReportsGraph (Analytics & reports)
 * ├── AIGraph (AI features)
 * └── ErrorGraph (Error handling)
 */

object NavigationGraphs {
    const val ROOT = "root_graph"
    const val AUTH = "auth_graph" 
    const val MAIN = "main_graph"
    const val HOME = "home_graph"
    const val REQUESTS = "requests_graph"
    const val NOTIFICATIONS = "notifications_graph"
    const val SEARCH = "search_graph"
    const val PROFILE = "profile_graph"
    const val ADMIN = "admin_graph"
    const val CHAT = "chat_graph"
    const val REPORTS = "reports_graph"
    const val AI = "ai_graph"
    const val WORKFLOW = "workflow_graph"
    const val ERROR = "error_graph"
    const val UTILITY = "utility_graph"
}
