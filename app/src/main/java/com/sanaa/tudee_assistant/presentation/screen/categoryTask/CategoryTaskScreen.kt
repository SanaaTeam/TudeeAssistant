package com.sanaa.tudee_assistant.presentation.screen.categoryTask

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.composable.TaskListColumn
import com.sanaa.tudee_assistant.presentation.designSystem.component.TabItem
import com.sanaa.tudee_assistant.presentation.designSystem.component.TudeeScrollableTabs
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState
import org.koin.compose.koinInject

@Composable
fun CategoryTaskScreen(
    viewModel: CategoryTaskViewModel = koinInject<CategoryTaskViewModel>(),
    categoryId: Int?,
    modifier: Modifier = Modifier,
//    onBackClick: () -> Unit = {},
//    onEditCategory: (Int) -> Unit = {},
) {
    val state by viewModel.state.collectAsState()


    LaunchedEffect(categoryId) {
        if (categoryId != null)
            viewModel.loadCategoryTasks(categoryId)
    }

    CategoryTaskScreenContent(
        state = state,
        listener = viewModel,
        onBackClick = { /*onBackClick()*/ },
        modifier = modifier.fillMaxSize()
    )
}

@Composable
fun CategoryTaskScreenContent(
    state: CategoryTaskScreenUiState,
    listener: CategoryTaskInteractionListener,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth()
                .background(color = Theme.color.surfaceHigh)
                .padding(horizontal = Theme.dimension.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedIconButton(painter = painterResource(R.drawable.arrow_left))
                Text(
                    text = state.currentCategory.name,
                    style = Theme.textStyle.title.large,
                    color = Theme.color.title,
                    modifier = Modifier.padding(start = Theme.dimension.regular)
                )
            }
            OutlinedIconButton()
        }

        TudeeScrollableTabs(
            tabs = listOf(
                TabItem(
                    label = stringResource(R.string.in_progress_task_status),
                    count = state.filteredTasks.size,
                    content = {
                        TaskListColumn(
                            taskList = state.filteredTasks,
                            onTaskClick = { },
                            categories = listOf(state.currentCategory),
                        )

                    }
                ),
                TabItem(
                    label = stringResource(R.string.todo_task_status),
                    count = state.filteredTasks.size,
                    content = {
                        TaskListColumn(
                            taskList = state.filteredTasks,
                            onTaskClick = { },
                            categories = listOf(state.currentCategory),
                        )

                    }
                ),
                TabItem(
                    label = stringResource(R.string.done_task_status),
                    count = state.filteredTasks.size,
                    content = {
                        TaskListColumn(
                            taskList = state.filteredTasks,
                            onTaskClick = { },
                            categories = listOf(state.currentCategory),
                        )

                    }
                ),

                ),
            selectedTabIndex = state.selectedTapIndex,
            onTabSelected = { it -> listener.onStatusChanged(it) },
            modifier = Modifier.fillMaxSize()
        )

    }

}


@Preview(showBackground = true)
@Composable
private fun CategoryTaskScreenPreview() {
    TudeeTheme {
        CategoryTaskScreenContent(
            state = CategoryTaskScreenUiState(
                currentCategory = CategoryUiState(
                    id = 1,
                    name = "Work",
                    imagePath = "",
                    isDefault = true,
                    tasksCount = 5
                ),
                allCategoryTasks = listOf(),
                isLoading = false
            ),
            listener = object : CategoryTaskInteractionListener {
                override fun onDeleteClicked() {}
                override fun onEditClicked() {}
                override fun onEditDismissClicked() {}
                override fun onConfirmDeleteClicked() {}
                override fun onDeleteDismiss() {}
                override fun onStatusChanged(index: Int) {

                }
            },
            onBackClick = {}
        )
    }
}
/*
@Preview
@Composable
private fun CategoryTaskScreenPreview() {
    TudeeTheme {
        val viewModel = koinViewModel<CategoryTaskViewModel>()

        CategoryTaskScreenContent(
            state = CategoryTaskScreenUiState(
                categoryName = "Work",
                isDefault = false,
                categoryId = 1,
                todoTasks = listOf(
                    TaskUiState(
                        1,
                        "Task 1",
                        "Description",
                        "12-03-2025",
                        TaskUiPriority.HIGH,
                        TaskUiStatus.TODO,
                        1,
                        "",
                    )
                ),
                inProgressTasks = listOf(
                    TaskUiState(
                        2,
                        "Task 2",
                        "Description",
                        "12-03-2025",
                        TaskUiPriority.MEDIUM,
                        TaskUiStatus.IN_PROGRESS,
                        1,
                        "",
                    )
                ),
                doneTasks = listOf(
                    TaskUiState(
                        3,
                        "Task 3",
                        "Description",
                        "12-03-2025",
                        TaskUiPriority.LOW,
                        TaskUiStatus.DONE,
                        1,
                        "",
                    )
                )
            ),
            onBackClick = {},
            onEditCategory = {},
            viewModel = viewModel
        )
    }
}
 */


@Composable
fun OutlinedIconButton(
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(R.drawable.pencil_edit),
    tint: Color = Theme.color.body,
    contentDescription: String? = null,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .border(width = 1.dp, color = Theme.color.stroke, shape = CircleShape)
            .clickable(
                onClick = { onClick() }),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.size(20.dp),
            tint = tint
        )
    }
}