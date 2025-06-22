package com.sanaa.tudee_assistant.presentation.design_system.color

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class TudeeColors(
    val primary: Color,
    val primaryVariant: Color,
    val primaryGradientStart: Color,
    val primaryGradientEnd: Color,
    val secondary: Color,

    val title: Color,
    val body: Color,
    val hint: Color,
    val stroke: Color,

    val surfaceLow: Color,
    val surface: Color,
    val surfaceHigh: Color,
    val onPrimary: Color,
    val onPrimaryCaption: Color,
    val onPrimaryCard: Color,
    val onPrimaryStroke: Color,
    val disable: Color,

    val pinkAccent: Color,
    val yellowAccent: Color,
    val yellowVariant: Color,
    val greenAccent: Color,
    val greenVariant: Color,
    val purpleAccent: Color,
    val purpleVariant: Color,
    val error: Color,
    val errorVariant: Color,
    val overlay: Color,
    val emojiTint: Color,
)

class ThemeController(isDark: Boolean) {
    var isDarkTheme by mutableStateOf(isDark)

    fun toggleTheme() {
        isDarkTheme = !isDarkTheme
    }
}

val LocalThemeController = staticCompositionLocalOf { ThemeController(false) }

val LocalTudeeColors = staticCompositionLocalOf { lightSchemaColors }

