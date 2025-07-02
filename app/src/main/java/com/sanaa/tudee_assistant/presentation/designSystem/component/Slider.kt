package com.sanaa.tudee_assistant.presentation.designSystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.model.TudeeUiStatus
import com.sanaa.tudee_assistant.presentation.screen.home.SliderUiState
import com.sanaa.tudee_assistant.presentation.utils.DataProvider

@Composable
fun Slider(
    state: SliderUiState,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Theme.dimension.regular)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Theme.dimension.small),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Theme.dimension.small)
            ) {
                Text(
                    text = state.title,
                    style = Theme.textStyle.title.small,
                    color = Theme.color.title
                )

                StatusImage(20.dp, state.status)
            }

            Text(
                modifier = Modifier.fillMaxWidth(0.8f),
                text = state.message,
                style = Theme.textStyle.body.small,
                color = Theme.color.body
            )
        }

        Box(
            modifier = Modifier
                .width(76.dp)
                .height(92.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .size(76.dp),
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 6.dp, start = 6.dp)
                        .size(64.75.dp)
                        .clip(CircleShape)
                        .background(Theme.color.primary.copy(alpha = 0.16f))
                        .align(Alignment.BottomStart),
                )
            }

            Image(
                modifier = Modifier
                    .padding(start = 3.dp)
                    .width(61.dp)
                    .height(92.dp),
                painter = painterResource(
                    id = state.robotImageRes
                ),
                contentDescription = null,
            )
        }
    }
}


@PreviewLightDark
@Composable
private fun Preview() {
    TudeeTheme(isDark = isSystemInDarkTheme()) {
        val previewStates = listOf(
            SliderUiState(
                title = stringResource(R.string.good_status_message_title),
                message = stringResource(R.string.good_status_message),
                robotImageRes = R.drawable.robot2,
                status = TudeeUiStatus.GOOD
            ),
            SliderUiState(
                title = stringResource(R.string.okay_status_message_title),
                message = stringResource(R.string.okay_status_message),
                robotImageRes = R.drawable.robot1,
                status = TudeeUiStatus.OKAY
            ),
            SliderUiState(
                title = stringResource(R.string.poor_status_message_title),
                message = stringResource(R.string.poor_status_message),
                robotImageRes = R.drawable.robot1,
                status = TudeeUiStatus.POOR
            ),
            SliderUiState(
                title = stringResource(R.string.bad_status_message_title),
                message = stringResource(R.string.bad_status_message),
                robotImageRes = R.drawable.robot3,
                status = TudeeUiStatus.BAD
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surface)
                .padding(Theme.dimension.medium),
            verticalArrangement = Arrangement.spacedBy(21.dp)
        ) {
            previewStates.forEach { state ->
                Slider(state = state)
            }
        }
    }
}