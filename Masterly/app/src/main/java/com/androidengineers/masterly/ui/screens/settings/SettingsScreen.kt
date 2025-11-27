package com.androidengineers.masterly.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    var pushNotificationsEnabled by remember { mutableStateOf(true) }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        SettingsHeader(onBackClick = onBackClick)
        
        // Upgrade Banner
        UpgradeBanner(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Pro Features Section
        SettingsSectionHeader(title = "Pro Features")
        
        ProFeatureItem(
            icon = Icons.Default.Notifications,
            title = "Cloud Sync & Backup",
            subtitle = "Sync your data across all devices"
        )
        
        ProFeatureItem(
            icon = Icons.Default.Notifications,
            title = "Daily Reminders",
            subtitle = "Get notified to practice your skills"
        )
        
        ProFeatureItem(
            icon = Icons.Default.Notifications,
            title = "Custom Themes & Icons",
            subtitle = "Personalize your app appearance"
        )
        
        ProFeatureItem(
            icon = Icons.Default.Notifications,
            title = "Advanced Analytics",
            subtitle = "Detailed insights and progress tracking"
        )
        
        ProFeatureItem(
            icon = Icons.Default.Notifications,
            title = "Focus Challenges",
            subtitle = "Join skill mastery challenges"
        )
        
        ProFeatureItem(
            icon = Icons.Default.Notifications,
            title = "Milestones & Badges",
            subtitle = "Earn rewards for your progress"
        )
        
        ProFeatureItem(
            icon = Icons.Default.Notifications,
            title = "CSV Export & Calendar Sync",
            subtitle = "Export data and sync with calendar"
        )
        
        ProFeatureItem(
            icon = Icons.Default.Notifications,
            title = "Voice Notes",
            subtitle = "Record reflections after sessions"
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // General Section
        SettingsSectionHeader(title = "General")
        
        SettingsToggleItem(
            icon = Icons.Default.Notifications,
            title = "Push Notifications",
            subtitle = "Receive app notifications",
            checked = pushNotificationsEnabled,
            onCheckedChange = { pushNotificationsEnabled = it }
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Account & Support Section
        SettingsSectionHeader(title = "Account & Support")
        
        SettingsNavigationItem(
            icon = Icons.Default.Person,
            title = "Account Settings",
            subtitle = "Manage your profile",
            onClick = { /* TODO */ }
        )
        
        SettingsActionItem(
            icon = Icons.Default.Notifications,
            title = "Backup Data",
            subtitle = "Pro feature - Cloud backup",
            actionText = "Backup Now",
            onClick = { /* TODO */ }
        )
        
        SettingsActionItem(
            icon = Icons.Default.Notifications,
            title = "Export Data",
            subtitle = "Pro feature - Data export",
            actionText = "Export CSV",
            onClick = { /* TODO */ }
        )
        
        SettingsNavigationItem(
            icon = Icons.Default.Notifications,
            title = "Privacy & Security",
            subtitle = "Data and privacy settings",
            onClick = { /* TODO */ }
        )
        
        SettingsNavigationItem(
            icon = Icons.Default.Notifications,
            title = "Help & Support",
            subtitle = "Get help and contact us",
            onClick = { /* TODO */ }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun SettingsHeader(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun UpgradeBanner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF7C3AED),
                        Color(0xFF3B82F6)
                    )
                )
            )
            .clickable { /* TODO: Navigate to upgrade */ }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column {
                    Text(
                        text = "Upgrade to Pro",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Unlock all premium features",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 12.sp
                    )
                }
            }
            
            Button(
                onClick = { /* TODO: Navigate to upgrade */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF7C3AED)
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Upgrade",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
private fun ProFeatureItem(
    icon: ImageVector,
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: Show upgrade prompt */ }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF2A2A2A)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF7C3AED),
                modifier = Modifier.size(20.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
        
        Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = "Locked",
            tint = Color.Gray,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
private fun SettingsToggleItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF2A2A2A)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
        
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF7C3AED),
                uncheckedThumbColor = Color.Gray,
                uncheckedTrackColor = Color(0xFF2A2A2A)
            )
        )
    }
}

@Composable
private fun SettingsNavigationItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF2A2A2A)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
        
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Navigate",
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun SettingsActionItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    actionText: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF2A2A2A)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
        
        TextButton(
            onClick = onClick,
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color(0xFF7C3AED)
            )
        ) {
            Text(
                text = actionText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
