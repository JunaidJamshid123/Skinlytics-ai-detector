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
import com.example.skinlytics.ui.components.ChatBotModal
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import com.example.skinlytics.ui.theme.BrownRich
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkinlyticsTheme {
                val viewModel: MainViewModel = viewModel()
                val splashFinished by viewModel.splashFinished.collectAsState()
                val navController = rememberNavController()
                var selectedRoute by remember { mutableStateOf(bottomNavItems[0].route) }
                var showChat by remember { mutableStateOf(false) }

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
                        },
                        floatingActionButton = {
                            FloatingActionButton(
                                onClick = { showChat = true },
                                containerColor = BrownRich,
                                modifier = Modifier.padding(bottom = 16.dp, end = 8.dp)
                            ) {
                                Icon(Icons.Default.Chat, contentDescription = "Chatbot", tint = Color.White)
                            }
                        }
                    ) { innerPadding ->
                        Box(Modifier.padding(innerPadding)) {
                            NavGraph(
                                navController = navController,
                                startDestination = selectedRoute
                            )
                            ChatBotModal(visible = showChat, onDismiss = { showChat = false })
                        }
                    }
                }
            }
        }
    }
}