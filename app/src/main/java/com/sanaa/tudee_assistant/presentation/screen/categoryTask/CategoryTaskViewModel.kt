package com.sanaa.tudee_assistant.presentation.screen.categoryTask

import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.state.mapper.toState
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

class CategoryTaskViewModel(
    val categoryService: CategoryService,
    val taskService: TaskService,
) : BaseViewModel<CategoryTaskScreenUiState>(initialState = CategoryTaskScreenUiState()),
    CategoryTaskInteractionListener {

    fun loadCategoryTasks(categoryId: Int) {
        tryToExecute(
            function = {
                _state.update { it.copy(isLoading = true) }
                val category = categoryService.getCategoryById(categoryId)
                val tasks = taskService.getTasksByCategoryId(categoryId).first()
                _state.update {
                    it.copy(
                        currentCategory = category.toState(tasksCount = tasks.size),
                        allCategoryTasks = tasks.map { task -> task.toState() }
                    )
                }
            },
            onError = { onError(message = "Error loading category tasks") },
            onSuccess = { onSuccess("Successfully loaded category tasks") }
        )
    }


    private fun onSuccess(message: String?) {
        _state.update {
            it.copy(
                isLoading = false,
                error = null,
                success = message
            )
        }
    }

    private fun onError(message: String?) {
        _state.update {
            it.copy(
                isLoading = false,
                error = message,
                success = null
            )
        }
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
        _state.update { it.copy(showEditCategoryBottomSheet = true) }
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
            0 -> TaskUiStatus.TODO
            1 -> TaskUiStatus.IN_PROGRESS
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
}