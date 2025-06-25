package com.example.skinlytics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _splashFinished = MutableStateFlow(false)
    val splashFinished: StateFlow<Boolean> = _splashFinished

    fun startSplashTimer() {
        viewModelScope.launch {
            delay(4000)
            _splashFinished.value = true
        }
    }
} 