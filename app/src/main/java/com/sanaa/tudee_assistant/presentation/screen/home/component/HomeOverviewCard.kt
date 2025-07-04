package com.sanaa.tudee_assistant.presentation.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.component.Slider
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.modifire.applyTaskCardAnimation
import com.sanaa.tudee_assistant.presentation.screen.home.HomeScreenUiState

@Composable
fun HomeOverviewCard(state: HomeScreenUiState) {
    Column(
        modifier = Modifier
            .padding(
                bottom = Theme.dimension.medium,
                start = Theme.dimension.medium,
                end = Theme.dimension.medium
            )
            .clip(RoundedCornerShape(Theme.dimension.medium))
            .background(Theme.color.surfaceHigh)
            .padding(top = Theme.dimension.small, bottom = Theme.dimension.regular)
    ) {
        TopDate(state)

        Slider(modifier = Modifier.padding(start = 12.dp), state = state.sliderUiState)

        Text(
            modifier = Modifier.padding(
                start = Theme.dimension.regular,
                top = Theme.dimension.small
            ),
            text = stringResource(R.string.overview),
            color = Theme.color.title,
            style = Theme.textStyle.title.large
        )
        TaskStatusCardsRow(state)
    }
}

@Composable
fun TaskStatusCardsRow(state: HomeScreenUiState) {
    val statuses = listOf(
        TaskUiStatus.DONE,
        TaskUiStatus.IN_PROGRESS,
        TaskUiStatus.TODO
    )

    Row(
        modifier = Modifier.padding(
            top = Theme.dimension.small,
            start = Theme.dimension.regular,
            end = Theme.dimension.regular
        ),
        horizontalArrangement = Arrangement.spacedBy(Theme.dimension.small)
    ) {
        statuses.forEachIndexed { index, status ->
            val count = state.tasks.count { it.status == status }

            TaskCountByStatusCard(
                count = count,
                taskUiStatus = status,
                modifier = Modifier
                    .weight(1f)
                    .applyTaskCardAnimation(delayMillis = index * 50)
            )
        }
    }
}