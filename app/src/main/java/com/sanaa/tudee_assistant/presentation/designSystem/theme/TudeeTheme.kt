package com.sanaa.tudee_assistant.presentation.designSystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.sanaa.tudee_assistant.presentation.designSystem.color.LocalTudeeColors
import com.sanaa.tudee_assistant.presentation.designSystem.color.darkSchemaColors
import com.sanaa.tudee_assistant.presentation.designSystem.color.lightSchemaColors
import com.sanaa.tudee_assistant.presentation.designSystem.text_style.LocalTudeeTextStyle
import com.sanaa.tudee_assistant.presentation.designSystem.text_style.defaultTextStyle
import com.sanaa.tudee_assistant.presentation.design_system.color.ColorThemeController
import com.sanaa.tudee_assistant.presentation.design_system.color.LocalColorThemeController

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