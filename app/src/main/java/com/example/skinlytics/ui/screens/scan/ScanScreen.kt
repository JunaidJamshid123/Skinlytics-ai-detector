package com.example.skinlytics.ui.screens.scan

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import kotlinx.coroutines.delay
import java.io.InputStream
import com.example.skinlytics.ui.components.ChatBotModal
import com.example.skinlytics.ui.components.ScreenWithChatbot
import com.example.skinlytics.ui.theme.BrownRich

/**
 * Enhanced ScanScreen with professional medical scanner UI
 * Fully responsive design with reduced sizes for better mobile experience
 */
@Composable
fun ScanScreen(
    onViewResult: () -> Unit = {}
) {
    // Get screen configuration for responsive design
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val isSmallScreen = screenHeight < 700.dp || screenWidth < 360.dp

    // Original color palette (unchanged)
    val skinGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFDE8D4), // Light peachy skin tone
            Color(0xFFF4E4D1), // Warm beige
            Color(0xFFEDD5C3), // Medium skin tone
            Color(0xFFF7EFE7)  // Light cream
        )
    )
    val buttonColor = Color(0xFF8B4A2B)
    val buttonTextColor = Color.White
    val accentColor = Color(0xFF6B3E2A)

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedBitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }
    var isScanning by remember { mutableStateOf(false) }
    var scanComplete by remember { mutableStateOf(false) }
    var scanProgress by remember { mutableStateOf(0f) }
    var scanStage by remember { mutableStateOf("") }
    var showPermissionDialog by remember { mutableStateOf(false) }
    var showChat by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Responsive sizing
    val headerIconSize = if (isSmallScreen) 36.dp else 40.dp
    val titleFontSize = if (isSmallScreen) 26.sp else 28.sp
    val subtitleFontSize = if (isSmallScreen) 12.sp else 13.sp
    val cardSize = if (isSmallScreen) 220.dp else 240.dp
    val buttonHeight = if (isSmallScreen) 48.dp else 52.dp
    val horizontalPadding = if (isSmallScreen) 16.dp else 20.dp
    val verticalSpacing = if (isSmallScreen) 16.dp else 20.dp

    // Animations
    val infiniteTransition = rememberInfiniteTransition(label = "scanning")
    val scannerRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    // Camera and gallery launchers
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            selectedBitmap = bitmap
            selectedImageUri = null
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            selectedImageUri = uri
            selectedBitmap = null
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        showPermissionDialog = !granted
        if (granted) {
            cameraLauncher.launch(null)
        }
    }

    // Scanning simulation
    LaunchedEffect(isScanning) {
        if (isScanning) {
            val stages = listOf(
                "Initializing scan..." to 0.1f,
                "Analyzing skin texture..." to 0.3f,
                "Detecting patterns..." to 0.5f,
                "Processing AI analysis..." to 0.7f,
                "Generating results..." to 0.9f,
                "Scan complete!" to 1.0f
            )

            stages.forEach { (stage, progress) ->
                scanStage = stage
                scanProgress = progress
                delay(800)
            }

            delay(500)
            isScanning = false
            scanComplete = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(skinGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = horizontalPadding)
                .statusBarsPadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header with responsive sizing
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = if (isSmallScreen) 16.dp else 24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Healing,
                    contentDescription = null,
                    modifier = Modifier.size(headerIconSize),
                    tint = buttonColor
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "AI Skin Analysis",
                    fontSize = titleFontSize,
                    fontWeight = FontWeight.Bold,
                    color = buttonColor,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Advanced dermatological scanning technology",
                    fontSize = subtitleFontSize,
                    color = accentColor,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(verticalSpacing))

            // Main scanning area with flexible height
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                when {
                    isScanning -> {
                        ScanningInterface(
                            selectedImageUri = selectedImageUri,
                            selectedBitmap = selectedBitmap,
                            scanProgress = scanProgress,
                            scanStage = scanStage,
                            scannerRotation = scannerRotation,
                            pulseScale = pulseScale,
                            buttonColor = buttonColor,
                            context = context,
                            cardSize = cardSize,
                            isSmallScreen = isSmallScreen
                        )
                    }
                    scanComplete -> {
                        ScanCompleteInterface(
                            selectedImageUri = selectedImageUri,
                            selectedBitmap = selectedBitmap,
                            onViewResult = onViewResult,
                            buttonColor = buttonColor,
                            buttonTextColor = buttonTextColor,
                            context = context,
                            cardSize = cardSize,
                            buttonHeight = buttonHeight,
                            isSmallScreen = isSmallScreen
                        )
                    }
                    else -> {
                        ImageSelectionInterface(
                            selectedImageUri = selectedImageUri,
                            selectedBitmap = selectedBitmap,
                            onCameraClick = {
                                val permission = Manifest.permission.CAMERA
                                val granted = ContextCompat.checkSelfPermission(context, permission) == PermissionChecker.PERMISSION_GRANTED
                                if (granted) {
                                    cameraLauncher.launch(null)
                                } else {
                                    permissionLauncher.launch(permission)
                                }
                            },
                            onGalleryClick = { galleryLauncher.launch("image/*") },
                            onStartScan = {
                                isScanning = true
                                scanComplete = false
                                scanProgress = 0f
                            },
                            buttonColor = buttonColor,
                            buttonTextColor = buttonTextColor,
                            accentColor = accentColor,
                            context = context,
                            cardSize = cardSize,
                            buttonHeight = buttonHeight,
                            isSmallScreen = isSmallScreen
                        )
                    }
                }
            }

            // Bottom spacing to prevent overlap with navigation
            Spacer(modifier = Modifier.height(if (isSmallScreen) 8.dp else 16.dp))
        }

        // Permission dialog
        if (showPermissionDialog) {
            AlertDialog(
                onDismissRequest = { showPermissionDialog = false },
                title = { Text("Camera Permission Required", fontSize = 16.sp) },
                text = { Text("We need camera access to capture skin images for analysis. This helps provide accurate diagnostic results.", fontSize = 14.sp) },
                confirmButton = {
                    TextButton(onClick = { showPermissionDialog = false }) {
                        Text("Understood", color = buttonColor, fontSize = 14.sp)
                    }
                },
                containerColor = Color(0xFFFDF6F0)
            )
        }
    }
}

