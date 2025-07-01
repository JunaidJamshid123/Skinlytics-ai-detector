package com.example.skinlytics.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ScanResult::class], version = 1, exportSchema = false)
@TypeConverters(ScanResultConverters::class)
abstract class SkinlyticsDatabase : RoomDatabase() {
    abstract fun scanResultDao(): ScanResultDao
} 