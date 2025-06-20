package com.sanaa.tudee_assistant.presentation.design_system.theme

import android.util.Log
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
    isDark: Boolean = false,
    content: @Composable () -> Unit,
) {
    Log.d("isDark Test", "TudeeTheme: is dark value is : $isDark ")

    val theme = if (isDark) darkSchemaColors else lightSchemaColors

    CompositionLocalProvider(
        LocalTudeeColors provides theme,
        LocalTudeeTextStyle provides defaultTextStyle
    ) {
        content()
        MaterialTheme.shapes.small
    }
}