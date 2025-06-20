package com.sanaa.tudee_assistant.domain

import kotlinx.coroutines.flow.Flow

interface ThemeManager {
    val isDarkTheme: Flow<Boolean>
    suspend fun setDarkTheme(enabled: Boolean)
}