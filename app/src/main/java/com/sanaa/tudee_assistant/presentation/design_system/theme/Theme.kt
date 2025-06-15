package com.sanaa.tudee_assistant.presentation.design_system.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.sanaa.tudee_assistant.presentation.design_system.color.LocalTudeeColors
import com.sanaa.tudee_assistant.presentation.design_system.color.TudeeColors
import com.sanaa.tudee_assistant.presentation.design_system.dimension.LocalTudeeDimension
import com.sanaa.tudee_assistant.presentation.design_system.dimension.TudeeDimension
import com.sanaa.tudee_assistant.presentation.design_system.text_style.LocalTudeeTextStyle
import com.sanaa.tudee_assistant.presentation.design_system.text_style.TudeeTextStyle

object Theme {
    val color: TudeeColors
        @Composable @ReadOnlyComposable get() = LocalTudeeColors.current

    val textStyle: TudeeTextStyle
        @Composable @ReadOnlyComposable get() = LocalTudeeTextStyle.current

    val dimension: TudeeDimension
        @Composable @ReadOnlyComposable get() = LocalTudeeDimension.current
}