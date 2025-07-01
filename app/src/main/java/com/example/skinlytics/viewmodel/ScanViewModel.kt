package com.example.skinlytics.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.skinlytics.model.ScanRepository
import com.example.skinlytics.model.ScanResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.stateIn

sealed class ScanUiState {
    object Idle : ScanUiState()
    object Loading : ScanUiState()
    data class Success(val result: ScanResult) : ScanUiState()
    data class Error(val message: String) : ScanUiState()
}

class ScanViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ScanRepository(application)
    private val _uiState = MutableStateFlow<ScanUiState>(ScanUiState.Idle)
    val uiState: StateFlow<ScanUiState> = _uiState

    val allScanResults: StateFlow<List<ScanResult>> = repository.getAllScanResults()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun uploadImageAndAnalyze(context: Context, imageUri: Uri?) {
        if (imageUri == null) {
            _uiState.value = ScanUiState.Error("No image selected")
            return
        }
        _uiState.value = ScanUiState.Loading
        viewModelScope.launch {
            try {
                val result = repository.uploadImageAndGetResult(context, imageUri)
                repository.insertScanResult(result)
                _uiState.value = ScanUiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = ScanUiState.Error(e.toString())
                e.printStackTrace()
            }
        }
    }

    fun reset() {
        _uiState.value = ScanUiState.Idle
    }
} 