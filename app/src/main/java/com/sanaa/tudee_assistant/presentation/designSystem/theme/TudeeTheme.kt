package com.sanaa.tudee_assistant.presentation.designSystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.sanaa.tudee_assistant.presentation.designSystem.color.LocalTudeeColors
import com.sanaa.tudee_assistant.presentation.designSystem.color.darkSchemaColors
import com.sanaa.tudee_assistant.presentation.designSystem.color.lightSchemaColors
import com.sanaa.tudee_assistant.presentation.designSystem.text_style.LocalTudeeTextStyle
import com.sanaa.tudee_assistant.presentation.designSystem.text_style.defaultTextStyle

@Composable
fun TudeeTheme(
    isDark: Boolean = false,
    content: @Composable () -> Unit,
) {
    val theme = if (isDark) darkSchemaColors else lightSchemaColors

    CompositionLocalProvider(
        LocalTudeeColors provides theme,
        LocalTudeeTextStyle provides defaultTextStyle
    ) {
        content()
    }
}