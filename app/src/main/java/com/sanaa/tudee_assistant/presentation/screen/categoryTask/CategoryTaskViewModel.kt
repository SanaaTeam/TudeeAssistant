package com.sanaa.tudee_assistant.presentation.screen.categoryTask

import android.net.Uri
import androidx.core.net.toUri
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.ImageProcessor
import com.sanaa.tudee_assistant.domain.service.StringProvider
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.SnackBarState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState
import com.sanaa.tudee_assistant.presentation.state.TaskUiState
import com.sanaa.tudee_assistant.presentation.state.mapper.toState
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

class CategoryTaskViewModel(
    private val categoryService: CategoryService,
    private val taskService: TaskService,
    private val imageProcessor: ImageProcessor,
    private val stringProvider: StringProvider,
) : BaseViewModel<CategoryTaskScreenUiState>(initialState = CategoryTaskScreenUiState()),
    CategoryTaskInteractionListener {


    fun loadCategoryTasks(categoryId: Int) {
        tryToExecute(
            function = {
                _state.update { it.copy(isLoading = true) }
                val category = categoryService.getCategoryById(categoryId)
                val tasks = taskService
                    .getTasksByCategoryId(categoryId)
                    .first()
                    .map { it.toState() }
                val currentStatus = _state.value.currentSelectedTaskStatus

                _state.update {
                    it.copy(
                        currentCategory = category.toState(tasksCount = tasks.size),
                        allCategoryTasks = tasks,
                        filteredTasks = tasks.filter { task -> task.status == currentStatus }
                    )
                }
            },
            onError = { onError(message = "Error loading category tasks") },
            onSuccess = { onSuccess("Successfully loaded category tasks") }
        )
    }

    override fun onDeleteClicked() {
        _state.update {
            it.copy(showDeleteCategoryBottomSheet = true, showEditCategoryBottomSheet = false)
        }
    }

    override fun onDeleteDismiss() {
        _state.update {
            it.copy(
                showDeleteCategoryBottomSheet = false,
                showEditCategoryBottomSheet = true
            )
        }
    }

    override fun onEditClicked() {
        _state.update {
            it.copy(
                showEditCategoryBottomSheet = true,
                editCategory = _state.value.currentCategory
            )
        }
    }

    override fun onEditDismissClicked() {
        _state.update { it.copy(showEditCategoryBottomSheet = false) }
    }

    override fun onConfirmDeleteClicked() {
        tryToExecute(
            function = {
                _state.update { it.copy(isLoading = true) }
                categoryService.deleteCategoryById(_state.value.currentCategory.id)
            },
            onError = { onError(message = stringProvider.getString(R.string.error_deleting_category)) },
            onSuccess = {
                onSuccess(
                    message = stringProvider.getString(R.string.successfully_deleted_category),
                    updateState = {
                        it.copy(
                            showDeleteCategoryBottomSheet = false,
                            navigateBackToCategoryList = true,
                            snackBarState = SnackBarState
                                .getInstance(stringProvider.getString(R.string.successfully_deleted_category)),
                        )
                    })

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
        _state.update {
            it.copy(
                currentSelectedTaskStatus = status,
                filteredTasks = _state.value.allCategoryTasks.filter { task -> task.status == status },
                selectedTapIndex = index
            )
        }
    }

    override fun onImageSelect(image: Uri?) {
        _state.update {
            it.copy(
                editCategory = _state.value.editCategory.copy(imagePath = image.toString())
            )
        }
    }

    override fun onTitleChange(title: String) {
        _state.update {
            it.copy(
                editCategory = _state.value.editCategory.copy(name = title)
            )
        }
    }

    override fun onSaveEditClicked(category: CategoryUiState) {
        tryToExecute(
            function = {
                _state.update { it.copy(isLoading = true) }

                val currentImagePath = _state.value.currentCategory.imagePath
                val newImagePath = if (category.imagePath != currentImagePath) {
                    imageProcessor.saveImageToInternalStorage(
                        imageProcessor.processImage(category.imagePath.toUri())
                    )
                } else {
                    currentImagePath
                }

                categoryService.updateCategory(
                    Category(
                        id = category.id,
                        name = category.name,
                        imagePath = newImagePath,
                        isDefault = category.isDefault
                    )
                )
            },
            onSuccess = {
                onSuccess(
                    message = "Update task successfully",
                    updateState = {
                        it.copy(
                            showEditCategoryBottomSheet = false,
                            snackBarState = SnackBarState
                                .getInstance(stringProvider.getString(R.string.category_updated_successfully)),
                            navigateBackToCategoryList = true,
                        )

                    }
                )
                loadCategoryTasks(category.id)
            },
            onError = {},
        )
    }

    override fun onTaskClicked(task: TaskUiState) {
        _state.update {
            it.copy(selectedTask = task, showTaskDetailsBottomSheet = true)
        }
    }

    override fun onTaskEditClicked(task: TaskUiState) {
        _state.update {
            it.copy(showEditTaskBottomSheet = true)
        }
    }

    override fun onTaskEditDismiss() {
        _state.update {
            it.copy(
                showEditTaskBottomSheet = false, selectedTask = null,
            )
        }
    }

    override fun onTaskEditSuccess() {
        _state.update {
            it.copy(
                showEditTaskBottomSheet = false, selectedTask = null,
                snackBarState = SnackBarState
                    .getInstance(stringProvider.getString(R.string.task_update_success)),
            )
        }
    }

    override fun onMoveStatusSuccess() {
        _state.update {
            it.copy(
                snackBarState = SnackBarState
                    .getInstance(stringProvider.getString(R.string.task_status_update_success)),
                showTaskDetailsBottomSheet = false,
            )
        }
        loadCategoryTasks(state.value.currentCategory.id)
    }

    override fun onHideSnackBar() {
        _state.update {
            it.copy(snackBarState = SnackBarState.hide())
        }
    }

    override fun onTaskDetailsDismiss() {
        _state.update {
            it.copy(showTaskDetailsBottomSheet = false, selectedTask = null)
        }
        loadCategoryTasks(state.value.currentCategory.id)
    }

    private fun onSuccess(
        message: String?,
        updateState: (oldState: CategoryTaskScreenUiState) -> CategoryTaskScreenUiState = { it },
    ) {
        _state.update {
            updateState(it).copy(
                isLoading = false,
                error = null,
                success = message
            )
        }
    }

    private fun onError(message: String?) {
        _state.update {
            it.copy(isLoading = false, error = message, success = null)
        }
    }

    fun isValidForm(): Boolean {
        val current = _state.value.currentCategory
        val edited = _state.value.editCategory

        val hasNameChanged = edited.name != current.name
        val hasImageChanged = edited.imagePath != current.imagePath
        val isNameValid =
            edited.name.isNotBlank() && (edited.name.length <= 24 && edited.name.length >= 2)

        return isNameValid && (hasNameChanged || hasImageChanged)
    }
}