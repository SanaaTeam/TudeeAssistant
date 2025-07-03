package com.sanaa.tudee_assistant.presentation.screen.home.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus

@Composable
fun RowScope.TaskCountByStatusCard(
    taskUiStatus: TaskUiStatus,
    count: Int,
    modifier: Modifier = Modifier,
) {
    val backgroundColor by animateColorAsState(
        targetValue = getTaskStatusBackgroundColor(taskUiStatus),
        label = "taskCountCardBackground"
    )

    Box(
        modifier
            .weight(1f)
            .clip(RoundedCornerShape(20.dp))
            .clipToBounds()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Theme.dimension.regular),
        ) {
            StatusImage(taskUiStatus)

            Text(
                modifier = Modifier.padding(top = Theme.dimension.extraSmall),
                text = count.toString(),
                color = Theme.color.onPrimary,
                style = Theme.textStyle.headline.medium
            )

            Text(
                text = getTaskStatusLabel(taskUiStatus),
                color = Theme.color.onPrimaryCaption,
                style = Theme.textStyle.label.small
            )
        }

        CardTopEndDecoration(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 40.dp, y = (-41).dp)
        )
    }
}

@Composable
private fun StatusImage(taskUiStatus: TaskUiStatus) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .border(
                1.dp,
                Color.White.copy(alpha = 0.12f),
                RoundedCornerShape(Theme.dimension.regular)
            )
            .clip(RoundedCornerShape(Theme.dimension.regular))
            .background(Color.White.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(Theme.dimension.large),
            painter = painterResource(
                id = when (taskUiStatus) {
                    TaskUiStatus.TODO -> R.drawable.status_view_to_do
                    TaskUiStatus.IN_PROGRESS -> R.drawable.status_view_in_progress
                    TaskUiStatus.DONE -> R.drawable.status_view_done
                }
            ),
            contentDescription = null,
        )
    }
}

@Composable
private fun CardTopEndDecoration(modifier: Modifier = Modifier) {
    Box(modifier = modifier.alpha(0.16f)) {
        Box(
            modifier = Modifier
                .size(78.dp)
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.87f),
                    shape = RoundedCornerShape(200.dp)
                ),
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(45.09.dp)
                .background(
                    color = Color.White.copy(alpha = 0.87f),
                    shape = CircleShape
                ),
        )

        Box(
            modifier = Modifier
                .padding(bottom = 3.9.dp, start = 11.7.dp)
                .align(Alignment.BottomStart)
                .size(7.8.dp)
                .background(
                    color = Color.White.copy(alpha = 0.87f),
                    shape = CircleShape
                ),
        )
    }
}

@Composable
fun getTaskStatusBackgroundColor(taskUiStatus: TaskUiStatus): Color = when (taskUiStatus) {
    TaskUiStatus.TODO -> Theme.color.purpleAccent
    TaskUiStatus.IN_PROGRESS -> Theme.color.yellowAccent
    TaskUiStatus.DONE -> Theme.color.greenAccent
}

@Composable
fun getTaskStatusLabel(taskUiStatus: TaskUiStatus): String = when (taskUiStatus) {
    TaskUiStatus.TODO -> stringResource(R.string.todo_task_status)
    TaskUiStatus.IN_PROGRESS -> stringResource(R.string.in_progress_task_status)
    TaskUiStatus.DONE -> stringResource(R.string.done_task_status)
}

@PreviewLightDark
@Composable
private fun Preview() {
    TudeeTheme(isSystemInDarkTheme()) {
        Row(
            Modifier
                .fillMaxSize()
                .background(Theme.color.surface)
                .padding(Theme.dimension.medium),
            horizontalArrangement = Arrangement.spacedBy(Theme.dimension.medium)
        ) {
            TaskCountByStatusCard(TaskUiStatus.DONE, 2)
            TaskCountByStatusCard(TaskUiStatus.IN_PROGRESS, 16)
            TaskCountByStatusCard(TaskUiStatus.TODO, 1)
        }
    }
}