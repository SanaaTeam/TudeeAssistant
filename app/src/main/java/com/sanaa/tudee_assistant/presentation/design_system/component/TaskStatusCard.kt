package com.sanaa.tudee_assistant.presentation.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus

@Composable
fun TaskStatusCard(
    modifier: Modifier = Modifier,
    taskUiStatus: TaskUiStatus
) {
    val backgroundColor = when (taskUiStatus) {
        TaskUiStatus.TODO -> Theme.color.yellowVariant
        TaskUiStatus.IN_PROGRESS -> Theme.color.purpleVariant
        TaskUiStatus.DONE -> Theme.color.greenVariant
    }
    val textColor = when (taskUiStatus) {
        TaskUiStatus.TODO -> Theme.color.yellowAccent
        TaskUiStatus.IN_PROGRESS -> Theme.color.purpleAccent
        TaskUiStatus.DONE -> Theme.color.greenVariant
    }

    Row(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(100.dp)
            )
            .padding(vertical = 6.dp, horizontal = Theme.dimension.regular),
        horizontalArrangement = Arrangement.spacedBy(Theme.dimension.extraSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(5.dp)
                .background(color = textColor, shape = CircleShape)
        )
        Text(
            text = when (taskUiStatus) {
                TaskUiStatus.TODO -> stringResource(R.string.todo_task_status)
                TaskUiStatus.IN_PROGRESS -> stringResource(R.string.in_progress_task_status)
                TaskUiStatus.DONE -> stringResource(R.string.done_task_status)
            },
            color = textColor,
            style = Theme.textStyle.label.small
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewTaskStatusCard() {
    TudeeTheme {
        TaskStatusCard(taskUiStatus = TaskUiStatus.TODO)
    }
}