package com.example.skinlytics.model

data class ScanResult(
    val about: String = "",
    val common_symptoms: List<String> = emptyList(),
    val disclaimer: String = "",
    val prediction: String = "",
    val severity: String = "",
    val treatment_recommendations: List<String> = emptyList()
) 