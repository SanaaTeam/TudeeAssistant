package com.sanaa.tudee_assistant.presentation.designSystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.TudeeUiStatus

@Composable
fun StatusImage(imageSize: Dp, status: TudeeUiStatus) {
    Image(
        modifier = Modifier.size(imageSize),
        painter = painterResource(
            id = when (status) {
                TudeeUiStatus.GOOD -> R.drawable.good_statues
                TudeeUiStatus.OKAY -> R.drawable.okay_statues
                TudeeUiStatus.POOR -> R.drawable.poor_statues
                TudeeUiStatus.BAD -> R.drawable.bad_staues
            }
        ),
        contentDescription = null,
    )
}

@PreviewLightDark
@Composable
private fun Preview() {
    TudeeTheme(isSystemInDarkTheme()) {
        Column(
            modifier = Modifier.padding(Theme.dimension.medium),
            verticalArrangement = Arrangement.spacedBy(Theme.dimension.medium)
        ) {
            StatusImage(Theme.dimension.large, TudeeUiStatus.GOOD)
            StatusImage(Theme.dimension.large, TudeeUiStatus.OKAY)
            StatusImage(Theme.dimension.large, TudeeUiStatus.POOR)
            StatusImage(Theme.dimension.large, TudeeUiStatus.BAD)
        }
    }
}