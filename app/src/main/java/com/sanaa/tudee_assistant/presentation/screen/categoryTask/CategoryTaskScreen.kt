package com.sanaa.tudee_assistant.presentation.screen.categoryTask

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.composable.bottomSheet.DeleteComponent
import com.sanaa.tudee_assistant.presentation.composable.bottomSheet.task.AddEditTaskScreen
import com.sanaa.tudee_assistant.presentation.composable.bottomSheet.task.taskDetailsBottomSheet.TaskDetailsComponent
import com.sanaa.tudee_assistant.presentation.designSystem.component.TabItem
import com.sanaa.tudee_assistant.presentation.designSystem.component.TudeeScrollableTabs
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.CategoryUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.navigation.AppNavigation
import com.sanaa.tudee_assistant.presentation.navigation.MainScreenRoute
import com.sanaa.tudee_assistant.presentation.screen.category.AddEditCategoryBottomSheet
import com.sanaa.tudee_assistant.presentation.screen.categoryTask.components.CategoryTaskScreenContainer
import com.sanaa.tudee_assistant.presentation.screen.categoryTask.components.CategoryTasksTopBar
import com.sanaa.tudee_assistant.presentation.screen.categoryTask.components.EmptyCategoryTasksComponent
import com.sanaa.tudee_assistant.presentation.screen.categoryTask.components.TasksListComponent
import com.sanaa.tudee_assistant.presentation.shared.LocalSnackBarState
import com.sanaa.tudee_assistant.presentation.utils.DataProvider
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CategoryTaskScreen(
    modifier: Modifier = Modifier,
    categoryId: Int?,
    viewModel: CategoryTaskViewModel = koinViewModel<CategoryTaskViewModel>(
        parameters = {
            parametersOf(categoryId)
        }
    ),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navController = AppNavigation.app

    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is NavigateBackToCategoryList -> {
                    navController.popBackStack(
                        route = MainScreenRoute,
                        inclusive = false
                    )
                }
            }
        }
    }


    CategoryTaskScreenContent(
        state = state,
        listener = viewModel,
        isValidForm = viewModel::isValidForm,
        onBackClick = {
            navController.popBackStack(
                route = MainScreenRoute,
                inclusive = false
            )
        },
        modifier = modifier.fillMaxSize()
    )
}

@Composable
private fun CategoryTaskScreenContent(
    state: CategoryTaskScreenUiState,
    isValidForm: () -> Boolean,
    listener: CategoryTaskInteractionListener,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val snackBarState = LocalSnackBarState.current

    val categoryName = DataProvider.getStringResourceByName(
        state.currentCategory.name,
        LocalContext.current
    )

    LaunchedEffect(state.snackBarState) {
        if (state.snackBarState.isVisible) {
            snackBarState.value = state.snackBarState
            listener.onHideSnackBar()
        }
    }

    CategoryTaskScreenContainer(
        topBar = {
            CategoryTasksTopBar(
                title = categoryName,
                onEditClick = { listener.onEditClicked() },
                onBackClick = onBackClick,
                isEditable = !state.currentCategory.isDefault
            )
        },
        modifier = modifier

    ) {
        Box {
            TudeeScrollableTabs(
                tabs = listOf(
                    TabItem(
                        label = stringResource(R.string.in_progress_task_status),
                        count = state.filteredTasks.size,
                        content = {
                            if (state.filteredTasks.isEmpty()) {
                                EmptyCategoryTasksComponent(categoryName)
                            } else {
                                TasksListComponent(
                                    tasks = state.filteredTasks,
                                    category = state.currentCategory,
                                    onTaskClicked = listener::onTaskClicked
                                )
                            }
                        }),
                    TabItem(
                        label = stringResource(R.string.todo_task_status),
                        count = state.filteredTasks.size,
                        content = {
                            if (state.filteredTasks.isEmpty()) {
                                EmptyCategoryTasksComponent(categoryName)
                            } else {
                                TasksListComponent(
                                    tasks = state.filteredTasks,
                                    category = state.currentCategory,
                                    onTaskClicked = listener::onTaskClicked
                                )
                            }
                        }),
                    TabItem(
                        label = stringResource(R.string.done_task_status),
                        count = state.filteredTasks.size,
                        content = {
                            if (state.filteredTasks.isEmpty()) {
                                EmptyCategoryTasksComponent(categoryName)
                            } else {
                                TasksListComponent(
                                    tasks = state.filteredTasks,
                                    category = state.currentCategory,
                                    onTaskClicked = listener::onTaskClicked
                                )
                            }
                        }),

                    ),
                selectedTabIndex = state.selectedTapIndex,
                onTabSelected = { it -> listener.onStatusChanged(it) },
                modifier = Modifier.fillMaxSize()
            )
            if (state.showEditCategoryBottomSheet) {
                AddEditCategoryBottomSheet(
                    onImageSelected = { listener.onImageSelect(it) },
                    onTitleChange = { listener.onTitleChange(it) },
                    onSaveClick = { listener.onSaveEditClicked(it) },
                    onDismiss = { listener.onEditDismissClicked() },
                    category = state.editCategory,
                    isEditMode = !state.currentCategory.isDefault,
                    onDeleteClick = { listener.onDeleteClicked() },
                    isFormValid = isValidForm
                )
            }
            if (state.showDeleteCategoryBottomSheet) {
                DeleteComponent(
                    onDismiss = listener::onDeleteDismiss,
                    onDeleteClicked = listener::onConfirmDeleteClicked,
                    title = stringResource(R.string.delete_category),
                )
            }
            if (state.selectedTask != null && state.showTaskDetailsBottomSheet)
                TaskDetailsComponent(
                    selectedTaskId = state.selectedTask.id,
                    onEditClick = {
                        listener.onTaskEditClicked(state.selectedTask)
                    },
                    onDismiss = listener::onTaskDetailsDismiss,
                    onMoveStatusSuccess = {
                        listener.onMoveStatusSuccess()
                    },
                    onMoveStatusFail = {}
                )
            if (state.showEditTaskBottomSheet) {
                AddEditTaskScreen(
                    onDismiss = { listener.onTaskEditDismiss() },
                    isEditMode = true,
                    taskToEdit = state.selectedTask,
                    onSuccess = {
                        listener.onTaskDetailsDismiss()
                        listener.onTaskEditSuccess()
                    },
                    onError = {},
                )
            }
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
            ),
            listener = object : CategoryTaskInteractionListener {
                override fun onDeleteClicked() {}
                override fun onEditClicked() {}
                override fun onEditDismissClicked() {}
                override fun onConfirmDeleteClicked() {}
                override fun onDeleteDismiss() {}
                override fun onStatusChanged(index: Int) {}
                override fun onImageSelect(image: Uri?) {}
                override fun onTitleChange(title: String) {}
                override fun onSaveEditClicked(category: CategoryUiState) {}
                override fun onTaskClicked(task: TaskUiState) {}
                override fun onTaskEditClicked(task: TaskUiState) {}
                override fun onTaskEditDismiss() {}
                override fun onHideSnackBar() {}
                override fun onTaskDetailsDismiss() {}
                override fun onTaskEditSuccess() {}
                override fun onMoveStatusSuccess() {}
            },
            isValidForm = { true },
            onBackClick = {},
        )
    }
}