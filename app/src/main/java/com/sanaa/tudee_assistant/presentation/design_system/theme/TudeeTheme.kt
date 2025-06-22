package com.sanaa.tudee_assistant.presentation.design_system.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.sanaa.tudee_assistant.presentation.design_system.color.ColorThemeController
import com.sanaa.tudee_assistant.presentation.design_system.color.LocalColorThemeController
import com.sanaa.tudee_assistant.presentation.design_system.color.LocalTudeeColors
import com.sanaa.tudee_assistant.presentation.design_system.color.darkSchemaColors
import com.sanaa.tudee_assistant.presentation.design_system.color.lightSchemaColors
import com.sanaa.tudee_assistant.presentation.design_system.text_style.LocalTudeeTextStyle
import com.sanaa.tudee_assistant.presentation.design_system.text_style.defaultTextStyle

@Composable
fun TudeeTheme(
    isDark: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorThemeController = remember { ColorThemeController(isDark) }
    val theme = if (colorThemeController.isDarkTheme) darkSchemaColors else lightSchemaColors

    CompositionLocalProvider(
        LocalColorThemeController provides colorThemeController,
        LocalTudeeColors provides theme,
        LocalTudeeTextStyle provides defaultTextStyle
    ) {
        content()
    }
}