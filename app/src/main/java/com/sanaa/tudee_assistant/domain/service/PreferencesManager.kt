package com.sanaa.tudee_assistant.domain.service

import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import kotlinx.coroutines.flow.Flow

interface PreferencesManager {
    val isFirstLaunch: Flow<Boolean>
    val isDarkTheme: Flow<Boolean>

    suspend fun setOnboardingCompleted()
    suspend fun setDarkTheme(enabled: Boolean)
    suspend fun changeTaskStatus(status: Task.TaskStatus)
}