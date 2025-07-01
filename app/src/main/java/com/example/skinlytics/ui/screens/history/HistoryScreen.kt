package com.example.skinlytics.ui.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skinlytics.viewmodel.ScanViewModel
import com.example.skinlytics.model.ScanResult
import com.example.skinlytics.ui.theme.BrownRich
import com.example.skinlytics.ui.theme.BrownDeep
import com.example.skinlytics.ui.theme.SkinPeach
import com.example.skinlytics.ui.theme.SkinBeige
import com.example.skinlytics.ui.theme.SkinMedium
import com.example.skinlytics.ui.theme.SkinCream
import androidx.compose.foundation.clickable

@Composable
fun HistoryScreen(scanViewModel: ScanViewModel = viewModel(), onScanSelected: (ScanResult) -> Unit) {
    val scanResults = scanViewModel.allScanResults.collectAsState().value
    val skinGradient = Brush.verticalGradient(
        colors = listOf(SkinPeach, SkinBeige, SkinMedium, SkinCream)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(skinGradient)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text(
                text = "Scan History",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = BrownRich,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            if (scanResults.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No scan history yet.", color = BrownDeep, fontSize = 16.sp)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(scanResults) { result ->
                        HistoryItem(result = result, onClick = { onScanSelected(result) })
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryItem(result: ScanResult, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.96f)),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = result.prediction,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = BrownRich
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = result.about,
                fontSize = 14.sp,
                color = BrownDeep,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (result.severity.isNotBlank()) {
                Text(
                    text = "Severity: ${result.severity}",
                    fontSize = 13.sp,
                    color = Color(0xFFB8956A),
                    fontWeight = FontWeight.Medium
                )
            }
            if (result.common_symptoms.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Symptoms: ${result.common_symptoms.joinToString(", ")}",
                    fontSize = 13.sp,
                    color = BrownDeep
                )
            }
        }
    }
} 