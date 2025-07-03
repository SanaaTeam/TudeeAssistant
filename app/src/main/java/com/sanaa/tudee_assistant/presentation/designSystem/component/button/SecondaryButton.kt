package com.sanaa.tudee_assistant.presentation.designSystem.component.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
    val animatedBorderColor by animateColorAsState(
        targetValue = if (enabled) Theme.color.stroke else Theme.color.disable,
        label = "animatedBorderColor"
    )

    val animatedContentColor by animateColorAsState(
        targetValue = if (enabled) Theme.color.primary else Theme.color.stroke,
        label = "animatedContentColor"
    )

    val verticalPadding = if (isLoading) Theme.dimension.medium else 18.dp

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
            .border(
                width = 1.dp,
                color = animatedBorderColor,
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
            contentColor = animatedContentColor
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
