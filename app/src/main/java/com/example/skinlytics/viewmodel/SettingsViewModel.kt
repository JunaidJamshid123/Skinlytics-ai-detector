package com.example.skinlytics.viewmodel

import androidx.lifecycle.ViewModel
import com.example.skinlytics.model.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {
    private val _settingsList = MutableStateFlow(repository.getSettingsList())
    val settingsList: StateFlow<List<String>> = _settingsList
} 