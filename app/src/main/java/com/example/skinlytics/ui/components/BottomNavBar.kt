package com.example.skinlytics.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object History : BottomNavItem("history", Icons.Default.History, "History")
    object Scan : BottomNavItem("scan", Icons.Default.CameraAlt, "Scan")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
    object Settings : BottomNavItem("settings", Icons.Default.Settings, "Settings")
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.History,
    BottomNavItem.Scan,
    BottomNavItem.Profile,
    BottomNavItem.Settings
)

@Composable
fun BottomNavBar(selectedRoute: String, onItemSelected: (String) -> Unit) {
    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = selectedRoute == item.route,
                onClick = { onItemSelected(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
} 