package com.sanaa.tudee_assistant.presentation.screen.categoryTask

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.component.CategoryTaskCard
import com.sanaa.tudee_assistant.presentation.designSystem.component.EmptyScreen
import com.sanaa.tudee_assistant.presentation.designSystem.component.TabItem
import com.sanaa.tudee_assistant.presentation.designSystem.component.TudeeScrollableTabs
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.screen.category.AddNewCategory
import com.sanaa.tudee_assistant.presentation.screen.categoryTask.components.CategoryTaskHeaderComponent
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState
import com.sanaa.tudee_assistant.presentation.state.TaskUiState
import org.koin.compose.koinInject

@Composable
fun CategoryTaskScreen(
    viewModel: CategoryTaskViewModel = koinInject<CategoryTaskViewModel>(),
    categoryId: Int?,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()


    LaunchedEffect(categoryId) {
        if (categoryId != null) viewModel.loadCategoryTasks(categoryId)
    }

    CategoryTaskScreenContent(
        state = state,
        listener = viewModel,
        modifier = modifier.fillMaxSize()
    )
}

@Composable
fun CategoryTaskScreenContent(
    state: CategoryTaskScreenUiState,
    listener: CategoryTaskInteractionListener,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(color = Theme.color.surface)
            .fillMaxWidth()
    ) {
        CategoryTaskHeaderComponent(
            title = state.currentCategory.name,
            onEditClick = { listener.onEditClicked() },
            onBackClick = {},
            isEditable = state.currentCategory.isDefault
        )

        TudeeScrollableTabs(
            tabs = listOf(
                TabItem(
                    label = stringResource(R.string.in_progress_task_status),
                    count = state.filteredTasks.size,
                    content = {
                        AnimatedVisibility(visible = state.filteredTasks.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(end = 20.dp, top = 172.dp),
                                contentAlignment = Alignment.TopEnd
                            ) {
                                EmptyScreen(
                                    title = stringResource(
                                        R.string.no_tasks_in,
                                        state.currentCategory.name,
                                    ),
                                    caption = null
                                )
                            }
                        }
                        AnimatedVisibility(visible = state.filteredTasks.isNotEmpty()) {
                            TasksList(
                                tasks = state.filteredTasks,
                                category = state.currentCategory,
                            )
                        }
                    }),
                TabItem(
                    label = stringResource(R.string.todo_task_status),
                    count = state.filteredTasks.size,
                    content = {
                        AnimatedVisibility(visible = state.filteredTasks.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(end = 20.dp, top = 172.dp),
                                contentAlignment = Alignment.TopEnd
                            ) {
                                EmptyScreen(
                                    title = stringResource(
                                        R.string.no_tasks_in,
                                        state.currentCategory.name,
                                    ),
                                    caption = null
                                )
                            }
                        }

                        AnimatedVisibility(visible = state.filteredTasks.isNotEmpty()) {
                            TasksList(
                                tasks = state.filteredTasks,
                                category = state.currentCategory,
                            )
                        }
                    }),
                TabItem(
                    label = stringResource(R.string.done_task_status),
                    count = state.filteredTasks.size,
                    content = {
                        AnimatedVisibility(visible = state.filteredTasks.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(end = 20.dp, top = 172.dp),
                                contentAlignment = Alignment.TopEnd
                            ) {
                                EmptyScreen(
                                    title = stringResource(
                                        R.string.no_tasks_in,
                                        state.currentCategory.name,
                                    ),
                                    caption = null
                                )
                            }
                        }
                        AnimatedVisibility(visible = state.filteredTasks.isNotEmpty()) {
                            TasksList(
                                tasks = state.filteredTasks,
                                category = state.currentCategory,
                            )
                        }
                    }),

                ),
            selectedTabIndex = state.selectedTapIndex,
            onTabSelected = { it -> listener.onStatusChanged(it) },
            modifier = Modifier.fillMaxSize()
        )

        if (state.showEditCategoryBottomSheet) {
            AddNewCategory(
                onImageSelected = { },
                onAddClick = { _, _ -> },
                onDismiss = { listener.onEditDismissClicked() })
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun CategoryTaskScreenPreview() {
    TudeeTheme {
        CategoryTaskScreenContent(
            state = CategoryTaskScreenUiState(
                currentCategory = CategoryUiState(
                    id = 1, name = "Work", imagePath = "", isDefault = true, tasksCount = 5
                ), allCategoryTasks = listOf(), isLoading = false
            ), listener = object : CategoryTaskInteractionListener {
                override fun onDeleteClicked() {}
                override fun onEditClicked() {}
                override fun onEditDismissClicked() {}
                override fun onConfirmDeleteClicked() {}
                override fun onDeleteDismiss() {}
                override fun onStatusChanged(index: Int) {}
                override fun onImageSelect(image: Uri?) {
                    TODO("Not yet implemented")
                }

                override fun onTitleChange(title: String) {
                    TODO("Not yet implemented")
                }
            }
        )
    }
}

@Composable
fun TasksList(
    tasks: List<TaskUiState>,
    category: CategoryUiState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.surface),
        contentPadding = PaddingValues(
            vertical = Theme.dimension.regular, horizontal = Theme.dimension.medium
        ),
        verticalArrangement = Arrangement.spacedBy(Theme.dimension.small)
    ) {
        itemsIndexed(tasks, key = { _, item -> item.hashCode() }) { _, task ->
            CategoryTaskCard(
                task = task, categoryImagePath = category.imagePath, onClick = {})
        }
    }
}

