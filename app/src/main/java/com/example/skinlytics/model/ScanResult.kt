package com.example.skinlytics.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "scan_results")
@TypeConverters(ScanResultConverters::class)
data class ScanResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val about: String = "",
    val common_symptoms: List<String> = emptyList(),
    val disclaimer: String = "",
    val prediction: String = "",
    val severity: String = "",
    val treatment_recommendations: List<String> = emptyList()
) 