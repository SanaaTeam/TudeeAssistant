package com.sanaa.tudee_assistant.presentation.designSystem.component.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.presentation.designSystem.component.button.utils.ButtonContent
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit = {},
    backgroundModifier: Modifier = when (enabled) {
        true -> Modifier.background(
            brush = Theme.color.primaryGradient
        )
        false -> Modifier.background(color = Theme.color.disable)
    },
    contentColor: Color = when (enabled) {
        true -> Theme.color.onPrimary
        false -> Theme.color.stroke
    },
) {

    val verticalPadding = if (isLoading) Theme.dimension.medium else 18.dp
    val animatedContentColor by animateColorAsState(
        targetValue = contentColor,
        label = "primaryButtonTextColor"
    )

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
            .then(backgroundModifier)
            .padding(horizontal = Theme.dimension.large, vertical = verticalPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            Theme.dimension.small,
            Alignment.CenterHorizontally
        )
    ) {


        ButtonContent(
            label = label,
            isLoading = isLoading,
            contentColor = animatedContentColor
        )

    }
}

@PreviewLightDark
@Composable
private fun PrimaryButtonDarkPreview(modifier: Modifier = Modifier) {
    TudeeTheme(isSystemInDarkTheme()) {
        Column {
            Box(modifier.padding(top = Theme.dimension.small, start = Theme.dimension.small)) {
                PrimaryButton(
                    label = "Submit",
                    isLoading = true,
                    enabled = true,
                    onClick = {},
                )
            }
            Box(modifier.padding(top = Theme.dimension.small, start = Theme.dimension.small)) {
                PrimaryButton(
                    label = "Submit",
                    isLoading = true,
                    enabled = false,
                    onClick = {},
                )
            }
            Box(modifier.padding(top = Theme.dimension.small, start = Theme.dimension.small)) {
                PrimaryButton(
                    label = "Submit",
                    isLoading = false,
                    enabled = true,
                    onClick = {},
                )
            }
            Box(modifier.padding(top = Theme.dimension.small, start = Theme.dimension.small)) {
                PrimaryButton(
                    label = "Submit",
                    isLoading = false,
                    enabled = false,
                    onClick = {},
                )
            }

        }
    }

}

