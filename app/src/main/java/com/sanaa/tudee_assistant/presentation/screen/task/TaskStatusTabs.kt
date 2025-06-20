package com.sanaa.tudee_assistant.presentation.screen.task

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.component.TabItem
import com.sanaa.tudee_assistant.presentation.design_system.component.TudeeScrollableTabs
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus

@Composable
fun TaskStatusTabs(
    state: TasksScreenUiState,
    onTaskSwipe: (TaskUiState) -> Boolean,
    onTaskClick: (TaskUiState) -> Unit,
) {

    var selectedTab by remember {
        mutableIntStateOf(
            when (state.selectedTaskUiStatus) {
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
                count = state.currentDateTasks.filter { it.status == TaskUiStatus.IN_PROGRESS }.size
            ) {
                TaskListColumn(
                    taskList = state.currentDateTasks.filter { it.status == TaskUiStatus.IN_PROGRESS },
                    categories = state.categories,
                    onTaskSwipe = { task -> onTaskSwipe(task) },
                    onTaskClick = onTaskClick
                )
            },
            TabItem(
                label = stringResource(R.string.todo_task_status),
                count = state.currentDateTasks.filter { it.status == TaskUiStatus.TODO }.size
            ) {
                TaskListColumn(
                    taskList = state.currentDateTasks.filter { it.status == TaskUiStatus.TODO },
                    categories = state.categories,
                    onTaskSwipe = { task -> onTaskSwipe(task) },
                    onTaskClick = onTaskClick
                )

            },
            TabItem(
                label = stringResource(R.string.done_task_status),
                count = state.currentDateTasks.filter { it.status == TaskUiStatus.DONE }.size
            ) {
                TaskListColumn(
                    taskList = state.currentDateTasks.filter { it.status == TaskUiStatus.DONE },
                    categories = state.categories,
                    onTaskSwipe = { task -> onTaskSwipe(task) },
                    onTaskClick = onTaskClick
                )
            }
        ),
        selectedTabIndex = selectedTab,
        onTabSelected = { selectedTab = it },
        modifier = Modifier.fillMaxSize()
    )
}