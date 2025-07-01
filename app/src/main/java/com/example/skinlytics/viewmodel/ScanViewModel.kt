package com.example.skinlytics.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skinlytics.model.ScanRepository
import com.example.skinlytics.model.ScanResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ScanUiState {
    object Idle : ScanUiState()
    object Loading : ScanUiState()
    data class Success(val result: ScanResult) : ScanUiState()
    data class Error(val message: String) : ScanUiState()
}

class ScanViewModel(
    private val repository: ScanRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<ScanUiState>(ScanUiState.Idle)
    val uiState: StateFlow<ScanUiState> = _uiState

    fun uploadImageAndAnalyze(context: Context, imageUri: Uri?) {
        if (imageUri == null) {
            _uiState.value = ScanUiState.Error("No image selected")
            return
        }
        _uiState.value = ScanUiState.Loading
        viewModelScope.launch {
            try {
                val result = repository.uploadImageAndGetResult(context, imageUri)
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