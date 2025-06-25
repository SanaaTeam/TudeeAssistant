package com.sanaa.tudee_assistant.presentation.designSystem.component.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sanaa.tudee_assistant.presentation.designSystem.component.button.utils.ButtonContent
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme


@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        horizontalArrangement = Arrangement.spacedBy(Theme.dimension.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ButtonContent(label, enabled, isLoading)
    }
}


@Preview
@Composable
private fun TextButtonLightPreview(modifier: Modifier = Modifier) {
    TudeeTheme(false) {
        Column(verticalArrangement = Arrangement.spacedBy(Theme.dimension.small)) {
            TextButton(
                label = "Cancel",
                enabled = true,
                isLoading = true,
                onClick = {}
            )

            TextButton(
                label = "Cancel",
                enabled = true,
                isLoading = false,
                onClick = {}
            )

            TextButton(
                label = "Cancel",
                enabled = false,
                isLoading = true,
                onClick = {}
            )
            TextButton(
                label = "Cancel",
                enabled = false,
                isLoading = false,
                onClick = {}
            )


        }
    }

}


@Preview
@Composable
private fun TextButtonDarkPreview(modifier: Modifier = Modifier) {
    TudeeTheme(true) {
        Column(verticalArrangement = Arrangement.spacedBy(Theme.dimension.small)) {
            TextButton(
                label = "Cancel",
                enabled = true,
                isLoading = true,
                onClick = {}
            )

            TextButton(
                label = "Cancel",
                enabled = true,
                isLoading = false,
                onClick = {}
            )

            TextButton(
                label = "Cancel",
                enabled = false,
                isLoading = true,
                onClick = {}
            )
            TextButton(
                label = "Cancel",
                enabled = false,
                isLoading = false,
                onClick = {}
            )


        }
    }

}

