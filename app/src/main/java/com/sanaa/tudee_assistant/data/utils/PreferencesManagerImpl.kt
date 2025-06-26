package com.sanaa.tudee_assistant.data.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class PreferencesManagerImpl(private val context: Context) : PreferencesManager {
    override val isFirstLaunch: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_FIRST_LAUNCH] ?: true
        }

    override suspend fun setOnboardingCompleted() {
        context.dataStore.edit { prefs ->
            prefs[IS_FIRST_LAUNCH] = false
        }
    }

    override val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[DARK_THEME_KEY] ?: false
        }

    override val lastSelectedTaskStatus: Flow<TaskUiStatus> = context.dataStore.data
        .map { preferences ->
            val status = preferences[LAST_SELECTED_TASK_SCREEN]
                ?: return@map TaskUiStatus.IN_PROGRESS

            when (status) {
                TODO -> TaskUiStatus.TODO
                DONE -> TaskUiStatus.DONE
                else -> TaskUiStatus.IN_PROGRESS
            }
        }

    override suspend fun changeTaskStatus(status: Task.TaskStatus) {
        context.dataStore.edit { prefs ->
            prefs[LAST_SELECTED_TASK_SCREEN] = status.toString()
        }
    }

    override suspend fun setDarkTheme(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DARK_THEME_KEY] = enabled
        }
    }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("storeData")
        val DARK_THEME_KEY = booleanPreferencesKey("is_dark_theme")
        val IS_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")
        val LAST_SELECTED_TASK_SCREEN = stringPreferencesKey("last_selected_task_screen")

        const val TODO = "TODO"
        const val IN_PROGRESS = "IN_PROGRESS"
        const val DONE = "DONE"
    }
}