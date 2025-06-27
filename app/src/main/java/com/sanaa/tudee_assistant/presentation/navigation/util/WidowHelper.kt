package com.sanaa.tudee_assistant.presentation.navigation.util

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

object WidowHelper {
    @Composable
    fun SystemNavigateBarEffect(
        color: Color,
    ) {
        val activity = LocalActivity.current ?: return

        SideEffect {
            val window = activity.window
            window.navigationBarColor = color.toArgb()
        }
    }
}