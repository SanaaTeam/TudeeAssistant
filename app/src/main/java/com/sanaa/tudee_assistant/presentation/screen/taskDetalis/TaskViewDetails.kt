package com.sanaa.tudee_assistant.presentation.screen.taskDetalis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.component.BaseBottomSheet
import com.sanaa.tudee_assistant.presentation.design_system.component.CategoryImageContainer
import com.sanaa.tudee_assistant.presentation.design_system.component.PriorityTag
import com.sanaa.tudee_assistant.presentation.design_system.component.TaskStatusCard
import com.sanaa.tudee_assistant.presentation.design_system.component.button.SecondaryButton
import com.sanaa.tudee_assistant.presentation.design_system.component.button.SecondaryIconButton
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.state.TaskUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskViewDetails(
    task: TaskUiState,
    onDismiss: () -> Unit,
    onEditClick: (TaskUiState) -> Unit,
    onMoveToClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val changeStatusTo = when (task.status) {
        TaskUiStatus.TODO -> stringResource(R.string.mark_as_in_progress)
        TaskUiStatus.IN_PROGRESS -> stringResource(R.string.mark_as_done)
        TaskUiStatus.DONE -> null
    }

    BaseBottomSheet(
        onDismiss = onDismiss,
        content = {
            Column(
                modifier = modifier
                    .padding(
                        bottom = Theme.dimension.large,
                        start = Theme.dimension.medium,
                        end = Theme.dimension.medium,
                    )
            ) {
                Text(
                    text = stringResource(R.string.task_details),
                    modifier = Modifier.padding(bottom = Theme.dimension.regular),
                    style = Theme.textStyle.title.large,
                    color = Theme.color.title,
                )
                CategoryImageContainer(
                    painter = painterResource(R.drawable.education_cat)
                )
                Column(
                    modifier = Modifier.padding(top = Theme.dimension.small),
                    verticalArrangement = Arrangement.spacedBy(Theme.dimension.small)
                ) {
                    Text(
                        text = task.title,
                        modifier = Modifier,
                        style = Theme.textStyle.title.medium,
                        color = Theme.color.title,

                        )
                    if (task.description != null)
                        Text(
                            text = task.description,
                            modifier = Modifier,
                            style = Theme.textStyle.body.small,
                            color = Theme.color.body,
                        )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Theme.dimension.regular),
                    thickness = 1.dp,
                    color = Theme.color.stroke
                )
                Column(
                    modifier = Modifier.padding(top = Theme.dimension.regular),
                    verticalArrangement = Arrangement.spacedBy(Theme.dimension.large)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Theme.dimension.small)
                    ) {
                        TaskStatusCard(taskUiStatus = task.status)
                        PriorityTag(
                            priority = task.priority,
                        )
                    }
                    changeStatusTo?.let {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(Theme.dimension.small)
                        ) {
                            SecondaryIconButton(
                                iconRes = painterResource(R.drawable.pencil_edit),
                                onClick = { onEditClick(task) }
                            )
                            SecondaryButton(
                                lable = changeStatusTo,
                                onClick = onMoveToClicked,
                                modifier = Modifier.weight(1f)
                            )

                        }
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewUpdateTaskStatus() {
    TudeeTheme {
        TaskViewDetails(
            task = TaskUiState(
                id = 1,
                title = "Organize Study Desk",
                description = "Review cell structure and functions for tomorrow...",
                dueDate = null,
                categoryId = 1,
                priority = TaskUiPriority.MEDIUM,
                status = TaskUiStatus.IN_PROGRESS,
            ),
            onDismiss = {},
            onEditClick = {},
            onMoveToClicked = {}
        )
    }
}
