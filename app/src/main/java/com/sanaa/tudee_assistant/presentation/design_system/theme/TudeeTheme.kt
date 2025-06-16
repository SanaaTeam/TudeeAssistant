package com.sanaa.tudee_assistant.presentation.design_system.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.sanaa.tudee_assistant.presentation.design_system.color.LocalTudeeColors
import com.sanaa.tudee_assistant.presentation.design_system.color.darkSchemaColors
import com.sanaa.tudee_assistant.presentation.design_system.color.lightSchemaColors
import com.sanaa.tudee_assistant.presentation.design_system.text_style.LocalTudeeTextStyle
import com.sanaa.tudee_assistant.presentation.design_system.text_style.defaultTextStyle

@Composable
fun TudeeTheme(
    isDarkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    val theme = if (isDarkTheme) darkSchemaColors else lightSchemaColors

    CompositionLocalProvider(
        LocalTudeeColors provides theme,
        LocalTudeeTextStyle provides defaultTextStyle
    ) {
        content()
        MaterialTheme.shapes.small
    }
}