package com.sanaa.tudee_assistant.presentation.design_system.button.utils

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme

@Composable
fun ButtonContent(
    label: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    contentColor: Color =when (enabled) {
        true -> Theme.color.primary
        false -> Theme.color.disable
    },

) {

    Text(
        text = label,
        style = Theme.textStyle.label.large.copy(color = contentColor)
    )
    if (isLoading) {
        SpinnerIcon(
            tint = contentColor
        )
    }
}