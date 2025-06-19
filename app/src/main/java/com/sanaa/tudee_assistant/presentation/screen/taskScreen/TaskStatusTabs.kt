package com.sanaa.tudee_assistant.presentation.screen.taskScreen

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
import com.sanaa.tudee_assistant.presentation.design_system.component.TudeeTabs
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.state.TaskUiModel

@Composable
fun TaskStatusTabs(
    state: TasksScreenUiState,
    onTaskSwipe: (TaskUiModel) -> Boolean,
    onTaskClick: (TaskUiModel) -> Unit,
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

    TudeeTabs(
        tabs = listOf(
            TabItem(
                label = stringResource(R.string.in_progress_task_status),
                count = state.currentDateTasks.filter { it.status == TaskUiStatus.IN_PROGRESS }.size
            ) {
                TaskListColumn(
                    state.currentDateTasks.filter { it.status == TaskUiStatus.IN_PROGRESS },
                    onTaskSwipe = { task -> onTaskSwipe(task) },
                    onTaskClick = { onTaskClick }
                )
            },
            TabItem(
                label = stringResource(R.string.todo_task_status),
                count = state.currentDateTasks.filter { it.status == TaskUiStatus.TODO }.size
            ) {
                TaskListColumn(
                    state.currentDateTasks.filter { it.status == TaskUiStatus.TODO },
                    onTaskSwipe = { task -> onTaskSwipe(task) })
            },
            TabItem(
                label = stringResource(R.string.done_task_status),
                count = state.currentDateTasks.filter { it.status == TaskUiStatus.DONE }.size
            ) {
                TaskListColumn(
                    state.currentDateTasks.filter { it.status == TaskUiStatus.DONE },
                    onTaskSwipe = { task -> onTaskSwipe(task) })
            }
        ),
        selectedTabIndex = selectedTab,
        onTabSelected = { selectedTab = it },
        modifier = Modifier.fillMaxSize()
    )
}