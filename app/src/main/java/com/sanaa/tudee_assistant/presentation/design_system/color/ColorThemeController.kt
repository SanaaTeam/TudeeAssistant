package com.sanaa.tudee_assistant.presentation.design_system.color

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf


class ColorThemeController(isDark: Boolean) {
    var isDarkTheme by mutableStateOf(isDark)

    fun toggleTheme() {
        isDarkTheme = !isDarkTheme
    }
}

val LocalColorThemeController = staticCompositionLocalOf { ColorThemeController(false) }