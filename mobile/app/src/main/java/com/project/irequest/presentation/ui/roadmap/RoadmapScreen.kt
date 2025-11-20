package com.project.irequest.presentation.ui.roadmap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.project.irequest.presentation.theme.*

/**
 * Roadmap & Timeline Screen
 * - Project milestones
 * - Feature releases
 * - Upcoming initiatives
 * - Timeline visualization
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoadmapScreen(
    onBack: () -> Unit = {}
) {
    var selectedQuarter by remember { mutableStateOf(0) }
    val quarters = listOf("Q1 2025", "Q2 2025", "Q3 2025", "Q4 2025")
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Roadmap",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Project timeline & milestones",
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAFAFA))
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Quarter Selector
            item {
                QuarterSelector(
                    selectedQuarter = selectedQuarter,
                    quarters = quarters,
                    onQuarterSelected = { selectedQuarter = it }
                )
            }
            
            // Timeline Overview
            item {
                TimelineOverview()
            }
            
            // Milestones
            val milestones = listOf(
                MilestoneData(
                    "Mobile App Launch",
                    "Q1 2025",
                    "Complete",
                    "Released mobile application for iOS and Android with core features.",
                    CustomGreen
                ),
                MilestoneData(
                    "AI-Powered Automation",
                    "Q2 2025",
                    "In Progress",
                    "Implement AI-driven request categorization and auto-assignment.",
                    CustomOrange
                ),
                MilestoneData(
                    "Advanced Analytics Dashboard",
                    "Q2 2025",
                    "In Progress",
                    "Real-time analytics with predictive insights and trend analysis.",
                    CustomOrange
                ),
                MilestoneData(
                    "Integration Hub",
                    "Q3 2025",
                    "Planned",
                    "Connect with Slack, Teams, JIRA, and other enterprise tools.",
                    PrimaryBlue
                ),
                MilestoneData(
                    "Multi-Language Support",
                    "Q3 2025",
                    "Planned",
                    "Add support for 10+ languages with localization.",
                    PrimaryBlue
                ),
                MilestoneData(
                    "Custom Workflow Builder",
                    "Q4 2025",
                    "Planned",
                    "Visual workflow designer for custom request processes.",
                    Color(0xFF6B7280)
                )
            )
            
            items(milestones) { milestone ->
                MilestoneCard(milestone)
            }
            
            // Upcoming Features
            item {
                UpcomingFeaturesSection()
            }
        }
    }
}

data class MilestoneData(
    val title: String,
    val quarter: String,
    val status: String,
    val description: String,
    val color: Color
)

@Composable
private fun QuarterSelector(
    selectedQuarter: Int,
    quarters: List<String>,
    onQuarterSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        quarters.forEachIndexed { index, quarter ->
            val isSelected = selectedQuarter == index
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                shape = RoundedCornerShape(8.dp),
                color = if (isSelected) PrimaryBlue else Color.White,
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isSelected) PrimaryBlue else Color(0xFFEAECF0)
                ),
                onClick = { onQuarterSelected(index) }
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = quarter,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                        ),
                        color = if (isSelected) Color.White else Color(0xFF667085)
                    )
                }
            }
        }
    }
}

@Composable
private fun TimelineOverview() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Progress Overview",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = Color(0xFF101828)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProgressStatItem("Completed", "3", CustomGreen)
                ProgressStatItem("In Progress", "2", CustomOrange)
                ProgressStatItem("Planned", "4", PrimaryBlue)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LinearProgressIndicator(
                progress = { 0.55f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = CustomGreen,
                trackColor = Color(0xFFEAECF0)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "55% Overall Completion",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF667085)
            )
        }
    }
}

@Composable
private fun ProgressStatItem(label: String, value: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF667085)
        )
    }
}

@Composable
private fun MilestoneCard(milestone: MilestoneData) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Timeline Indicator
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = milestone.color,
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        if (milestone.status == "Complete") {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Surface(
                                shape = CircleShape,
                                color = Color.White,
                                modifier = Modifier.size(16.dp)
                            ) {}
                        }
                    }
                }
                
                // Connector Line
                if (milestone.status != "Complete") {
                    Surface(
                        modifier = Modifier
                            .width(2.dp)
                            .height(60.dp),
                        color = Color(0xFFEAECF0)
                    ) {}
                }
            }
            
            // Content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = milestone.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color(0xFF101828)
                    )
                    
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = milestone.color.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = milestone.status,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = milestone.color
                        )
                    }
                }
                
                Text(
                    text = milestone.quarter,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = PrimaryBlue
                )
                
                Text(
                    text = milestone.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF667085)
                )
            }
        }
    }
}

@Composable
private fun UpcomingFeaturesSection() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Under Consideration",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = Color(0xFF101828)
            )
            
            listOf(
                "Voice-to-text request submission",
                "AR-powered remote support",
                "Blockchain-based audit trail",
                "IoT device integration",
                "Predictive maintenance alerts"
            ).forEach { feature ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(6.dp),
                        shape = CircleShape,
                        color = Color(0xFF667085)
                    ) {}
                    
                    Text(
                        text = feature,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF667085)
                    )
                }
            }
        }
    }
}
