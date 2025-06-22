package com.sanaa.tudee_assistant.presentation.composable

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
import com.sanaa.tudee_assistant.presentation.design_system.component.EmptyScreen
import com.sanaa.tudee_assistant.presentation.design_system.component.TabItem
import com.sanaa.tudee_assistant.presentation.design_system.component.TudeeScrollableTabs
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.screen.taskScreen.TasksScreenUiState
import com.sanaa.tudee_assistant.presentation.state.TaskUiState

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
                if (state.currentDateTasks.any { it.status == TaskUiStatus.IN_PROGRESS }) {
                    TaskListColumn(
                        taskList = state.currentDateTasks.filter { it.status == TaskUiStatus.IN_PROGRESS },
                        categories = state.categories,
                        onTaskSwipe = { task -> onTaskSwipe(task) },
                        onTaskClick = { onTaskClick }
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 20.dp, top = 120.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        EmptyScreen()
                    }
                }
            },
            TabItem(
                label = stringResource(R.string.todo_task_status),
                count = state.currentDateTasks.filter { it.status == TaskUiStatus.TODO }.size
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
                        EmptyScreen()
                    }
                }
            },
            TabItem(
                label = stringResource(R.string.done_task_status),
                count = state.currentDateTasks.filter { it.status == TaskUiStatus.DONE }.size
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
                        EmptyScreen()
                    }
                }
            }
        ),
        selectedTabIndex = selectedTab,
        onTabSelected = { selectedTab = it },
        modifier = Modifier.fillMaxSize()
    )
}