package com.example.skinlytics.ui.screens.scan

import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skinlytics.viewmodel.ScanUiState
import com.example.skinlytics.viewmodel.ScanViewModel
import com.google.accompanist.flowlayout.FlowRow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.graphics.Bitmap
import android.net.Uri
import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import com.example.skinlytics.model.ScanRepository
import java.io.InputStream
import android.widget.Toast
import android.util.Log
import androidx.lifecycle.ViewModelStoreOwner
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun ResultScreen(
    viewModelStoreOwner: ViewModelStoreOwner,
    selectedImageUri: Uri? = null,
    selectedBitmap: Bitmap? = null,
    context: Context = LocalContext.current
) {
    val scanViewModel: ScanViewModel = viewModel(viewModelStoreOwner = viewModelStoreOwner, factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ScanViewModel(ScanRepository()) as T
        }
    })
    val uiState = scanViewModel.uiState.collectAsState().value
    when (uiState) {
        is ScanUiState.Success -> {
            val result = (uiState as ScanUiState.Success).result
            val configuration = LocalConfiguration.current
            val screenWidth = configuration.screenWidthDp.dp
            val skinGradient = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFFDE8D4),
                    Color(0xFFF4E4D1),
                    Color(0xFFEDD5C3),
                    Color(0xFFF7EFE7)
                )
            )
            val horizontalPadding = when {
                screenWidth < 360.dp -> 12.dp
                screenWidth < 480.dp -> 18.dp
                else -> 24.dp
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(skinGradient)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = horizontalPadding, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // --- Analyzed Image ---
                    val imageModifier = Modifier
                        .size(180.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .shadow(10.dp, RoundedCornerShape(20.dp))
                    when {
                        selectedBitmap != null -> {
                            Image(
                                bitmap = selectedBitmap.asImageBitmap(),
                                contentDescription = "Analyzed skin image",
                                modifier = imageModifier,
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(18.dp))
                        }
                        selectedImageUri != null -> {
                            val inputStream: InputStream? = remember(selectedImageUri) {
                                context.contentResolver.openInputStream(selectedImageUri)
                            }
                            val bitmap = remember(selectedImageUri) {
                                inputStream?.use { BitmapFactory.decodeStream(it) }
                            }
                            if (bitmap != null) {
                                Image(
                                    bitmap = bitmap.asImageBitmap(),
                                    contentDescription = "Analyzed skin image",
                                    modifier = imageModifier,
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.height(18.dp))
                            }
                        }
                    }
                    // --- Prediction & Severity ---
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(10.dp, RoundedCornerShape(24.dp)),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.98f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = result.prediction,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF8B4A2B),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            if (result.severity.isNotBlank()) {
                                Card(
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = when (result.severity.lowercase()) {
                                            "mild" -> Color(0xFFB5D6A7)
                                            "moderate" -> Color(0xFFFFE082)
                                            "severe" -> Color(0xFFE57373)
                                            else -> Color(0xFFB5D6A7)
                                        }
                                    ),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                ) {
                                    Text(
                                        text = result.severity.uppercase(),
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(22.dp))
                    // --- About Section ---
                    if (result.about.isNotBlank()) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(6.dp, RoundedCornerShape(18.dp)),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.93f)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                        ) {
                            Column(Modifier.padding(20.dp)) {
                                Text(
                                    text = "About This Condition",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF8B4A2B)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = result.about,
                                    fontSize = 16.sp,
                                    color = Color(0xFF6B3E2A),
                                    textAlign = TextAlign.Start,
                                    lineHeight = 22.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(18.dp))
                    }
                    // --- Symptoms Section ---
                    if (result.common_symptoms.isNotEmpty()) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(6.dp, RoundedCornerShape(18.dp)),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.93f)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                        ) {
                            Column(Modifier.padding(20.dp)) {
                                Text(
                                    text = "Common Symptoms",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF8B4A2B),
                                    modifier = Modifier.padding(bottom = 10.dp)
                                )
                                FlowRow(
                                    mainAxisSpacing = 8.dp,
                                    crossAxisSpacing = 8.dp,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    result.common_symptoms.forEach { symptom ->
                                        Card(
                                            shape = RoundedCornerShape(12.dp),
                                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF4E4D1)),
                                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                        ) {
                                            Text(
                                                text = symptom,
                                                color = Color(0xFF6B3E2A),
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Medium,
                                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(18.dp))
                    }
                    // --- Recommendations Section ---
                    if (result.treatment_recommendations.isNotEmpty()) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(6.dp, RoundedCornerShape(18.dp)),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.93f)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                        ) {
                            Column(Modifier.padding(20.dp)) {
                                Text(
                                    text = "Treatment Recommendations",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF8B4A2B),
                                    modifier = Modifier.padding(bottom = 10.dp)
                                )
                                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                    result.treatment_recommendations.forEachIndexed { index, rec ->
                                        Card(
                                            shape = RoundedCornerShape(12.dp),
                                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF4E4D1)),
                                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.padding(16.dp)
                                            ) {
                                                Text(
                                                    text = "${index + 1}",
                                                    color = Color(0xFF8B4A2B),
                                                    fontSize = 13.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier.padding(end = 12.dp)
                                                )
                                                Text(
                                                    text = rec,
                                                    color = Color(0xFF6B3E2A),
                                                    fontSize = 15.sp,
                                                    fontWeight = FontWeight.Medium,
                                                    lineHeight = 20.sp,
                                                    modifier = Modifier.weight(1f)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(18.dp))
                    }
                    // --- Disclaimer Section ---
                    if (result.disclaimer.isNotBlank()) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(4.dp, RoundedCornerShape(12.dp)),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE082).copy(alpha = 0.3f)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = result.disclaimer,
                                    fontSize = 12.sp,
                                    color = Color(0xFF6B3E2A),
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
        is ScanUiState.Error -> {
            val error = (uiState as ScanUiState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: $error", color = Color.Red, fontWeight = FontWeight.Bold)
            }
        }
        is ScanUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
                Text("Loading...", modifier = Modifier.padding(top = 16.dp))
            }
        }
        else -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No result available.")
            }
        }
    }
}