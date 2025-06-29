package com.sanaa.tudee_assistant.presentation.component.bottomSheet.task.taskDetailsBottomSheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.component.CategoryThumbnail
import com.sanaa.tudee_assistant.presentation.designSystem.component.BaseBottomSheet
import com.sanaa.tudee_assistant.presentation.designSystem.component.CategoryImageContainer
import com.sanaa.tudee_assistant.presentation.designSystem.component.PriorityTag
import com.sanaa.tudee_assistant.presentation.designSystem.component.TaskStatusCard
import com.sanaa.tudee_assistant.presentation.designSystem.component.button.SecondaryButton
import com.sanaa.tudee_assistant.presentation.designSystem.component.button.SecondaryIconButton
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.mapper.toState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsComponent(
    selectedTaskId: Int,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    interactionListener: TaskDetailsBottomSheetViewModel = koinViewModel<TaskDetailsBottomSheetViewModel>(),
    onEditClick: (TaskUiState) -> Unit = {},
    onMoveStatusSuccess: () -> Unit = {},
    onMoveStatusFail: () -> Unit = {},
) {
    val state: State<DetailsUiState> = interactionListener.state.collectAsStateWithLifecycle()

    LaunchedEffect(selectedTaskId) {
        interactionListener.getSelectedTask(selectedTaskId)
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

                CategoryImageContainer {
                    CategoryThumbnail(
                        modifier.size(32.dp),
                        imagePath = state.value.categoryImagePath
                    )
                }
                Column(
                    modifier = Modifier.padding(top = Theme.dimension.small),
                    verticalArrangement = Arrangement.spacedBy(Theme.dimension.small)
                ) {
                    Text(
                        text = state.value.title,
                        modifier = Modifier,
                        style = Theme.textStyle.title.medium,
                        color = Theme.color.title,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    state.value.description?.let {
                        Text(
                            text = it,
                            modifier = Modifier,
                            style = Theme.textStyle.body.small,
                            color = Theme.color.body,
                            maxLines = 5,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
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
                        TaskStatusCard(taskUiStatus = state.value.status)
                        PriorityTag(
                            priority = state.value.priority,
                            enabled = false
                        )
                    }
                    state.value.moveStatusToLabel.let { label ->
                        if (label.isNotEmpty()) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(Theme.dimension.small)
                            ) {
                                SecondaryIconButton(
                                    iconRes = painterResource(R.drawable.pencil_edit),
                                    onClick = { onEditClick(state.value.toState()) }
                                )
                                SecondaryButton(
                                    label = label,
                                    onClick = {
                                        interactionListener.onMoveTaskToAnotherStatus(
                                            onMoveStatusSuccess, onMoveStatusFail
                                        )
                                    },
                                    modifier = Modifier.weight(1f)
                                )

                            }
                        }

                    }
                }
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun PreviewUpdateTaskStatus() {
    TudeeTheme {
        TaskDetailsComponent(
            selectedTaskId = 1,
            onDismiss = {},
        ) {}
    }
}
