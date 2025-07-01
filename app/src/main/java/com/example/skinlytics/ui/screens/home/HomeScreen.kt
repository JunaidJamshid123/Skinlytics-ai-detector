package com.example.skinlytics.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skinlytics.ui.theme.BrownRich
import com.example.skinlytics.ui.theme.BrownDeep
import com.example.skinlytics.ui.theme.SkinPeach
import com.example.skinlytics.ui.theme.SkinBeige
import com.example.skinlytics.ui.theme.SkinMedium
import com.example.skinlytics.ui.theme.SkinCream

// For navigation
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// For viewModel in Compose
import androidx.lifecycle.viewmodel.compose.viewModel

// For Material Icons
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Chat

import kotlinx.coroutines.delay
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import com.example.skinlytics.ui.components.ChatBotModal
import com.example.skinlytics.ui.theme.BrownRich
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.layout.padding
import com.example.skinlytics.ui.components.ScreenWithChatbot

@Composable
fun HomeScreen(onStartScan: () -> Unit) {
    val skinGradient = Brush.verticalGradient(
        colors = listOf(
            SkinPeach,
            SkinBeige,
            SkinMedium,
            SkinCream
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(skinGradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(12.dp, RoundedCornerShape(28.dp)),
                shape = RoundedCornerShape(28.dp),
                colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.97f)),
                elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(32.dp)
                ) {
                    Text(
                        text = "Welcome to Skinlytics!",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrownRich,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Your personal AI-powered skin health companion. Scan, analyze, and track your skin with confidence.",
                        fontSize = 16.sp,
                        color = BrownDeep,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    Button(
                        onClick = { onStartScan() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BrownRich),
                        shape = RoundedCornerShape(18.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                    ) {
                        Text(
                            text = "Start Skin Scan",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
} 