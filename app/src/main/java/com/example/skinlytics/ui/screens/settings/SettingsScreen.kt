package com.example.skinlytics.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.skinlytics.viewmodel.SettingsViewModel
import com.example.skinlytics.ui.theme.BrownRich
import com.example.skinlytics.ui.theme.BrownDeep
import com.example.skinlytics.ui.theme.SkinPeach
import com.example.skinlytics.ui.theme.SkinBeige
import com.example.skinlytics.ui.theme.SkinMedium
import com.example.skinlytics.ui.theme.SkinCream

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val settingsList = viewModel.settingsList.collectAsState().value
    val skinGradient = Brush.verticalGradient(
        colors = listOf(SkinPeach, SkinBeige, SkinMedium, SkinCream)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(skinGradient),
        contentAlignment = Alignment.TopCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.93f)
                .padding(top = 32.dp),
            shape = RoundedCornerShape(24.dp),
            colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.97f)),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier.padding(28.dp)
            ) {
                Text(
                    text = "Settings",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrownRich,
                    modifier = Modifier.padding(bottom = 18.dp)
                )
                settingsList.forEach { label ->
                    val icon = when (label) {
                        "Notifications" -> Icons.Default.Notifications
                        "Dark Mode" -> Icons.Default.DarkMode
                        "About App" -> Icons.Default.Info
                        else -> Icons.Default.Settings
                    }
                    SettingsItem(icon = icon, label = label)
                }
            }
        }
    }
}

@Composable
fun SettingsItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 14.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = BrownRich,
            modifier = Modifier.size(26.dp)
        )
        Spacer(modifier = Modifier.width(18.dp))
        Text(
            text = label,
            fontSize = 16.sp,
            color = BrownDeep
        )
    }
} 