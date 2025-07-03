package com.sanaa.tudee_assistant.presentation.designSystem.component.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.sanaa.tudee_assistant.presentation.designSystem.component.button.utils.ButtonContent
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme

@Composable
fun NegativeTextButton(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit,
) {
    val animatedContentColor by animateColorAsState(
        targetValue = if(enabled)Theme.color.error else Theme.color.stroke,
        label ="animatedContentColor"
    )
    Row(
        modifier
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        horizontalArrangement = Arrangement.spacedBy(Theme.dimension.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ButtonContent(
            label = label,
            enabled = enabled,
            isLoading = isLoading,
            contentColor = animatedContentColor
        )
    }
}

@PreviewLightDark
@Composable
private fun NegativeTextButtonDarkPreview(modifier: Modifier = Modifier) {
    TudeeTheme(isSystemInDarkTheme()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Theme.dimension.small)
        ) {
            NegativeTextButton(
                label = "Submit",
                enabled = true,
                isLoading = true,
                onClick = {}
            )

            NegativeTextButton(
                label = "Submit",
                enabled = true,
                isLoading = false,
                onClick = {}
            )

            NegativeTextButton(
                label = "Submit",
                enabled = false,
                isLoading = false,
                onClick = {}
            )
            NegativeTextButton(
                label = "Submit",
                enabled = false,
                isLoading = true,
                onClick = {}
            )
        }
    }
}