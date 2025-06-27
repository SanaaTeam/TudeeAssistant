package com.sanaa.tudee_assistant.presentation.designSystem.component.button

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.presentation.designSystem.component.button.utils.ButtonContent
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit = {},
) {
    val borderColor = when (enabled) {
        true -> Theme.color.stroke
        false -> Theme.color.disable
    }

    val contentColor = when (enabled) {
        true -> Theme.color.primary
        false -> Theme.color.stroke
    }

    val verticalPadding = when (isLoading) {
        true -> Theme.dimension.medium
        false -> 18.dp
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(100.dp)
            )
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
            enabled = enabled,
            contentColor = contentColor
        )
    }
}

@PreviewLightDark
@Composable
private fun SecondaryButtonDarkPreview(modifier: Modifier = Modifier) {
    TudeeTheme(isSystemInDarkTheme()) {
        Column(verticalArrangement = Arrangement.spacedBy(Theme.dimension.small)) {
            SecondaryButton(
                label = "Submit",
                enabled = true,
                isLoading = true,
                onClick = { }
            )

            SecondaryButton(
                label = "Submit",
                enabled = true,
                isLoading = false,
                onClick = { }
            )

            SecondaryButton(
                label = "Submit",
                enabled = false,
                isLoading = false,
                onClick = { }
            )

            SecondaryButton(
                label = "Submit",
                enabled = false,
                isLoading = true,
                onClick = { }
            )
        }
    }
}
