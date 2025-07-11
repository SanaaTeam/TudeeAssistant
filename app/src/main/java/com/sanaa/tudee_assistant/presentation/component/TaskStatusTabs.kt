package com.sanaa.tudee_assistant.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.component.EmptyContent
import com.sanaa.tudee_assistant.presentation.designSystem.component.TabItem
import com.sanaa.tudee_assistant.presentation.designSystem.component.TudeeScrollableTabs
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.screen.tasks.TasksScreenUiState

@Composable
fun TaskStatusTabs(
    state: TasksScreenUiState,
    onTaskSwipe: (TaskUiState) -> Boolean,
    onTaskClick: (TaskUiState) -> Unit,
    onTapClick: (TaskUiStatus) -> Unit,
) {

    var selectedTab by remember {
        mutableIntStateOf(
            when (state.selectedStatusTab) {
                TaskUiStatus.IN_PROGRESS -> 0
                TaskUiStatus.TODO -> 1
                TaskUiStatus.DONE -> 2
            }
        )
    }

    TudeeScrollableTabs(
        tabs = listOf(
            TabItem(
                label = stringResource(R.string.in_progress_task_status),
                count = state.currentDateTasks.filter { it.status == TaskUiStatus.IN_PROGRESS }.size,
                onClick = { onTapClick(TaskUiStatus.IN_PROGRESS) },
            ) {
                if (state.currentDateTasks.any { it.status == TaskUiStatus.IN_PROGRESS }) {
                    TaskListColumn(
                        taskList = state.currentDateTasks.filter { it.status == TaskUiStatus.IN_PROGRESS },
                        categories = state.categories,
                        onTaskSwipe = { task -> onTaskSwipe(task) },
                        onTaskClick = onTaskClick
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 20.dp, top = 120.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        EmptyContent(title = stringResource(R.string.no_task_here))
                    }
                }
            },
            TabItem(
                label = stringResource(R.string.todo_task_status),
                count = state.currentDateTasks.filter { it.status == TaskUiStatus.TODO }.size,
                onClick = { onTapClick(TaskUiStatus.TODO) },
            ) {
                if (state.currentDateTasks.any { it.status == TaskUiStatus.TODO }) {
                    TaskListColumn(
                        taskList = state.currentDateTasks.filter { it.status == TaskUiStatus.TODO },
                        categories = state.categories,
                        onTaskSwipe = { task -> onTaskSwipe(task) },
                        onTaskClick = onTaskClick
                    )

                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 20.dp, top = 120.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        EmptyContent(
                            title = stringResource(R.string.no_task_here)
                        )
                    }
                }
            },
            TabItem(
                label = stringResource(R.string.done_task_status),
                count = state.currentDateTasks.filter { it.status == TaskUiStatus.DONE }.size,
                onClick = { onTapClick(TaskUiStatus.DONE) },
            ) {
                if (state.currentDateTasks.any { it.status == TaskUiStatus.DONE }) {
                    TaskListColumn(
                        taskList = state.currentDateTasks.filter { it.status == TaskUiStatus.DONE },
                        categories = state.categories,
                        onTaskSwipe = { task -> onTaskSwipe(task) },
                        onTaskClick = onTaskClick
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 20.dp, top = 120.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        EmptyContent(title = stringResource(R.string.no_task_here))
                    }
                }
            }
        ),
        selectedTabIndex = selectedTab,
        onTabSelected = { selectedTab = it },
        modifier = Modifier.fillMaxSize()
    )
}