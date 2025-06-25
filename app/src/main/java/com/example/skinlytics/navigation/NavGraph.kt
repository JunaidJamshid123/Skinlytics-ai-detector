package com.example.skinlytics.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.skinlytics.ui.screens.splash.SplashScreen
import com.example.skinlytics.ui.screens.home.HomeScreen
import com.example.skinlytics.ui.screens.history.HistoryScreen
import com.example.skinlytics.ui.screens.scan.ScanScreen
import com.example.skinlytics.ui.screens.profile.ProfileScreen
import com.example.skinlytics.ui.screens.settings.SettingsScreen
import com.example.skinlytics.ui.components.BottomNavItem

@Composable
fun NavGraph(navController: NavHostController, startDestination: String = "splash") {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("splash") { SplashScreen() }
        composable(BottomNavItem.Home.route) { HomeScreen() }
        composable(BottomNavItem.History.route) { HistoryScreen() }
        composable(BottomNavItem.Scan.route) { ScanScreen() }
        composable(BottomNavItem.Profile.route) { ProfileScreen() }
        composable(BottomNavItem.Settings.route) { SettingsScreen() }
    }
} 