package com.project.irequest.presentation.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.irequest.R
import com.project.irequest.presentation.theme.CustomGreen
import com.project.irequest.presentation.theme.CustomOrange
import com.project.irequest.presentation.theme.CustomRed
import com.project.irequest.presentation.theme.PrimaryBlue

/**
 * Profile Screen - User profile and settings
 * Mapped from: Areas/Identity/Views/Manage/Index.cshtml
 * Clean, minimal card design focused on information hierarchy
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("FunctionName")
fun ProfileScreen(
    onLogout: () -> Unit = {},
    onEditProfile: () -> Unit = {},
    onChangePassword: () -> Unit = {},
    onSettings: () -> Unit = {},
    onPersonalInfo: () -> Unit = {},
    onTermsOfService: () -> Unit = {},
    onPrivacyPolicy: () -> Unit = {},
    onDataPrivacy: () -> Unit = {},
    onSecurityPrivacy: () -> Unit = {},
    onSupportHelp: () -> Unit = {}
) {
    var twoFactorEnabled by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 1.dp,
            color = Color.White
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
            // Avatar & Basic Info Sectionf
            item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp) 
            ) {
                // Avatar with Edit Button
                Box(
                    modifier = Modifier.padding(vertical = 16.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    // Avatar Image
                    androidx.compose.foundation.Image(
                        painter = painterResource(id = R.drawable.avatar),
                        contentDescription = "Profile Avatar",
                        modifier = Modifier.size(68.dp)
                    )
                    
                    // Edit Avatar Button
                    Surface(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .clickable(onClick = onEditProfile),
                        color = PrimaryBlue,
                        shadowElevation = 4.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
                
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                // Name
                Text(
                    text = "Nguyễn Văn A",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Text(
                        text = "Nhân viên IT",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
    
                    // Department Badge
                    Surface(
                        color = PrimaryBlue.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "Phòng IT",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = PrimaryBlue
                        )
                    }
                }
                
            }
            }
        }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Legal & Support Items
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            ActionListItem(
                iconRes = R.drawable.user,
                title = "Thông tin cá nhân",
                onClick = onPersonalInfo,
                showTopBorder = false,
                showBottomBorder = true
            )
            
            ActionListItem(
                iconRes = R.drawable.file_text,
                title = "Điều khoản sử dụng",
                onClick = onTermsOfService,
                showTopBorder = true,
                showBottomBorder = true
            )
            
            ActionListItem(
                iconRes = R.drawable.shield_keyhole,
                title = "Chính sách bảo mật",
                onClick = onPrivacyPolicy,
                showTopBorder = true,
                showBottomBorder = true
            )
            
            ActionListItem(
                iconRes = R.drawable.key,
                title = "Chính sách dữ liệu cá nhân",
                onClick = onDataPrivacy,
                showTopBorder = true,
                showBottomBorder = true
            )
            
            ActionListItem(
                iconRes = R.drawable.shield_user,
                title = "Bảo mật và quyền riêng tư",
                onClick = onSecurityPrivacy,
                showTopBorder = true,
                showBottomBorder = true
            )
            
            ActionListItem(
                iconRes = R.drawable.question_circle,
                title = "Hỗ trợ và trợ giúp",
                onClick = onSupportHelp,
                showTopBorder = true,
                showBottomBorder = true
            )

            ActionListItem(
                iconRes = R.drawable.settings,
                title = "Cài đặt ứng dụng",
                onClick = onSettings,
                showTopBorder = true,
                showBottomBorder = true
            )
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Logout Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .height(44.dp)
                .background(
                    color = Color(0xFFFEE4E2),
                    shape = RoundedCornerShape(24.dp)
                )
                .clip(RoundedCornerShape(8.dp))
                .clickable(onClick = onLogout),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Đăng xuất",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                ),
                color = Color(0xFFF04438)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun StatItem(
    label: String,
    value: String,
    color: Color
) {
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
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun InfoListItem(
    icon: ImageVector,
    label: String,
    value: String,
    onClick: (() -> Unit)?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .let { if (onClick != null) it.clickable(onClick = onClick) else it }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium
                )
            )
        }
        
        if (onClick != null) {
            Image(
                painter = painterResource(id = R.drawable.alt_arrow_right),
                contentDescription = "Navigate",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun ActionListItem(
    iconRes: Int,
    title: String,
    onClick: () -> Unit,
    showTopBorder: Boolean = false,
    showBottomBorder: Boolean = true
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (showTopBorder) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFFFFFFF)
            )
        }
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(20.dp)
            )
            
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.weight(1f)
            )
            
            Image(
                painter = painterResource(id = R.drawable.alt_arrow_right),
                contentDescription = "Navigate",
                modifier = Modifier.size(16.dp)
            )
        }
        
        if (showBottomBorder) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFF2F4F7)
            )
        }
    }
}

