package com.project.irequest.presentation.ui.reports

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.project.irequest.presentation.theme.*

/**
 * Reports & Analytics Screen
 * - Request statistics by period
 * - Department performance
 * - Request type breakdown
 * - Response time metrics
 * - SLA compliance
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    onBack: () -> Unit = {}
) {
    var selectedPeriod by remember { mutableStateOf(0) }
    val periods = listOf("Today", "Week", "Month", "Quarter", "Year")
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Reports & Analytics",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
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
            // Period Selector
            item {
                PeriodSelector(
                    selectedPeriod = selectedPeriod,
                    periods = periods,
                    onPeriodSelected = { selectedPeriod = it }
                )
            }
            
            // Summary Cards
            item {
                SummaryCardsSection()
            }
            
            // Request Trends Chart
            item {
                TrendsChartSection()
            }
            
            // Department Performance
            item {
                DepartmentPerformanceReport()
            }
            
            // Request Types Breakdown
            item {
                RequestTypesBreakdown()
            }
            
            // Response Time Metrics
            item {
                ResponseTimeMetrics()
            }
            
            // SLA Compliance
            item {
                SLAComplianceSection()
            }
        }
    }
}

@Composable
private fun PeriodSelector(
    selectedPeriod: Int,
    periods: List<String>,
    onPeriodSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        periods.forEachIndexed { index, period ->
            val isSelected = selectedPeriod == index
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onPeriodSelected(index) },
                shape = RoundedCornerShape(8.dp),
                color = if (isSelected) PrimaryBlue else Color.White,
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isSelected) PrimaryBlue else Color(0xFFEAECF0)
                )
            ) {
                Text(
                    text = period,
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                    ),
                    color = if (isSelected) Color.White else Color(0xFF667085),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun SummaryCardsSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Overview",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = Color(0xFF101828)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ReportCard(
                modifier = Modifier.weight(1f),
                title = "Total",
                value = "1,234",
                change = "+12.5%",
                isPositive = true,
                color = PrimaryBlue
            )
            
            ReportCard(
                modifier = Modifier.weight(1f),
                title = "Completed",
                value = "987",
                change = "+8.3%",
                isPositive = true,
                color = CustomGreen
            )
        }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ReportCard(
                modifier = Modifier.weight(1f),
                title = "Avg Time",
                value = "2.5h",
                change = "-15%",
                isPositive = true,
                color = CustomOrange
            )
            
            ReportCard(
                modifier = Modifier.weight(1f),
                title = "SLA Met",
                value = "94%",
                change = "+2.1%",
                isPositive = true,
                color = Color(0xFF8B5CF6)
            )
        }
    }
}

@Composable
private fun ReportCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    change: String,
    isPositive: Boolean,
    color: Color
) {
    Surface(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF667085)
            )
            
            Column {
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = color
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isPositive) "↑" else "↓",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isPositive) CustomGreen else CustomRed
                    )
                    Text(
                        text = change,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isPositive) CustomGreen else CustomRed
                    )
                }
            }
        }
    }
}

@Composable
private fun TrendsChartSection() {
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
                text = "Request Trends",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = Color(0xFF101828)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Simple bar chart visualization
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                listOf(45, 78, 62, 91, 58, 82, 95).forEachIndexed { index, height ->
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Surface(
                            modifier = Modifier
                                .width(24.dp)
                                .height((height * 1.5).dp),
                            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
                            color = PrimaryBlue.copy(alpha = 0.7f + (index * 0.05f))
                        ) {}
                        
                        Text(
                            text = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")[index],
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF667085)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DepartmentPerformanceReport() {
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Department Performance",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = Color(0xFF101828)
            )
            
            listOf(
                Triple("IT Department", 156, 0.92f),
                Triple("HR Department", 98, 0.87f),
                Triple("Finance", 72, 0.94f),
                Triple("Operations", 134, 0.89f)
            ).forEach { (name, count, rate) ->
                DepartmentReportItem(name, count, rate)
            }
        }
    }
}

@Composable
private fun DepartmentReportItem(name: String, requestCount: Int, completionRate: Float) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = Color(0xFF101828)
            )
            
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "$requestCount requests",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF667085)
                )
                Text(
                    text = "${(completionRate * 100).toInt()}%",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = if (completionRate >= 0.9f) CustomGreen else CustomOrange
                )
            }
        }
        
        LinearProgressIndicator(
            progress = { completionRate },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = if (completionRate >= 0.9f) CustomGreen else CustomOrange,
            trackColor = Color(0xFFEAECF0)
        )
    }
}

@Composable
private fun RequestTypesBreakdown() {
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
                text = "Request Types",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = Color(0xFF101828)
            )
            
            listOf(
                Triple("Hardware", 345, PrimaryBlue),
                Triple("Software", 289, Color(0xFF8B5CF6)),
                Triple("Access", 178, CustomOrange),
                Triple("Support", 156, CustomGreen)
            ).forEach { (type, count, color) ->
                RequestTypeItem(type, count, color)
            }
        }
    }
}

@Composable
private fun RequestTypeItem(type: String, count: Int, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(8.dp),
                shape = CircleShape,
                color = color
            ) {}
            
            Text(
                text = type,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF101828)
            )
        }
        
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = Color(0xFF101828)
        )
    }
}

@Composable
private fun ResponseTimeMetrics() {
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
                text = "Response Time",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = Color(0xFF101828)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TimeMetricCard(
                    modifier = Modifier.weight(1f),
                    label = "Average",
                    value = "2.5h",
                    color = PrimaryBlue
                )
                
                TimeMetricCard(
                    modifier = Modifier.weight(1f),
                    label = "Fastest",
                    value = "15min",
                    color = CustomGreen
                )
                
                TimeMetricCard(
                    modifier = Modifier.weight(1f),
                    label = "Slowest",
                    value = "8.2h",
                    color = CustomRed
                )
            }
        }
    }
}

@Composable
private fun TimeMetricCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    color: Color
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge.copy(
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
private fun SLAComplianceSection() {
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "SLA Compliance",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = Color(0xFF101828)
            )
            
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "94%",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = CustomGreen
                    )
                    Text(
                        text = "SLA Met This Month",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF667085)
                    )
                }
            }
            
            HorizontalDivider(color = Color(0xFFEAECF0))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SLAStatItem("On Time", "927", CustomGreen)
                SLAStatItem("Breached", "58", CustomRed)
                SLAStatItem("At Risk", "45", CustomOrange)
            }
        }
    }
}

@Composable
private fun SLAStatItem(label: String, value: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge.copy(
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
