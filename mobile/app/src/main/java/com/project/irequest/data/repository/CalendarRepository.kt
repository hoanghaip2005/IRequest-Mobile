package com.project.irequest.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.project.irequest.di.FirebaseModule
import com.project.irequest.presentation.ui.calendar.CalendarEvent
import com.project.irequest.presentation.ui.calendar.EventType
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class CalendarRepository {
    
    private val firestore: FirebaseFirestore = FirebaseModule.getFirestore()
    
    companion object {
        private const val COLLECTION_EVENTS = "calendar_events"
        
        @Volatile
        private var instance: CalendarRepository? = null
        
        fun getInstance(): CalendarRepository {
            return instance ?: synchronized(this) {
                instance ?: CalendarRepository().also { instance = it }
            }
        }
    }

    /**
     * Get events for a specific date
     */
    suspend fun getEventsByDate(date: LocalDate): Result<List<CalendarEvent>> {
        return try {
            val startOfDay = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())
            val endOfDay = Date.from(date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant())

            val snapshot = firestore.collection(COLLECTION_EVENTS)
                .whereGreaterThanOrEqualTo("date", startOfDay)
                .whereLessThan("date", endOfDay)
                .orderBy("date", Query.Direction.ASCENDING)
                .get()
                .await()

            val events = snapshot.documents.mapNotNull { doc ->
                try {
                    CalendarEvent(
                        id = doc.id,
                        title = doc.getString("title") ?: "",
                        time = doc.getString("time") ?: "",
                        type = EventType.valueOf(doc.getString("type") ?: "MEETING"),
                        color = androidx.compose.ui.graphics.Color(
                            doc.getLong("color")?.toInt() ?: 0xFF2563EB.toInt()
                        )
                    )
                } catch (e: Exception) {
                    null
                }
            }

            Result.success(events)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get events for a month
     */
    suspend fun getEventsByMonth(year: Int, month: Int): Result<Map<LocalDate, List<CalendarEvent>>> {
        return try {
            val startOfMonth = LocalDate.of(year, month, 1)
            val endOfMonth = startOfMonth.plusMonths(1)

            val startDate = Date.from(startOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant())
            val endDate = Date.from(endOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant())

            val snapshot = firestore.collection(COLLECTION_EVENTS)
                .whereGreaterThanOrEqualTo("date", startDate)
                .whereLessThan("date", endDate)
                .orderBy("date", Query.Direction.ASCENDING)
                .get()
                .await()

            val eventsMap = mutableMapOf<LocalDate, MutableList<CalendarEvent>>()

            snapshot.documents.forEach { doc ->
                try {
                    val timestamp = doc.getTimestamp("date")
                    val eventDate = timestamp?.toDate()?.toInstant()
                        ?.atZone(ZoneId.systemDefault())?.toLocalDate()

                    if (eventDate != null) {
                        val event = CalendarEvent(
                            id = doc.id,
                            title = doc.getString("title") ?: "",
                            time = doc.getString("time") ?: "",
                            type = EventType.valueOf(doc.getString("type") ?: "MEETING"),
                            color = androidx.compose.ui.graphics.Color(
                                doc.getLong("color")?.toInt() ?: 0xFF2563EB.toInt()
                            )
                        )

                        eventsMap.getOrPut(eventDate) { mutableListOf() }.add(event)
                    }
                } catch (e: Exception) {
                    // Skip invalid events
                }
            }

            Result.success(eventsMap)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Add a new calendar event
     */
    suspend fun addEvent(
        title: String,
        date: LocalDate,
        time: String,
        type: EventType,
        color: androidx.compose.ui.graphics.Color
    ): Result<String> {
        return try {
            val eventData = hashMapOf(
                "title" to title,
                "date" to Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                "time" to time,
                "type" to type.name,
                "color" to color.value.toLong(),
                "createdAt" to Date()
            )

            val docRef = firestore.collection(COLLECTION_EVENTS)
                .add(eventData)
                .await()

            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Delete an event
     */
    suspend fun deleteEvent(eventId: String): Result<Unit> {
        return try {
            firestore.collection(COLLECTION_EVENTS)
                .document(eventId)
                .delete()
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