@Composable
private fun ImageSelectionInterface(
    selectedImageUri: Uri?,
    selectedBitmap: android.graphics.Bitmap?,
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit,
    onStartScan: () -> Unit,
    buttonColor: Color,
    buttonTextColor: Color,
    accentColor: Color,
    context: android.content.Context,
    cardSize: androidx.compose.ui.unit.Dp,
    buttonHeight: androidx.compose.ui.unit.Dp,
    isSmallScreen: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        // Image preview area with responsive sizing
        Card(
            modifier = Modifier
                .size(cardSize)
                .padding(if (isSmallScreen) 12.dp else 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF4E4D1).copy(alpha = 0.7f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (selectedImageUri != null || selectedBitmap != null) {
                    when {
                        selectedBitmap != null -> {
                            Image(
                                bitmap = selectedBitmap!!.asImageBitmap(),
                                contentDescription = "Selected skin image",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        selectedImageUri != null -> {
                            val inputStream: InputStream? = remember(selectedImageUri) {
                                selectedImageUri?.let { uri ->
                                    context.contentResolver.openInputStream(uri)
                                }
                            }
                            val bitmap = remember(selectedImageUri) {
                                inputStream?.use { BitmapFactory.decodeStream(it) }
                            }
                            if (bitmap != null) {
                                Image(
                                    bitmap = bitmap.asImageBitmap(),
                                    contentDescription = "Selected skin image",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(16.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }

                    // Overlay for selected image
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Color.Black.copy(alpha = 0.3f),
                                RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Image selected",
                            modifier = Modifier.size(if (isSmallScreen) 36.dp else 40.dp),
                            tint = Color.White
                        )
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoCamera,
                            contentDescription = "No image",
                            modifier = Modifier.size(if (isSmallScreen) 48.dp else 56.dp),
                            tint = accentColor.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No image selected",
                            color = accentColor.copy(alpha = 0.8f),
                            fontSize = if (isSmallScreen) 14.sp else 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Capture or select a skin image",
                            color = accentColor.copy(alpha = 0.6f),
                            fontSize = if (isSmallScreen) 10.sp else 11.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(if (isSmallScreen) 20.dp else 24.dp))

        // Action buttons with responsive layout
        if (selectedImageUri == null && selectedBitmap == null) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                ActionButton(
                    onClick = onCameraClick,
                    icon = Icons.Default.CameraAlt,
                    text = "Camera",
                    buttonColor = buttonColor,
                    buttonTextColor = buttonTextColor,
                    modifier = Modifier.weight(1f),
                    buttonHeight = buttonHeight,
                    isSmallScreen = isSmallScreen
                )
                ActionButton(
                    onClick = onGalleryClick,
                    icon = Icons.Default.PhotoLibrary,
                    text = "Gallery",
                    buttonColor = buttonColor,
                    buttonTextColor = buttonTextColor,
                    modifier = Modifier.weight(1f),
                    buttonHeight = buttonHeight,
                    isSmallScreen = isSmallScreen
                )
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(if (isSmallScreen) 8.dp else 10.dp)
            ) {
                Button(
                    onClick = onStartScan,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(buttonHeight),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                    shape = RoundedCornerShape(if (isSmallScreen) 24.dp else 26.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Scanner,
                        contentDescription = null,
                        modifier = Modifier.size(if (isSmallScreen) 18.dp else 20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Start AI Analysis",
                        color = buttonTextColor,
                        fontSize = if (isSmallScreen) 15.sp else 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onCameraClick,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = buttonColor),
                        border = BorderStroke(1.5.dp, buttonColor),
                        modifier = Modifier.height(if (isSmallScreen) 36.dp else 40.dp)
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(if (isSmallScreen) 16.dp else 18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Retake", fontSize = if (isSmallScreen) 12.sp else 13.sp)
                    }
                    OutlinedButton(
                        onClick = onGalleryClick,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = buttonColor),
                        border = BorderStroke(1.5.dp, buttonColor),
                        modifier = Modifier.height(if (isSmallScreen) 36.dp else 40.dp)
                    ) {
                        Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(if (isSmallScreen) 16.dp else 18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Choose", fontSize = if (isSmallScreen) 12.sp else 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun ScanningInterface(
    selectedImageUri: Uri?,
    selectedBitmap: android.graphics.Bitmap?,
    scanProgress: Float,
    scanStage: String,
    scannerRotation: Float,
    pulseScale: Float,
    buttonColor: Color,
    context: android.content.Context,
    cardSize: androidx.compose.ui.unit.Dp,
    isSmallScreen: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.size(cardSize + 32.dp),
            contentAlignment = Alignment.Center
        ) {
            // Background image
            Card(
                modifier = Modifier.size(cardSize),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                when {
                    selectedBitmap != null -> {
                        Image(
                            bitmap = selectedBitmap!!.asImageBitmap(),
                            contentDescription = "Scanning image",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    selectedImageUri != null -> {
                        val inputStream: InputStream? = remember(selectedImageUri) {
                            selectedImageUri?.let { uri ->
                                context.contentResolver.openInputStream(uri)
                            }
                        }
                        val bitmap = remember(selectedImageUri) {
                            inputStream?.use { BitmapFactory.decodeStream(it) }
                        }
                        if (bitmap != null) {
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = "Scanning image",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }

            // Scanner overlay
            Box(
                modifier = Modifier
                    .size(cardSize)
                    .background(
                        Color.Black.copy(alpha = 0.4f),
                        RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Rotating scanner ring
                Box(
                    modifier = Modifier
                        .size(if (isSmallScreen) 140.dp else 160.dp)
                        .rotate(scannerRotation)
                        .border(
                            3.dp,
                            Brush.sweepGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    buttonColor,
                                    buttonColor.copy(alpha = 0.8f),
                                    Color.Transparent
                                )
                            ),
                            CircleShape
                        )
                )

                // Pulsing center
                Box(
                    modifier = Modifier
                        .size(if (isSmallScreen) 80.dp else 100.dp)
                        .scale(pulseScale)
                        .background(
                            buttonColor.copy(alpha = 0.3f),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Psychology,
                        contentDescription = "AI analyzing",
                        modifier = Modifier.size(if (isSmallScreen) 32.dp else 40.dp),
                        tint = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(if (isSmallScreen) 20.dp else 24.dp))

        // Progress indicator
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = scanStage,
                fontSize = if (isSmallScreen) 15.sp else 16.sp,
                fontWeight = FontWeight.Bold,
                color = buttonColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = { scanProgress },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = buttonColor,
                trackColor = Color(0xFFF4E4D1)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "${(scanProgress * 100).toInt()}%",
                fontSize = if (isSmallScreen) 12.sp else 13.sp,
                fontWeight = FontWeight.Medium,
                color = buttonColor.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
private fun ScanCompleteInterface(
    selectedImageUri: Uri?,
    selectedBitmap: android.graphics.Bitmap?,
    onViewResult: () -> Unit,
    buttonColor: Color,
    buttonTextColor: Color,
    context: android.content.Context,
    cardSize: androidx.compose.ui.unit.Dp,
    buttonHeight: androidx.compose.ui.unit.Dp,
    isSmallScreen: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        // Success animation
        Box(
            modifier = Modifier.size(cardSize),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.size(cardSize),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                when {
                    selectedBitmap != null -> {
                        Image(
                            bitmap = selectedBitmap!!.asImageBitmap(),
                            contentDescription = "Analyzed image",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    selectedImageUri != null -> {
                        val inputStream: InputStream? = remember(selectedImageUri) {
                            selectedImageUri?.let { uri ->
                                context.contentResolver.openInputStream(uri)
                            }
                        }
                        val bitmap = remember(selectedImageUri) {
                            inputStream?.use { BitmapFactory.decodeStream(it) }
                        }
                        if (bitmap != null) {
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = "Analyzed image",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }

            // Success overlay
            Box(
                modifier = Modifier
                    .size(cardSize)
                    .background(
                        Color.Black.copy(alpha = 0.3f),
                        RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.size(if (isSmallScreen) 80.dp else 100.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Scan complete",
                            modifier = Modifier.size(if (isSmallScreen) 44.dp else 56.dp),
                            tint = Color(0xFF4CAF50)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(if (isSmallScreen) 20.dp else 24.dp))

        Text(
            text = "Analysis Complete!",
            fontSize = if (isSmallScreen) 20.sp else 22.sp,
            fontWeight = FontWeight.Bold,
            color = buttonColor,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Your skin analysis is ready for review",
            fontSize = if (isSmallScreen) 13.sp else 14.sp,
            color = Color(0xFF6B3E2A),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(if (isSmallScreen) 20.dp else 24.dp))

        Button(
            onClick = onViewResult,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(buttonHeight),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            shape = RoundedCornerShape(if (isSmallScreen) 24.dp else 26.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Visibility,
                contentDescription = null,
                modifier = Modifier.size(if (isSmallScreen) 18.dp else 20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "View Detailed Results",
                color = buttonTextColor,
                fontSize = if (isSmallScreen) 15.sp else 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ActionButton(
    onClick: () -> Unit,
    icon: ImageVector,
    text: String,
    buttonColor: Color,
    buttonTextColor: Color,
    modifier: Modifier = Modifier,
    buttonHeight: androidx.compose.ui.unit.Dp,
    isSmallScreen: Boolean
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(buttonHeight),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        shape = RoundedCornerShape(if (isSmallScreen) 24.dp else 26.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(if (isSmallScreen) 18.dp else 20.dp),
            tint = buttonTextColor
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            color = buttonTextColor,
            fontSize = if (isSmallScreen) 13.sp else 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}