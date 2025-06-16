package com.sanaa.tudee_assistant.presentation.design_system.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.TaskStatus

@Composable
fun RowScope.TaskCountByStatusCard(
    taskStatus: TaskStatus,
    count: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .weight(1f)
            .clip(RoundedCornerShape(20.dp))
            .clipToBounds()
            .background(
                when (taskStatus) {
                    TaskStatus.TODO -> Theme.color.purpleAccent
                    TaskStatus.IN_PROGRESS -> Theme.color.yellowAccent
                    TaskStatus.DONE -> Theme.color.greenAccent
                }
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
        ) {
            StatusImage(taskStatus)

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = count.toString(),
                color = Theme.color.onPrimary,
                style = Theme.textStyle.headline.medium
            )

            Text(
                modifier = Modifier,
                text = when (taskStatus) {
                    TaskStatus.TODO -> stringResource(R.string.todo)
                    TaskStatus.IN_PROGRESS -> stringResource(
                        R.string.in_progress_task
                    )

                    TaskStatus.DONE -> stringResource(R.string.done)
                },
                color = Theme.color.onPrimaryCaption,
                style = Theme.textStyle.label.small
            )
        }

        CardDecoration(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 40.dp, y = (-41).dp)
        )
    }
}

@Composable
private fun StatusImage(taskStatus: TaskStatus) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(
                id = when (taskStatus) {
                    TaskStatus.TODO -> R.drawable.status_view_to_do
                    TaskStatus.IN_PROGRESS -> R.drawable.status_view_in_progress
                    TaskStatus.DONE -> R.drawable.status_view_done
                }
            ),
            contentDescription = null,
        )
    }
}

@Composable
private fun CardDecoration(modifier: Modifier = Modifier) {
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


@Preview
@Composable
private fun Preview() {
    TudeeTheme(isDarkTheme = false) {
        Row(
            Modifier
                .fillMaxSize()
                .background(Theme.color.surface)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TaskCountByStatusCard(TaskStatus.DONE, 2)
            TaskCountByStatusCard(TaskStatus.IN_PROGRESS, 16)
            TaskCountByStatusCard(TaskStatus.TODO, 1)
        }
    }
}