package com.sanaa.tudee_assistant.domain.service

import com.sanaa.tudee_assistant.domain.entity.Task
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import kotlinx.coroutines.flow.Flow

interface PreferencesManager {
    val isFirstLaunch: Flow<Boolean>
    val isDarkTheme: Flow<Boolean>
    val lastSelectedTaskStatus: Flow<TaskUiStatus>

    suspend fun setOnboardingCompleted()
    suspend fun setDarkTheme(enabled: Boolean)
    suspend fun changeTaskStatus(status: Task.TaskStatus)
}