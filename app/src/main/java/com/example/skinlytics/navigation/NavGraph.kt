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
import com.example.skinlytics.ui.screens.scan.ResultScreen
import androidx.lifecycle.ViewModelStoreOwner
import androidx.compose.runtime.remember

@Composable
fun NavGraph(navController: NavHostController, startDestination: String = "splash") {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("splash") { SplashScreen() }
        composable(BottomNavItem.Home.route) { HomeScreen() }
        composable(BottomNavItem.History.route) { HistoryScreen() }
        composable(BottomNavItem.Scan.route) { backStackEntry ->
            ScanScreen(onViewResult = { navController.navigate("result") }, viewModelStoreOwner = backStackEntry)
        }
        composable("result") {
            val scanBackStackEntry = remember(navController) {
                navController.getBackStackEntry(BottomNavItem.Scan.route)
            }
            ResultScreen(viewModelStoreOwner = scanBackStackEntry)
        }
        composable(BottomNavItem.Profile.route) { ProfileScreen() }
        composable(BottomNavItem.Settings.route) { SettingsScreen() }
    }
} 