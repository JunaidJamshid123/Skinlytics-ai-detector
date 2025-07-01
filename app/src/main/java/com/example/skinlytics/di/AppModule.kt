package com.example.skinlytics.di

import com.example.skinlytics.model.DefaultProfileRepository
import com.example.skinlytics.model.DefaultSettingsRepository
import com.example.skinlytics.model.ProfileRepository
import com.example.skinlytics.model.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideProfileRepository(): ProfileRepository = DefaultProfileRepository()

    @Provides
    @Singleton
    fun provideSettingsRepository(): SettingsRepository = DefaultSettingsRepository()
} 