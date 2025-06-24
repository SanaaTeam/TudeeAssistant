package com.sanaa.tudee_assistant.presentation.designSystem.color

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

val LocalTudeeColors = staticCompositionLocalOf { lightSchemaColors }

