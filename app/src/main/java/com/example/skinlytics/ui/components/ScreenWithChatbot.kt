package com.example.skinlytics.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.skinlytics.ui.theme.BrownRich

@Composable
fun ScreenWithChatbot(content: @Composable () -> Unit) {
    var showChat by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        content()
        FloatingActionButton(
            onClick = { showChat = true },
            containerColor = BrownRich,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(Icons.Default.Chat, contentDescription = "Chatbot", tint = Color.White)
        }
        ChatBotModal(visible = showChat, onDismiss = { showChat = false })
    }
} 