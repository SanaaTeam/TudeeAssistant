package com.sanaa.tudee_assistant.presentation.screen.categoryTask

import android.net.Uri
import androidx.core.net.toUri
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.ImageProcessor
import com.sanaa.tudee_assistant.domain.service.StringProvider
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.base.BaseViewModel
import com.sanaa.tudee_assistant.presentation.model.CategoryUiState
import com.sanaa.tudee_assistant.presentation.model.SnackBarState
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.model.mapper.toDomain
import com.sanaa.tudee_assistant.presentation.model.mapper.toState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first

class CategoryTaskViewModel(
    private val categoryService: CategoryService,
    private val taskService: TaskService,
    val categoryId: Int?,
    private val imageProcessor: ImageProcessor,
    private val stringProvider: StringProvider,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseViewModel<CategoryTaskScreenUiState>(CategoryTaskScreenUiState(), dispatcher),
    CategoryTaskInteractionListener {

    private val _effects = MutableSharedFlow<CategoryTasksEffects>()
    val effects = _effects.asSharedFlow()

    init {
        if (categoryId != null)
            loadCategoryTasks(categoryId)
    }

    private fun loadCategoryTasks(categoryId: Int) {
        tryToExecute(
            callee = {
                updateState { it.copy(isLoading = true) }
                val category = categoryService.getCategoryById(categoryId)
                val tasks = taskService
                    .getTasksByCategoryId(categoryId)
                    .first()
                    .map { it.toState() }

                updateState {
                    it.copy(
                        currentCategory = category.toState(tasksCount = tasks.size),
                        todoTasks = tasks.filter { task -> task.status == TaskUiStatus.TODO },
                        inProgressTasks = tasks.filter { task -> task.status == TaskUiStatus.IN_PROGRESS },
                        doneTasks = tasks.filter { task -> task.status == TaskUiStatus.DONE }
                    )
                }
            },
            onError = { onError(message = "Error loading category tasks") },
            onSuccess = { onSuccess("Successfully loaded category tasks") }
        )
    }

    override fun onDeleteClicked() {
        updateState {
            it.copy(showDeleteCategoryBottomSheet = true, showEditCategoryBottomSheet = false)
        }
    }

    override fun onDeleteDismiss() {
        updateState {
            it.copy(
                showDeleteCategoryBottomSheet = false,
                showEditCategoryBottomSheet = true
            )
        }
    }

    override fun onEditClicked() {
        updateState {
            it.copy(
                showEditCategoryBottomSheet = true,
                editCategory = state.value.currentCategory
            )
        }
    }

    override fun onEditDismissClicked() {
        updateState { it.copy(showEditCategoryBottomSheet = false) }
    }

    override fun onConfirmDeleteClicked() {
        tryToExecute(
            callee = {
                updateState { it.copy(isLoading = true) }
                categoryService.deleteCategoryById(state.value.currentCategory.id)
                taskService.deleteTaskByCategoryId(state.value.currentCategory.id)
            },
            onError = { onError(message = stringProvider.deletingCategoryError) },
            onSuccess = {
                onSuccess(
                    message = stringProvider.deletedCategorySuccessfully,
                    updateState = {
                        it.copy(
                            showDeleteCategoryBottomSheet = false,
                            snackBarState = SnackBarState
                                .getInstance(stringProvider.deletedCategorySuccessfully),
                        )
                    },
                    effect = NavigateBackToCategoryList
                )
            }
        )
    }

    override fun onStatusChanged(index: Int) {
        val status = when (index) {
            0 -> TaskUiStatus.IN_PROGRESS
            1 -> TaskUiStatus.TODO
            2 -> TaskUiStatus.DONE
            else -> TaskUiStatus.TODO
        }
        updateState {
            it.copy(
                currentSelectedTaskStatus = status,
                selectedTapIndex = index
            )
        }
    }

    override fun onImageSelect(image: Uri?) {
        updateState {
            it.copy(
                editCategory = state.value.editCategory.copy(imagePath = image.toString())
            )
        }
    }

    override fun onTitleChange(title: String) {
        if (title.length > 24) return
        updateState {
            it.copy(
                editCategory = state.value.editCategory.copy(name = title)
            )
        }
    }

    override fun onSaveEditClicked(category: CategoryUiState) {
        tryToExecute(
            callee = {
                updateState { it.copy(isLoading = true) }

                val currentImagePath = state.value.currentCategory.imagePath
                val newImagePath = if (category.imagePath != currentImagePath) {
                    imageProcessor.saveImageToInternalStorage(
                        imageProcessor.processImage(category.imagePath.toUri())
                    )
                } else {
                    currentImagePath
                }

                categoryService.updateCategory(category.copy(imagePath = newImagePath).toDomain())
            },
            onSuccess = {
                onSuccess(
                    message = "Update task successfully",
                    updateState = {
                        it.copy(
                            showEditCategoryBottomSheet = false,
                            snackBarState = SnackBarState
                                .getInstance(stringProvider.categoryUpdateSuccessfully),
                        )

                    },
                    effect = NavigateBackToCategoryList
                )
                loadCategoryTasks(category.id)
            },
            onError = {},
        )
    }

    override fun onTaskClicked(task: TaskUiState) {
        updateState {
            it.copy(selectedTask = task, showTaskDetailsBottomSheet = true)
        }
    }

    override fun onTaskEditClicked(task: TaskUiState) {
        updateState {
            it.copy(showEditTaskBottomSheet = true)
        }
    }

    override fun onTaskEditDismiss() {
        updateState {
            it.copy(
                showEditTaskBottomSheet = false, selectedTask = null,
            )
        }
    }

    override fun onTaskEditSuccess() {
        updateState {
            it.copy(
                showEditTaskBottomSheet = false, selectedTask = null,
                snackBarState = SnackBarState
                    .getInstance(stringProvider.taskUpdateSuccess),
            )
        }
    }

    override fun onMoveStatusSuccess() {
        updateState {
            it.copy(
                snackBarState = SnackBarState
                    .getInstance(stringProvider.taskStatusUpdateSuccess),
                showTaskDetailsBottomSheet = false,
            )
        }
        loadCategoryTasks(state.value.currentCategory.id)
    }

    override fun onHideSnackBar() {
        updateState {
            it.copy(snackBarState = SnackBarState.hide())
        }
    }

    override fun onTaskDetailsDismiss() {
        updateState {
            it.copy(showTaskDetailsBottomSheet = false, selectedTask = null)
        }
        loadCategoryTasks(state.value.currentCategory.id)
    }

    private fun onSuccess(
        message: String?,
        updateState: (oldState: CategoryTaskScreenUiState) -> CategoryTaskScreenUiState = { it },
        effect: CategoryTasksEffects? = null,
    ) {
        updateState {
            updateState(it).copy(
                isLoading = false,
                error = null,
                success = message
            )
        }

        effect?.let {
            tryToExecute(
                callee = {
                    emitEffect(it)
                }
            )
        }
    }

    private fun onError(message: String?) {
        updateState {
            it.copy(isLoading = false, error = message, success = null)
        }
    }

    private suspend fun emitEffect(effect: CategoryTasksEffects) {
        _effects.emit(effect)

    }

    fun isValidForm(): Boolean {
        val current = state.value.currentCategory
        val edited = state.value.editCategory

        val hasNameChanged = edited.name != current.name
        val hasImageChanged = edited.imagePath != current.imagePath
        val isNameValid =
            edited.name.isNotBlank() && (edited.name.length in 2..24)

        return isNameValid && (hasNameChanged || hasImageChanged)
    }
}