package com.example.skinlytics.model

interface SettingsRepository {
    fun getSettingsList(): List<String>
}

class DefaultSettingsRepository : SettingsRepository {
    override fun getSettingsList() = listOf("Notifications", "Dark Mode", "About App")
} 