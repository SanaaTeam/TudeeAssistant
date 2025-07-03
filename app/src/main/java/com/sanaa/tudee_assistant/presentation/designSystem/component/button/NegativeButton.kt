package com.sanaa.tudee_assistant.presentation.designSystem.component.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme

@Composable
fun NegativeButton(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit = {},
) {
    val animatedBackgroundColor by animateColorAsState(
        targetValue = if (enabled) Theme.color.errorVariant else Theme.color.disable,
        label = "NegativeButtonBackground"
    )

    val animatedContentColor by animateColorAsState(
        targetValue = if (enabled) Theme.color.error else Theme.color.stroke,
        label = "NegativeButtonContent"
    )

    val backgroundModifier = Modifier.background(color = animatedBackgroundColor)

    Box {
        PrimaryButton(
            label = label,
            enabled = enabled,
            isLoading = isLoading,
            onClick = onClick,
            backgroundModifier = backgroundModifier,
            contentColor = animatedContentColor,
            modifier = modifier
        )
    }
}

@PreviewLightDark
@Composable
private fun NegativeButtonDarkPreview(modifier: Modifier = Modifier) {
    TudeeTheme(isSystemInDarkTheme()) {
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