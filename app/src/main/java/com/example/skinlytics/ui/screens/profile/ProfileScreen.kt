package com.example.skinlytics.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.skinlytics.viewmodel.ProfileViewModel
import com.example.skinlytics.ui.theme.BrownRich
import com.example.skinlytics.ui.theme.BrownDeep
import com.example.skinlytics.ui.theme.SkinPeach
import com.example.skinlytics.ui.theme.SkinBeige
import com.example.skinlytics.ui.theme.SkinMedium
import com.example.skinlytics.ui.theme.SkinCream

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    val userName = viewModel.userName.collectAsState().value
    val userEmail = viewModel.userEmail.collectAsState().value
    val skinGradient = Brush.verticalGradient(
        colors = listOf(SkinPeach, SkinBeige, SkinMedium, SkinCream)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(skinGradient),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(24.dp),
            shape = RoundedCornerShape(24.dp),
            colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.97f)),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(32.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(SkinMedium),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "User Avatar",
                        tint = BrownRich,
                        modifier = Modifier.size(60.dp)
                    )
                }
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = userName,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrownRich
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = userEmail,
                    fontSize = 15.sp,
                    color = BrownDeep
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "This is your profile page. You can add more details here.",
                    fontSize = 14.sp,
                    color = BrownDeep
                )
            }
        }
    }
} 