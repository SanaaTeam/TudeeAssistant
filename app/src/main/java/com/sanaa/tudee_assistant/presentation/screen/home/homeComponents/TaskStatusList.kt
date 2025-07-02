package com.sanaa.tudee_assistant.presentation.screen.home.homeComponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.component.EmptyContent
import com.sanaa.tudee_assistant.presentation.designSystem.component.PriorityTag
import com.sanaa.tudee_assistant.presentation.designSystem.component.TaskItemCard
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.model.CategoryUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.screen.home.HomeScreenUiState

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun TaskStatusList(
    scrollState: LazyListState,
    state: HomeScreenUiState,
    onTaskClick: (TaskUiState) -> Unit,
    onNavigateToTaskScreen: (TaskUiStatus) -> Unit,
) {
    LazyColumn(
        state = scrollState,
        modifier = Modifier,
        contentPadding = PaddingValues(bottom = Theme.dimension.regular)
    ) {
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .background(Theme.color.primary)
                )

                HomeOverviewCard(state)
            }
        }

        item {
            AnimatedVisibility(
                state.tasks.isEmpty(),
                exit = shrinkVertically(
                    shrinkTowards = Alignment.Top,
                    animationSpec = tween(500)
                ) + fadeOut(tween(50))
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 48.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    EmptyContent()
                }
            }
        }

        stickyHeader {
            if (state.tasks.any { it.status == TaskUiStatus.DONE }) {
                Title(
                    text = stringResource(R.string.done_task_status),
                    tasksCount = state.tasks.filter { it.status == TaskUiStatus.DONE }.size,
                    taskUiStatus = TaskUiStatus.DONE,
                    onNavigateToTaskScreen = onNavigateToTaskScreen
                )
            }
        }

        item {
            TaskStatusList(
                items = state.tasks.filter { it.status == TaskUiStatus.DONE },
                categories = state.categories,
                onClick = { onTaskClick(it) }
            )
        }

        stickyHeader {
            if (state.tasks.any { it.status == TaskUiStatus.IN_PROGRESS }) {
                Title(
                    text = stringResource(R.string.in_progress_task_status),
                    tasksCount = state.tasks.filter { it.status == TaskUiStatus.IN_PROGRESS }.size,
                    taskUiStatus = TaskUiStatus.IN_PROGRESS,
                    onNavigateToTaskScreen = onNavigateToTaskScreen
                )
            }
        }

        item {
            TaskStatusList(
                items = state.tasks.filter { it.status == TaskUiStatus.IN_PROGRESS },
                categories = state.categories,
                onClick = { onTaskClick(it) }
            )
        }

        stickyHeader {
            if (state.tasks.any { it.status == TaskUiStatus.TODO }) {
                Title(
                    text = stringResource(R.string.todo_task_status),
                    tasksCount = state.tasks.filter { it.status == TaskUiStatus.TODO }.size,
                    taskUiStatus = TaskUiStatus.TODO,
                    onNavigateToTaskScreen = onNavigateToTaskScreen
                )
            }
        }

        item {
            TaskStatusList(
                items = state.tasks.filter { it.status == TaskUiStatus.TODO },
                categories = state.categories,
                onClick = { onTaskClick(it) }
            )
        }
    }
}

@Composable
fun TaskStatusList(
    items: List<TaskUiState>,
    categories: List<CategoryUiState>,
    onClick: (TaskUiState) -> Unit,
) {
    if (items.isEmpty()) return
    val targetHeight = if (items.size == 1) 111.dp + Theme.dimension.medium
    else 222.dp + Theme.dimension.extraLarge

    LazyHorizontalGrid(
        modifier = Modifier.height(targetHeight),
        rows = GridCells.Fixed(
            if (items.size == 1) 1 else 2
        ),
        contentPadding = PaddingValues(
            start = Theme.dimension.medium,
            end = Theme.dimension.medium,
            bottom = Theme.dimension.medium
        ),
        verticalArrangement = Arrangement.spacedBy(Theme.dimension.small),
        horizontalArrangement = Arrangement.spacedBy(Theme.dimension.small),
    ) {
        items(items = items) { task ->
            val categoryImagePath = categories.first { it.id == task.categoryId }.imagePath
            TaskItemCard(
                modifier = Modifier.width(320.dp),
                task = task,
                categoryImagePath = categoryImagePath,
                onClick = { onClick(it) },
                taskDateAndPriority = {
                    PriorityTag(
                        modifier = Modifier.padding(start = Theme.dimension.extraSmall),
                        priority = task.priority,
                        enabled = false
                    )
                }
            )
        }
    }
}