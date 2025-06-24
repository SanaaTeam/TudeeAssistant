package com.sanaa.tudee_assistant.presentation.screen.categoryTask

import android.net.Uri
import androidx.core.net.toUri
import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.ImageProcessor
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState
import com.sanaa.tudee_assistant.presentation.state.mapper.toState
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

class CategoryTaskViewModel(
    private val categoryService: CategoryService,
    private val taskService: TaskService,
    val categoryId: Int,
    private val imageProcessor: ImageProcessor,
) : BaseViewModel<CategoryTaskScreenUiState>(initialState = CategoryTaskScreenUiState()),
    CategoryTaskInteractionListener {


    init {
        loadCategoryTasks(categoryId)
    }

    private fun loadCategoryTasks(categoryId: Int) {
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
            it.copy(showDeleteCategoryBottomSheet = true)
        }
    }

    override fun onDeleteDismiss() {
        _state.update { it.copy(showDeleteCategoryBottomSheet = false) }
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
            onError = { onError(message = "Error deleting category") },
            onSuccess = { onSuccess("Successfully deleted category") }
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
                val imagePath = imageProcessor.saveImageToInternalStorage(
                    imageProcessor.processImage(category.imagePath.toUri())
                )
                categoryService.updateCategory(
                    Category(
                        id = category.id,
                        name = category.name,
                        imagePath = imagePath,
                        isDefault = category.isDefault
                    )
                )
            },
            onSuccess = { onSuccess(message = "message") },
            onError = {},
        )
    }

    private fun onSuccess(message: String?) {
        _state.update {
            it.copy(isLoading = false, error = null, success = message)
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

        val isNameChanged = edited.name != current.name
        val isImageChanged = edited.imagePath != current.imagePath
        val isNameNotEmpty = edited.name.isNotBlank()

        return isNameNotEmpty && (isNameChanged || isImageChanged)
    }
}