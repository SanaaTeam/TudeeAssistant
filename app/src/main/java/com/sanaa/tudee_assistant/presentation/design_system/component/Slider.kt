package com.sanaa.tudee_assistant.presentation.design_system.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.model.TudeeUiStatus
import com.sanaa.tudee_assistant.presentation.utils.DataProvider

@Composable
fun Slider(
    tasks: List<TaskUiState>,
    modifier: Modifier = Modifier,
) {
    val status = getSlideStatus(tasks)
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
                    text = when (status) {
                        TudeeUiStatus.GOOD -> stringResource(R.string.good_status_message_title)
                        TudeeUiStatus.OKAY -> stringResource(R.string.okay_status_message_title)
                        TudeeUiStatus.POOR -> stringResource(R.string.poor_status_message_title)
                        TudeeUiStatus.BAD -> stringResource(R.string.bad_status_message_title)
                    },
                    style = Theme.textStyle.title.small,
                    color = Theme.color.title
                )

                StatusImage(20.dp, status)
            }

            Text(
                text = when (status) {
                    TudeeUiStatus.GOOD -> {
                        stringResource(R.string.good_status_message)
                    }

                    TudeeUiStatus.OKAY -> {
                        stringResource(R.string.okay_status_message)
                            .replace(
                                "*",
                                tasks.filter { it.status == TaskUiStatus.DONE }.size.toString()
                            )
                            .replace("#", tasks.size.toString())
                    }

                    TudeeUiStatus.POOR -> {
                        stringResource(R.string.poor_status_message)
                    }

                    TudeeUiStatus.BAD -> {
                        stringResource(R.string.bad_status_message)
                    }
                },
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
                    id = when (status) {
                        TudeeUiStatus.OKAY -> R.drawable.robot1
                        TudeeUiStatus.GOOD -> R.drawable.robot2
                        TudeeUiStatus.BAD -> R.drawable.robot3
                        TudeeUiStatus.POOR -> R.drawable.robot1
                    }
                ),
                contentDescription = null,
            )
        }
    }
}

fun getSlideStatus(tasks: List<TaskUiState>): TudeeUiStatus {
    val allTasksDone = tasks.all { it.status == TaskUiStatus.DONE }
    val allTasksNotFinished = tasks.size > 1 && !tasks.any { it.status == TaskUiStatus.DONE }
    return if (tasks.isEmpty())
        TudeeUiStatus.POOR
    else if (allTasksNotFinished)
        TudeeUiStatus.BAD
    else if (allTasksDone)
        TudeeUiStatus.GOOD
    else
        TudeeUiStatus.OKAY
}

@Preview
@Composable
private fun Preview() {
    val doneTask = DataProvider.getTasksSample()[0].copy(status = TaskUiStatus.DONE)
    TudeeTheme(isDark = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surface)
                .padding(Theme.dimension.medium),
            verticalArrangement = Arrangement.spacedBy(21.dp)
        ) {
            Slider(
                listOf(
                    doneTask.copy(status = TaskUiStatus.IN_PROGRESS),
                    doneTask.copy(status = TaskUiStatus.TODO),
                    doneTask.copy(),
                    doneTask.copy(),
                )
            )
            Slider(
                listOf(
                    doneTask.copy(),
                    doneTask.copy(),
                    doneTask.copy(),
                    doneTask.copy(),
                )
            )
            Slider(
                listOf(
                    doneTask.copy(status = TaskUiStatus.IN_PROGRESS),
                    doneTask.copy(status = TaskUiStatus.TODO),
                    doneTask.copy(status = TaskUiStatus.IN_PROGRESS),
                    doneTask.copy(status = TaskUiStatus.TODO),
                )
            )
            Slider(emptyList())
        }
    }
}