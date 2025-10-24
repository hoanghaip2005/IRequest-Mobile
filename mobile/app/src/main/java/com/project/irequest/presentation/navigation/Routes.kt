package com.project.irequest.presentation.navigation

/**
 * Navigation routes for IRequest Mobile App
 * Based on C# Web App structure and functionality
 */
sealed class Routes(val route: String) {
    
    // Authentication Routes (Areas/Identity/Views/Account/)
    object Auth {
        const val ROOT = "auth"
        const val LOGIN = "auth/login"                    // Login.cshtml
        const val REGISTER = "auth/register"              // Register.cshtml
        const val FORGOT_PASSWORD = "auth/forgot"         // ForgotPassword.cshtml
        const val RESET_PASSWORD = "auth/reset"           // ResetPassword.cshtml
        const val EXTERNAL_LOGIN = "auth/external"        // ExternalLoginConfirmation.cshtml
    }
    
    // Main App Routes
    object Main {
        const val ROOT = "main"
        
        // Dashboard (Views/Home/ + Areas/IRequest/Views/Dashboard/)
        object Dashboard {
            const val HOME = "main/dashboard"              // Index.cshtml + Dashboard views
            const val STATISTICS = "main/dashboard/stats"  // UserPerformance.cshtml
            const val OVERDUE = "main/dashboard/overdue"   // OverdueRequests.cshtml
            const val TIMEOUT = "main/dashboard/timeout"   // TimeoutOverview.cshtml
        }
        
        // Request Management (Areas/IRequest/Views/Request/)
        object Request {
            const val ROOT = "main/requests"
            const val LIST = "main/requests/list"          // Index.cshtml
            const val CREATE = "main/requests/create"      // Create.cshtml
            const val DETAILS = "main/requests/{id}"       // Details.cshtml
            const val EDIT = "main/requests/{id}/edit"     // Edit.cshtml
            const val MY_TASKS = "main/requests/my-tasks"  // MyTasks.cshtml
            const val COMPLETED = "main/requests/completed" // Completed.cshtml
            
            fun details(id: Int) = "main/requests/$id"
            fun edit(id: Int) = "main/requests/$id/edit"
        }
        
        // Comments (Areas/IRequest/Views/Comment/)
        object Comment {
            const val LIST = "main/comments"               // Comment views
        }
        
        // Workflow Management (Areas/IRequest/Views/Workflow/ + WorkflowStep/)
        object Workflow {
            const val LIST = "main/workflows"              // Workflow Index
            const val DETAILS = "main/workflows/{id}"      // Workflow Details
            const val STEPS = "main/workflows/{id}/steps"  // WorkflowStep views
            
            fun details(id: Int) = "main/workflows/$id"
            fun steps(id: Int) = "main/workflows/$id/steps"
        }
        
        // Master Data Management
        object MasterData {
            // Departments (Areas/IRequest/Views/Department/)
            const val DEPARTMENTS = "main/departments"      // Department Index
            
            // Priorities (Areas/IRequest/Views/Priority/)
            const val PRIORITIES = "main/priorities"        // Priority Index
            
            // Status (Areas/IRequest/Views/Status/)
            const val STATUSES = "main/statuses"           // Status Index
        }
        
        // User Profile & Settings (Areas/Identity/Views/Manage/)
        object Profile {
            const val ROOT = "main/profile"
            const val VIEW = "main/profile/view"            // Profile view
            const val EDIT = "main/profile/edit"            // Edit profile
            const val CHANGE_PASSWORD = "main/profile/password" // Change password
            const val SETTINGS = "main/profile/settings"    // App settings
        }
        
        // Notifications
        object Notification {
            const val LIST = "main/notifications"           // Notifications list
            const val DETAILS = "main/notifications/{id}"   // Notification details
            
            fun details(id: Int) = "main/notifications/$id"
        }
        
        // Admin Features (Areas/Identity/Views/User/ + Role/)
        object Admin {
            const val ROOT = "main/admin"
            const val USERS = "main/admin/users"           // User management
            const val ROLES = "main/admin/roles"           // Role management
            const val DEPARTMENTS_ADMIN = "main/admin/departments" // Department admin
        }
        
        // Search & Filter
        object Search {
            const val GLOBAL = "main/search"               // Global search
            const val ADVANCED = "main/search/advanced"     // Advanced search
        }
        
        // Analytics & Reporting (Areas/IRequest/Views/Dashboard/)
        object Reports {
            const val ROOT = "main/reports"
            const val USER_PERFORMANCE = "main/reports/user-performance" // UserPerformance.cshtml
            const val DEPARTMENT_STATS = "main/reports/department-stats"
            const val SLA_REPORTS = "main/reports/sla"      // Areas/IRequest/Views/Sla/
        }
        
        // AI Features (Areas/IRequest/Views/AI/)
        object AI {
            const val ROOT = "main/ai"
            const val ASSISTANT = "main/ai/assistant"       // AI Assistant
            const val SUGGESTIONS = "main/ai/suggestions"   // AI Suggestions
        }
        
        // Chat Features (Views/Chat/)
        object Chat {
            const val ROOT = "main/chat"
            const val ROOM = "main/chat/{roomId}"           // Chat room
            
            fun room(roomId: String) = "main/chat/$roomId"
        }
    }
    
    // Error & Utility Routes
    object Utility {
        const val ERROR = "error"                          // Views/Error/
        const val ACCESS_DENIED = "access-denied"          // AccessDenied.cshtml
        const val NOT_FOUND = "not-found"                 // 404 page
        const val OFFLINE = "offline"                      // Offline mode
    }
    
    // Deep Link Routes
    object DeepLink {
        const val REQUEST_NOTIFICATION = "notification/request/{requestId}"
        const val COMMENT_NOTIFICATION = "notification/comment/{commentId}"
        
        fun requestNotification(requestId: Int) = "notification/request/$requestId"
        fun commentNotification(commentId: Int) = "notification/comment/$commentId"
    }
}