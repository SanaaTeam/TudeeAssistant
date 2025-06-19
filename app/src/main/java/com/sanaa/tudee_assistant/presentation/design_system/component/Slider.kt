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
import com.sanaa.tudee_assistant.presentation.model.TaskPriority
import com.sanaa.tudee_assistant.presentation.model.TaskStatus
import com.sanaa.tudee_assistant.presentation.model.TudeeStatus
import com.sanaa.tudee_assistant.presentation.state.CategoryTaskState

@Composable
fun Slider(
    tasks: List<CategoryTaskState>,
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
                        TudeeStatus.GOOD -> stringResource(R.string.good_status_message_title)
                        TudeeStatus.OKAY -> stringResource(R.string.okay_status_message_title)
                        TudeeStatus.POOR -> stringResource(R.string.poor_status_message_title)
                        TudeeStatus.BAD -> stringResource(R.string.bad_status_message_title)
                    },
                    style = Theme.textStyle.title.small,
                    color = Theme.color.title
                )

                StatusImage(20.dp, status)
            }

            Text(
                text = when (status) {
                    TudeeStatus.GOOD -> {
                        stringResource(R.string.good_status_message)
                    }

                    TudeeStatus.OKAY -> {
                        stringResource(R.string.okay_status_message)
                            .replace(
                                "*",
                                tasks.filter { it.status == TaskStatus.DONE }.size.toString()
                            )
                            .replace("#", tasks.size.toString())
                    }

                    TudeeStatus.POOR -> {
                        stringResource(R.string.poor_status_message)
                    }

                    TudeeStatus.BAD -> {
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
                        TudeeStatus.OKAY -> R.drawable.robot1
                        TudeeStatus.GOOD -> R.drawable.robot2
                        TudeeStatus.BAD -> R.drawable.robot3
                        TudeeStatus.POOR -> R.drawable.robot1
                    }
                ),
                contentDescription = null,
            )
        }
    }
}

fun getSlideStatus(tasks: List<CategoryTaskState>): TudeeStatus {
    val allTasksDone = tasks.all { it.status == TaskStatus.DONE }
    val allTasksNotFinished = tasks.size > 1 && !tasks.any { it.status == TaskStatus.DONE }
    return if (tasks.isEmpty())
        TudeeStatus.POOR
    else if (allTasksNotFinished)
        TudeeStatus.BAD
    else if (allTasksDone)
        TudeeStatus.GOOD
    else
        TudeeStatus.OKAY
}

@Preview
@Composable
private fun Preview() {
    val doneTask = CategoryTaskState(
        icon = painterResource(R.drawable.birthday_cake),
        title = "Organize Study Desk",
        date = "12-03-2025",
        priority = TaskPriority.LOW,
        status = TaskStatus.DONE,
    )
    TudeeTheme(isDarkTheme = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surface)
                .padding(Theme.dimension.medium),
            verticalArrangement = Arrangement.spacedBy(21.dp)
        ) {
            Slider(
                listOf(
                    doneTask.copy(status = TaskStatus.IN_PROGRESS),
                    doneTask.copy(status = TaskStatus.TODO),
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
                    doneTask.copy(status = TaskStatus.IN_PROGRESS),
                    doneTask.copy(status = TaskStatus.TODO),
                    doneTask.copy(status = TaskStatus.IN_PROGRESS),
                    doneTask.copy(status = TaskStatus.TODO),
                )
            )
            Slider(emptyList())
        }
    }
}