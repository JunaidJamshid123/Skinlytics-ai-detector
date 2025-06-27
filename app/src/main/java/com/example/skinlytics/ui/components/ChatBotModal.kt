package com.example.skinlytics.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skinlytics.ui.theme.BrownRich
import com.example.skinlytics.ui.theme.BrownDeep
import androidx.compose.foundation.clickable
import androidx.compose.material3.TextFieldDefaults

@Composable
fun ChatBotModal(
    visible: Boolean,
    onDismiss: () -> Unit
) {
    if (!visible) return
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.25f))
            .clickable(onClick = onDismiss)
    ) {
        Box(
            Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 24.dp),
                tonalElevation = 8.dp,
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .widthIn(max = 380.dp)
                    .fillMaxWidth(0.95f)
                    .heightIn(min = 320.dp, max = 480.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    // Header
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(BrownRich)
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = "Chatbot",
                            tint = Color.White
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            "Chatbot",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(Modifier.weight(1f))
                        IconButton(onClick = onDismiss) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                        }
                    }
                    // Messages
                    Column(
                        Modifier
                            .weight(1f)
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        // Hardcoded messages
                        ChatMessage("Hi! How can I help you today?", isUser = false)
                        Spacer(Modifier.height(8.dp))
                        ChatMessage("Show me my skin history.", isUser = true)
                        Spacer(Modifier.height(8.dp))
                        ChatMessage("Here is your recent skin analysis history.", isUser = false)
                    }
                    // Input
                    var input by remember { mutableStateOf("") }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = input,
                            onValueChange = { input = it },
                            placeholder = { Text("Type a message...") },
                            modifier = Modifier.weight(1f),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            maxLines = 2,
                            singleLine = false
                        )
                        IconButton(onClick = { input = "" }, enabled = input.isNotBlank()) {
                            Icon(Icons.Default.Send, contentDescription = "Send", tint = BrownDeep)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ChatMessage(text: String, isUser: Boolean) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = if (isUser) BrownRich else MaterialTheme.colorScheme.surfaceVariant,
            tonalElevation = 2.dp
        ) {
            Text(
                text,
                color = if (isUser) Color.White else Color.Black,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

@Composable
fun HomeScreen() {
    var showChat by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Welcome to Home", modifier = Modifier.align(Alignment.Center))
        // Floating Chatbot Button
        FloatingActionButton(
            onClick = { showChat = true },
            containerColor = BrownRich,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(Icons.Default.Chat, contentDescription = "Chatbot", tint = Color.White)
        }
        // ChatBot Modal
        ChatBotModal(visible = showChat, onDismiss = { showChat = false })
    }
} 