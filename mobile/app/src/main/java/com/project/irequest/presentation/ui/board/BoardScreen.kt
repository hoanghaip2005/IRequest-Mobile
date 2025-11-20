package com.project.irequest.presentation.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.project.irequest.presentation.theme.*

/**
 * Kanban Board Screen
 * - Visual workflow management
 * - Drag & drop cards (future enhancement)
 * - Status columns: Backlog, To Do, In Progress, Review, Done
 * - Quick filters
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoardScreen(
    onBack: () -> Unit = {},
    onRequestClick: (String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Kanban Board",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Visualize your workflow",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF667085)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Filter */ }) {
                        Icon(Icons.Default.MoreVert, "Options")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Create new card */ },
                containerColor = PrimaryBlue
            ) {
                Icon(Icons.Default.Add, "Add Card", tint = Color.White)
            }
        }
    ) { padding ->
        LazyRow(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAFAFA))
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Backlog Column
            item {
                BoardColumn(
                    title = "Backlog",
                    count = 12,
                    color = Color(0xFF6B7280),
                    cards = listOf(
                        CardData("REQ-1001", "New laptop request", "High", "Alex"),
                        CardData("REQ-1002", "Software license", "Medium", "John"),
                        CardData("REQ-1003", "Meeting room booking", "Low", "Sarah")
                    ),
                    onCardClick = onRequestClick
                )
            }
            
            // To Do Column
            item {
                BoardColumn(
                    title = "To Do",
                    count = 8,
                    color = PrimaryBlue,
                    cards = listOf(
                        CardData("REQ-0998", "Access card replacement", "High", "Mike"),
                        CardData("REQ-0997", "Printer maintenance", "Medium", "Lisa")
                    ),
                    onCardClick = onRequestClick
                )
            }
            
            // In Progress Column
            item {
                BoardColumn(
                    title = "In Progress",
                    count = 15,
                    color = CustomOrange,
                    cards = listOf(
                        CardData("REQ-0995", "Network troubleshooting", "High", "Tom"),
                        CardData("REQ-0994", "Email setup", "Medium", "Emma"),
                        CardData("REQ-0993", "Software installation", "Low", "David")
                    ),
                    onCardClick = onRequestClick
                )
            }
            
            // Review Column
            item {
                BoardColumn(
                    title = "Review",
                    count = 6,
                    color = Color(0xFF8B5CF6),
                    cards = listOf(
                        CardData("REQ-0990", "VPN access approval", "Medium", "Anna"),
                        CardData("REQ-0989", "Budget approval", "High", "Chris")
                    ),
                    onCardClick = onRequestClick
                )
            }
            
            // Done Column
            item {
                BoardColumn(
                    title = "Done",
                    count = 45,
                    color = CustomGreen,
                    cards = listOf(
                        CardData("REQ-0987", "Password reset", "Low", "Jane"),
                        CardData("REQ-0986", "File sharing setup", "Medium", "Bob"),
                        CardData("REQ-0985", "Monitor replacement", "Low", "Kate")
                    ),
                    onCardClick = onRequestClick
                )
            }
        }
    }
}

data class CardData(
    val id: String,
    val title: String,
    val priority: String,
    val assignee: String
)

@Composable
private fun BoardColumn(
    title: String,
    count: Int,
    color: Color,
    cards: List<CardData>,
    onCardClick: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .width(300.dp)
            .fillMaxHeight(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Column Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(12.dp),
                        shape = CircleShape,
                        color = color
                    ) {}
                    
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color(0xFF101828)
                    )
                }
                
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = color.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = count.toString(),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = color
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Cards
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(cards) { card ->
                    BoardCard(
                        card = card,
                        onClick = { onCardClick(card.id) }
                    )
                }
                
                // Add Card Button
                item {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* Add card */ },
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFF9FAFB),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            Color(0xFFEAECF0)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add card",
                                tint = Color(0xFF667085),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Add card",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF667085)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BoardCard(
    card: CardData,
    onClick: () -> Unit
) {
    val priorityColor = when (card.priority) {
        "High" -> CustomRed
        "Medium" -> CustomOrange
        "Low" -> CustomGreen
        else -> Color(0xFF667085)
    }
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        color = Color.White,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEAECF0)),
        shadowElevation = 0.5.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Priority Badge & ID
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = priorityColor.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = card.priority,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = priorityColor
                    )
                }
                
                Text(
                    text = card.id,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = PrimaryBlue
                )
            }
            
            // Title
            Text(
                text = card.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = Color(0xFF101828),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            HorizontalDivider(color = Color(0xFFEAECF0))
            
            // Assignee
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = PrimaryBlue.copy(alpha = 0.2f),
                    modifier = Modifier.size(24.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = card.assignee.first().toString(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = PrimaryBlue
                        )
                    }
                }
                
                Text(
                    text = card.assignee,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF667085)
                )
            }
        }
    }
}
