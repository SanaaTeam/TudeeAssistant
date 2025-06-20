package com.sanaa.tudee_assistant.presentation.screen.category_task

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.component.CategoryTaskCard
import com.sanaa.tudee_assistant.presentation.design_system.component.TabItem
import com.sanaa.tudee_assistant.presentation.design_system.component.TudeeScrollableTabs
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.TaskUiModel
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.screen.category_task.composable.UpdateCurrentCategory
import com.sanaa.tudee_assistant.presentation.state.CategoryTaskUiState
import com.sanaa.tudee_assistant.presentation.state.TaskUiState
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun CategoryTaskScreen(
    viewModel: CategoryTaskViewModel = koinInject<CategoryTaskViewModel>(),
    categoryId: Int?,
    onBackClick: () -> Unit = {},
    onEditCategory: (Int) -> Unit = {},
) {
    val state by viewModel.state.collectAsState()


    LaunchedEffect(categoryId) {
        if (categoryId != null)
            viewModel.loadCategoryTasks(categoryId)
    }

    CategoryTaskScreenContent(
        state = state,
        onBackClick = onBackClick,
        onEditCategory = onEditCategory,
        onDeleteCategory = {

        },
        viewModel = viewModel
    )
}

@Composable
fun CategoryTaskScreenContent(
    state: CategoryTaskUiState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onEditCategory: (Int) -> Unit = {},
    onDeleteCategory: () -> Unit = {},
    viewModel: CategoryTaskViewModel
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var showBottomSheetState by remember { mutableStateOf(false) }


    val tabs = listOf(
        TabItem(
            label = stringResource(R.string.in_progress_task_status),
            count = state.inProgressTasksCount
        ) {
            TasksList(
                tasks = state.inProgressTasks,
            )
        },
        TabItem(
            label = stringResource(R.string.todo_task_status),
            count = state.todoTasksCount
        ) {
            TasksList(
                tasks = state.todoTasks,
            )
        },
        TabItem(
            label = stringResource(R.string.done_task_status),
            count = state.doneTasksCount
        ) {
            TasksList(
                tasks = state.doneTasks,
            )
        }
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.surface)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(Theme.color.surfaceHigh)
                .padding(horizontal = Theme.dimension.medium),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(color = Theme.color.stroke, width = 1.dp, shape = CircleShape)
                            .clickable { onBackClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_left),
                            contentDescription = null,
                            tint = Theme.color.body,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Text(
                        text = state.categoryName,
                        style = Theme.textStyle.title.large,
                        color = Theme.color.title
                    )
                }

                if (!state.isDefault && state.categoryId != -1) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(color = Theme.color.stroke, width = 1.dp, shape = CircleShape)
                            .clickable { showBottomSheetState = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.pencil_edit),
                            contentDescription = null,
                            tint = Theme.color.body,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Theme.color.primary
                    )
                }
            }

            state.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(Theme.dimension.medium)
                    ) {
                        Text(
                            text = state.error,
                            style = Theme.textStyle.body.medium,
                            color = Theme.color.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = Theme.dimension.medium)
                        )
                    }
                }
            }

            state.totalTasksCount == 0 -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(Theme.dimension.medium)
                    ) {
                        Text(
                            text = "No tasks found in this category",
                            style = Theme.textStyle.title.medium,
                            color = Theme.color.hint,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Add your first task to get started!",
                            style = Theme.textStyle.body.medium,
                            color = Theme.color.hint,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            else -> {
                TudeeScrollableTabs(
                    tabs = tabs,
                    selectedTabIndex = selectedTabIndex,
                    onTabSelected = { selectedTabIndex = it },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        if (showBottomSheetState) {
            UpdateCurrentCategory(
                onImageSelected = { },
                onEditClick = { name, imageUri ->
                    showBottomSheetState = false
                },
                onDeleteClick = {
                    showBottomSheetState = false
                    onBackClick()
                },
                onDismiss = {
                    showBottomSheetState = false
                }
            )
        }

    }
}

@Composable
private fun TasksList(
    tasks: List<TaskUiModel>,
    modifier: Modifier = Modifier,
) {
    if (tasks.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Theme.color.surface),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No tasks found",
                style = Theme.textStyle.body.medium,
                color = Theme.color.hint,
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(Theme.color.surface),
            contentPadding = PaddingValues(Theme.dimension.medium),
            verticalArrangement = Arrangement.spacedBy(Theme.dimension.medium)
        ) {
            items(tasks) { task ->
                CategoryTaskCard(
                    task = TaskUiState(
                        id = task.id,
                        title = task.title,
                        description = task.description,
                        dueDate = task.dueDate,
                        priority = task.priority,
                        status = task.status,
                        categoryId = task.categoryId
                    ),
                    categoryImagePath = task.categoryImagePath
                )
            }
        }
    }

}

@Preview
@Composable
private fun CategoryTaskScreenPreview() {
    TudeeTheme {
        val viewModel = koinViewModel<CategoryTaskViewModel>()

        CategoryTaskScreenContent(
            state = CategoryTaskUiState(
                categoryName = "Work",
                isDefault = false,
                categoryId = 1,
                todoTasks = listOf(
                    TaskUiModel(
                        1,
                        "Task 1",
                        "Description",
                        "12-03-2025",
                        "",
                        TaskUiPriority.HIGH,
                        TaskUiStatus.TODO,
                        1
                    )
                ),
                inProgressTasks = listOf(
                    TaskUiModel(
                        2,
                        "Task 2",
                        "Description",
                        "12-03-2025",
                        "",
                        TaskUiPriority.MEDIUM,
                        TaskUiStatus.IN_PROGRESS,
                        1
                    )
                ),
                doneTasks = listOf(
                    TaskUiModel(
                        3,
                        "Task 3",
                        "Description",
                        "12-03-2025",
                        "",
                        TaskUiPriority.LOW,
                        TaskUiStatus.DONE,
                        1
                    )
                )
            ),
            onBackClick = {},
            onEditCategory = {},
            viewModel = viewModel
        )
    }
}