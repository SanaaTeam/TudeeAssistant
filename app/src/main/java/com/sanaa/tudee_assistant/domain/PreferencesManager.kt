package com.sanaa.tudee_assistant.domain

import kotlinx.coroutines.flow.Flow

interface PreferencesManager {
    val isFirstLaunch: Flow<Boolean>
    suspend fun setOnboardingCompleted()
    val isDarkTheme: Flow<Boolean>
    suspend fun setDarkTheme(enabled: Boolean)
}