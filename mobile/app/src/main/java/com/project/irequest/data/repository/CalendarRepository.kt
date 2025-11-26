package com.project.irequest.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.project.irequest.di.FirebaseModule
import com.project.irequest.presentation.ui.calendar.CalendarRequest
import com.project.irequest.presentation.ui.calendar.RequestType
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class CalendarRepository {
    
    private val firestore: FirebaseFirestore = FirebaseModule.getFirestore()
    
    companion object {
        private const val COLLECTION_REQUESTS = "requests"
        
        @Volatile
        private var instance: CalendarRepository? = null
        
        fun getInstance(): CalendarRepository {
            return instance ?: synchronized(this) {
                instance ?: CalendarRepository().also { instance = it }
            }
        }
    }

    /**
     * Get requests for a specific date
     */
    suspend fun getRequestsByDate(date: LocalDate): Result<List<CalendarRequest>> {
        return try {
            val startOfDay = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())
            val endOfDay = Date.from(date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant())

            val snapshot = firestore.collection(COLLECTION_REQUESTS)
                .whereGreaterThanOrEqualTo("createdAt", startOfDay)
                .whereLessThan("createdAt", endOfDay)
                .orderBy("createdAt", Query.Direction.ASCENDING)
                .get()
                .await()

            val requests = snapshot.documents.mapNotNull { doc ->
                try {
                    val title = doc.getString("title") ?: "Untitled Request"
                    val createdAt = doc.getTimestamp("createdAt")?.toDate()
                    val timeString = createdAt?.let {
                        val calendar = java.util.Calendar.getInstance()
                        calendar.time = it
                        String.format("%02d:%02d", calendar.get(java.util.Calendar.HOUR_OF_DAY), calendar.get(java.util.Calendar.MINUTE))
                    } ?: "--:--"
                    
                    val statusName = doc.getString("statusName") ?: "Pending"
                    val priorityName = doc.getString("priorityName") ?: "Normal"
                    
                    // Determine type based on status or priority
                    val type = when {
                        statusName.contains("Approved", ignoreCase = true) -> RequestType.TASK
                        priorityName.contains("High", ignoreCase = true) || priorityName.contains("Urgent", ignoreCase = true) -> RequestType.DEADLINE
                        else -> RequestType.TASK
                    }
                    
                    // Color based on priority
                    val color = when {
                        priorityName.contains("High", ignoreCase = true) || priorityName.contains("Urgent", ignoreCase = true) -> 
                            androidx.compose.ui.graphics.Color(0xFFDC2626) // Red
                        priorityName.contains("Low", ignoreCase = true) -> 
                            androidx.compose.ui.graphics.Color(0xFF16A34A) // Green
                        else -> 
                            androidx.compose.ui.graphics.Color(0xFF2563EB) // Blue
                    }
                    
                    CalendarRequest(
                        id = doc.id,
                        title = title,
                        time = timeString,
                        type = type,
                        color = color
                    )
                } catch (e: Exception) {
                    null
                }
            }

            Result.success(requests)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get requests for a month
     */
    suspend fun getRequestsByMonth(year: Int, month: Int): Result<Map<LocalDate, List<CalendarRequest>>> {
        return try {
            val startOfMonth = LocalDate.of(year, month, 1)
            val endOfMonth = startOfMonth.plusMonths(1)

            val startDate = Date.from(startOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant())
            val endDate = Date.from(endOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant())

            val snapshot = firestore.collection(COLLECTION_REQUESTS)
                .whereGreaterThanOrEqualTo("createdAt", startDate)
                .whereLessThan("createdAt", endDate)
                .orderBy("createdAt", Query.Direction.ASCENDING)
                .get()
                .await()

            val requestsMap = mutableMapOf<LocalDate, MutableList<CalendarRequest>>()

            snapshot.documents.forEach { doc ->
                try {
                    val timestamp = doc.getTimestamp("createdAt")
                    val requestDate = timestamp?.toDate()?.toInstant()
                        ?.atZone(ZoneId.systemDefault())?.toLocalDate()

                    if (requestDate != null) {
                        val title = doc.getString("title") ?: "Untitled Request"
                        val createdAt = timestamp.toDate()
                        val timeString = createdAt.let {
                            val calendar = java.util.Calendar.getInstance()
                            calendar.time = it
                            String.format("%02d:%02d", calendar.get(java.util.Calendar.HOUR_OF_DAY), calendar.get(java.util.Calendar.MINUTE))
                        }
                        
                        val statusName = doc.getString("statusName") ?: "Pending"
                        val priorityName = doc.getString("priorityName") ?: "Normal"
                        
                        // Determine type based on status or priority
                        val type = when {
                            statusName.contains("Approved", ignoreCase = true) -> RequestType.TASK
                            priorityName.contains("High", ignoreCase = true) || priorityName.contains("Urgent", ignoreCase = true) -> RequestType.DEADLINE
                            else -> RequestType.TASK
                        }
                        
                        // Color based on priority
                        val color = when {
                            priorityName.contains("High", ignoreCase = true) || priorityName.contains("Urgent", ignoreCase = true) -> 
                                androidx.compose.ui.graphics.Color(0xFFDC2626) // Red
                            priorityName.contains("Low", ignoreCase = true) -> 
                                androidx.compose.ui.graphics.Color(0xFF16A34A) // Green
                            else -> 
                                androidx.compose.ui.graphics.Color(0xFF2563EB) // Blue
                        }
                        
                        val request = CalendarRequest(
                            id = doc.id,
                            title = title,
                            time = timeString,
                            type = type,
                            color = color
                        )

                        requestsMap.getOrPut(requestDate) { mutableListOf() }.add(request)
                    }
                } catch (e: Exception) {
                    // Skip invalid requests
                }
            }

            Result.success(requestsMap)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Add a new calendar request
     */
    suspend fun addRequest(
        title: String,
        date: LocalDate,
        time: String,
        type: RequestType,
        color: androidx.compose.ui.graphics.Color
    ): Result<String> {
        return try {
            val requestData = hashMapOf(
                "title" to title,
                "date" to Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                "time" to time,
                "type" to type.name,
                "color" to color.value.toLong(),
                "createdAt" to Date()
            )

            val docRef = firestore.collection(COLLECTION_REQUESTS)
                .add(requestData)
                .await()

            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Delete a request
     */
    suspend fun deleteRequest(requestId: String): Result<Unit> {
        return try {
            firestore.collection(COLLECTION_REQUESTS)
                .document(requestId)
                .delete()
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
