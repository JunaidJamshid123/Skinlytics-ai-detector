package com.example.skinlytics.ui.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skinlytics.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
    // Animation states
    var startAnimation by remember { mutableStateOf(false) }

    // Logo animations
    val logoScale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.3f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "logoScale"
    )

    val logoAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "logoAlpha"
    )

    // Text animations with delays
    val titleAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 500,
            easing = FastOutSlowInEasing
        ),
        label = "titleAlpha"
    )

    val titleOffset by animateFloatAsState(
        targetValue = if (startAnimation) 0f else 50f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 500,
            easing = FastOutSlowInEasing
        ),
        label = "titleOffset"
    )

    val taglineAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "taglineAlpha"
    )

    val taglineOffset by animateFloatAsState(
        targetValue = if (startAnimation) 0f else 30f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "taglineOffset"
    )

    // Pulsing animation for the logo background
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    // Floating dots animation
    val dot1Offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -25f,
        animationSpec = infiniteRepeatable(
            animation = tween(3200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot1Offset"
    )

    val dot2Offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot2Offset"
    )

    val dot3Offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -15f,
        animationSpec = infiniteRepeatable(
            animation = tween(3600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot3Offset"
    )

    val dot4Offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 18f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot4Offset"
    )

    // Loading dots alpha animation
    val loadingDotsAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 1500,
            easing = FastOutSlowInEasing
        ),
        label = "loadingDotsAlpha"
    )

    // Start animation on composition
    LaunchedEffect(Unit) {
        delay(300)
        startAnimation = true
    }

    // Skin-tone inspired gradient background
    val skinGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFDE8D4), // Light peachy skin tone
            Color(0xFFF4E4D1), // Warm beige
            Color(0xFFEDD5C3), // Medium skin tone
            Color(0xFFF7EFE7)  // Light cream
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(skinGradient),
        contentAlignment = Alignment.Center
    ) {
        // Floating decorative elements with skin-tone colors
        FloatingDots(
            dot1Offset = dot1Offset,
            dot2Offset = dot2Offset,
            dot3Offset = dot3Offset,
            dot4Offset = dot4Offset,
            alpha = if (startAnimation) 0.4f else 0f
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo with pulsing background circle
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                // Outer pulsing circle - darker skin tone
                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .scale(pulseScale)
                        .alpha(logoAlpha * 0.15f)
                        .background(
                            Color(0xFFD4A574), // Medium-dark skin tone
                            CircleShape
                        )
                )

                // Inner pulsing circle - lighter skin tone
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .scale(pulseScale * 0.9f)
                        .alpha(logoAlpha * 0.25f)
                        .background(
                            Color(0xFFE8C5A0), // Light-medium skin tone
                            CircleShape
                        )
                )

                // Logo
                Image(
                    painter = painterResource(id = R.drawable.skin_cell),
                    contentDescription = "Skinlytics Logo",
                    modifier = Modifier
                        .size(85.dp)
                        .scale(logoScale)
                        .alpha(logoAlpha)
                )
            }

            // App Title with rich brown color
            Text(
                text = "skinlytics",
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8B4A2B), // Rich brown, natural skin undertone
                modifier = Modifier
                    .alpha(titleAlpha)
                    .graphicsLayer {
                        translationY = titleOffset
                    }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Tagline with warm brown
            Text(
                text = "AI-Powered Skin Health Companion",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF6B3E2A), // Deeper brown for contrast
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .alpha(taglineAlpha)
                    .graphicsLayer {
                        translationY = taglineOffset
                    }
                    .padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(65.dp))

            // Loading animation with skin-tone colors
            LoadingDots(alpha = loadingDotsAlpha)
        }
    }
}

@Composable
fun FloatingDots(
    dot1Offset: Float,
    dot2Offset: Float,
    dot3Offset: Float,
    dot4Offset: Float,
    alpha: Float
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(alpha)
    ) {
        // Top right dot - light skin tone
        Box(
            modifier = Modifier
                .size(14.dp)
                .offset(
                    x = (-70).dp,
                    y = (130 + dot1Offset).dp
                )
                .align(Alignment.TopEnd)
                .background(
                    Color(0xFFE8C5A0).copy(alpha = 0.7f), // Light-medium skin tone
                    CircleShape
                )
        )

        // Bottom left dot - medium skin tone
        Box(
            modifier = Modifier
                .size(18.dp)
                .offset(
                    x = 70.dp,
                    y = (-140 + dot2Offset).dp
                )
                .align(Alignment.BottomStart)
                .background(
                    Color(0xFFD4A574).copy(alpha = 0.6f), // Medium skin tone
                    CircleShape
                )
        )

        // Middle right dot - darker skin tone
        Box(
            modifier = Modifier
                .size(10.dp)
                .offset(
                    x = (-50).dp,
                    y = (dot3Offset).dp
                )
                .align(Alignment.CenterEnd)
                .background(
                    Color(0xFFB8956A).copy(alpha = 0.5f), // Medium-dark skin tone
                    CircleShape
                )
        )

        // Top left dot - peachy skin tone
        Box(
            modifier = Modifier
                .size(12.dp)
                .offset(
                    x = 80.dp,
                    y = (160 + dot4Offset).dp
                )
                .align(Alignment.TopStart)
                .background(
                    Color(0xFFF2C2A7).copy(alpha = 0.6f), // Peachy skin tone
                    CircleShape
                )
        )

        // Bottom right dot - warm skin tone
        Box(
            modifier = Modifier
                .size(16.dp)
                .offset(
                    x = (-60).dp,
                    y = (-180 + dot1Offset * 0.7f).dp
                )
                .align(Alignment.BottomEnd)
                .background(
                    Color(0xFFDDB892).copy(alpha = 0.5f), // Warm skin tone
                    CircleShape
                )
        )
    }
}

@Composable
fun LoadingDots(alpha: Float) {
    val infiniteTransition = rememberInfiniteTransition(label = "loading")

    val dot1Alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot1Alpha"
    )

    val dot2Alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, delayMillis = 200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot2Alpha"
    )

    val dot3Alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, delayMillis = 400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot3Alpha"
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.alpha(alpha)
    ) {
        repeat(3) { index ->
            val dotAlpha = when (index) {
                0 -> dot1Alpha
                1 -> dot2Alpha
                else -> dot3Alpha
            }

            val dotColor = when (index) {
                0 -> Color(0xFFD4A574) // Medium skin tone
                1 -> Color(0xFFE8C5A0) // Light-medium skin tone
                else -> Color(0xFFB8956A) // Medium-dark skin tone
            }

            Box(
                modifier = Modifier
                    .size(9.dp)
                    .alpha(dotAlpha)
                    .background(
                        dotColor,
                        CircleShape
                    )
            )
        }
    }
} 