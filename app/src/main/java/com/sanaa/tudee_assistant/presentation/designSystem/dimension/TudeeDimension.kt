package com.sanaa.tudee_assistant.presentation.designSystem.dimension

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

data class TudeeDimension(
    val extraSmall: Dp,
    val small: Dp,
    val regular: Dp,
    val medium: Dp,
    val large: Dp,
    val extraLarge: Dp,
)

val LocalTudeeDimension = staticCompositionLocalOf { defaultDimension }
