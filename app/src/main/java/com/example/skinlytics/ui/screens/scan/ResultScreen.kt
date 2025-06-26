package com.example.skinlytics.ui.screens.scan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultScreen() {
    val skinGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFDE8D4),
            Color(0xFFF4E4D1),
            Color(0xFFEDD5C3),
            Color(0xFFF7EFE7)
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(skinGradient),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Scan Result",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8B4A2B)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "This is a placeholder for the scan result.",
                fontSize = 18.sp,
                color = Color(0xFF6B3E2A)
            )
        }
    }
} 