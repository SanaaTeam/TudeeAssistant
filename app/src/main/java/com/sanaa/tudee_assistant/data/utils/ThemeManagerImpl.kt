package com.sanaa.tudee_assistant.data.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.sanaa.tudee_assistant.domain.ThemeManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class ThemeManagerImpl(private val context: Context) : ThemeManager{

     companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("storeData")
        val DARK_THEME_KEY = booleanPreferencesKey("is_dark_theme")
    }

    override val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .map { prefrences->
            prefrences[DARK_THEME_KEY]?:false
        }


    override suspend fun setDarkTheme(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DARK_THEME_KEY] = enabled
        }
    }


}