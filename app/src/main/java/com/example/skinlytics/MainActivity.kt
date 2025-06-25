package com.example.skinlytics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.skinlytics.navigation.NavGraph
import com.example.skinlytics.ui.components.BottomNavBar
import com.example.skinlytics.ui.components.bottomNavItems
import com.example.skinlytics.ui.theme.SkinlyticsTheme
import com.example.skinlytics.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkinlyticsTheme {
                val viewModel: MainViewModel = viewModel()
                val splashFinished by viewModel.splashFinished.collectAsState()
                val navController = rememberNavController()
                var selectedRoute by remember { mutableStateOf(bottomNavItems[0].route) }

                LaunchedEffect(Unit) {
                    viewModel.startSplashTimer()
                }

                if (!splashFinished) {
                    com.example.skinlytics.ui.screens.splash.SplashScreen()
                } else {
                    Scaffold(
                        bottomBar = {
                            BottomNavBar(selectedRoute = selectedRoute) { route ->
                                selectedRoute = route
                                navController.navigate(route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    ) { innerPadding ->
                        NavGraph(
                            navController = navController,
                            startDestination = selectedRoute
                        )
                    }
                }
            }
        }
    }
}