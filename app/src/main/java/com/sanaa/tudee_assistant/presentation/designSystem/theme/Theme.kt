package com.sanaa.tudee_assistant.presentation.designSystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.sanaa.tudee_assistant.presentation.designSystem.color.LocalTudeeColors
import com.sanaa.tudee_assistant.presentation.designSystem.color.TudeeColors
import com.sanaa.tudee_assistant.presentation.designSystem.dimension.LocalTudeeDimension
import com.sanaa.tudee_assistant.presentation.designSystem.dimension.TudeeDimension
import com.sanaa.tudee_assistant.presentation.designSystem.textStyle.LocalTudeeTextStyle
import com.sanaa.tudee_assistant.presentation.designSystem.textStyle.TudeeTextStyle

object Theme {
    val color: TudeeColors
        @Composable @ReadOnlyComposable get() = LocalTudeeColors.current

    val textStyle: TudeeTextStyle
        @Composable @ReadOnlyComposable get() = LocalTudeeTextStyle.current

    val dimension: TudeeDimension
        @Composable @ReadOnlyComposable get() = LocalTudeeDimension.current
}