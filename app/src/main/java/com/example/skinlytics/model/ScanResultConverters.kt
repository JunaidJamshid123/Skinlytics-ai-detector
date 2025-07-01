package com.example.skinlytics.model

import androidx.room.TypeConverter

class ScanResultConverters {
    @TypeConverter
    fun fromStringList(list: List<String>?): String = list?.joinToString("|;|") ?: ""

    @TypeConverter
    fun toStringList(data: String?): List<String> = data?.split("|;|")?.filter { it.isNotEmpty() } ?: emptyList()
} 