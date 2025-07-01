package com.example.skinlytics.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScanResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScanResult(result: ScanResult)

    @Query("SELECT * FROM scan_results ORDER BY id DESC")
    fun getAllScanResults(): Flow<List<ScanResult>>
} 