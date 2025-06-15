package com.sanaa.tudee_assistant.presentation.component.button.utils

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme

@Composable
fun ButtonContent(
    label: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    style:TextStyle = Theme.textStyle.label.large
        .copy(
            color = when (enabled) {
                true -> Theme.color.primary
                false -> Theme.color.disable
            }
        ),
    spinnerTint: Color = when (enabled) {
        true -> Theme.color.primary
        false -> Theme.color.disable
    }
) {

    Text(
        text = label,
        style = style
    )
    if (isLoading) {
        SpinnerIcon(
            tint = spinnerTint
        )
    }
}