package com.sanaa.tudee_assistant.presentation.design_system.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme

@Composable
fun NegativeButton(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit = {},
) {
    val backgroundModifier = when (enabled) {
        true -> Modifier.background(color = Theme.color.errorVariant)
        false -> Modifier.background(color = Theme.color.disable)
    }
    val contentColor = when (enabled) {
        true -> Theme.color.error
        false -> Theme.color.stroke
    }


    Box {
        PrimaryButton(
            label = label,
            enabled = enabled,
            isLoading = isLoading,
            onClick = onClick,
            backgroundModifier = backgroundModifier,
            contentColor = contentColor,
        )
    }
}


@Preview
@Composable
private fun NegativeButtonLightPreview(modifier: Modifier = Modifier) {
    TudeeTheme(isDarkTheme = false) {
        Column(verticalArrangement = Arrangement.spacedBy(Theme.dimension.small)) {
            NegativeButton(
                label = "Submit",
                enabled = true,
                isLoading = true,
                onClick = {}
            )

            NegativeButton(
                label = "Submit",
                enabled = true,
                isLoading = false,
                onClick = {}
            )

            NegativeButton(
                label = "Submit",
                enabled = false,
                isLoading = true,
                onClick = {}
            )

            NegativeButton(
                label = "Submit",
                enabled = false,
                isLoading = false,
                onClick = {}
            )

        }

    }

}


@Preview
@Composable
private fun NegativeButtonDarkPreview(modifier: Modifier = Modifier) {
    TudeeTheme(isDarkTheme = true) {
        Column(verticalArrangement = Arrangement.spacedBy(Theme.dimension.small)) {
            NegativeButton(
                label = "Submit",
                enabled = true,
                isLoading = true,
                onClick = {}
            )

            NegativeButton(
                label = "Submit",
                enabled = true,
                isLoading = false,
                onClick = {}
            )

            NegativeButton(
                label = "Submit",
                enabled = false,
                isLoading = true,
                onClick = {}
            )

            NegativeButton(
                label = "Submit",
                enabled = false,
                isLoading = false,
                onClick = {}
            )

        }

    }

}