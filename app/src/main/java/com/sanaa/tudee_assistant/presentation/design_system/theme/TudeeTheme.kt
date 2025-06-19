package com.sanaa.tudee_assistant.presentation.design_system.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sanaa.tudee_assistant.data.utils.ThemeManager
import com.sanaa.tudee_assistant.presentation.design_system.color.LocalTudeeColors
import com.sanaa.tudee_assistant.presentation.design_system.color.darkSchemaColors
import com.sanaa.tudee_assistant.presentation.design_system.color.lightSchemaColors
import com.sanaa.tudee_assistant.presentation.design_system.text_style.LocalTudeeTextStyle
import com.sanaa.tudee_assistant.presentation.design_system.text_style.defaultTextStyle
import org.koin.compose.koinInject

@Composable
fun TudeeTheme(
    isDark: Boolean,
    content: @Composable () -> Unit,
) {
    val theme = if (isDark) darkSchemaColors else lightSchemaColors

    CompositionLocalProvider(
        LocalTudeeColors provides theme,
        LocalTudeeTextStyle provides defaultTextStyle
    ) {
        content()
        MaterialTheme.shapes.small
    }
}