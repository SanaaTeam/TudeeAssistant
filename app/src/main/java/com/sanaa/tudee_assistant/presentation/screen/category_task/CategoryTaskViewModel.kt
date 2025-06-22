package com.sanaa.tudee_assistant.presentation.screen.category_task

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.state.mapper.toState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryTaskViewModel(
    private val categoryService: CategoryService,
    private val taskService: TaskService
) : ViewModel() {

    private val _state = MutableStateFlow(CategoryTaskScreenUiState())
    val state: StateFlow<CategoryTaskScreenUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val category = categoryService.getCategoryById(_state.value.categoryId)
            _state.update {
                _state.value.copy(
                    categoryName = category.name,
                    categoryImagePath = category.imagePath,
                    isDefault = category.isDefault,
                )
            }
        }
    }

    fun loadCategoryTasks(categoryId: Int) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, error = null)

                val category = categoryService.getCategoryById(categoryId)

                _state.value = _state.value.copy(
                    categoryId = categoryId,
                    categoryName = category.name,
                    categoryImagePath = category.imagePath,
                    isDefault = category.isDefault
                )

                taskService.getTasksByCategoryId(categoryId)
                    .catch { exception ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = exception.message ?: "Failed to load tasks"
                        )
                    }
                    .collect { tasks ->
                        val uiTasks = tasks.map { it.toState() }

                        _state.value = _state.value.copy(
                            isLoading = false,
                            todoTasks = uiTasks.filter { it.status == TaskUiStatus.TODO },
                            inProgressTasks = uiTasks.filter { it.status == TaskUiStatus.IN_PROGRESS },
                            doneTasks = uiTasks.filter { it.status == TaskUiStatus.DONE },
                            error = null
                        )
                    }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load category tasks"
                )
            }
        }
    }

    fun updateCategory(newName: String, uri: Uri?) {
        viewModelScope.launch {
            try {
                val currentCategory = categoryService.getCategoryById(_state.value.categoryId)
                val updatedCategory = currentCategory.copy(
                    name = _state.value.categoryName,
                    imagePath = _state.value.categoryImagePath
                )

                categoryService.updateCategory(updatedCategory)

                _state.value = _state.value.copy(
                    categoryName = updatedCategory.name,
                    categoryImagePath = updatedCategory.imagePath,
                    error = null
                )

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Failed to update category"
                )
            }
        }
    }

    fun deleteCategory(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, error = null) }

                val categoryId = _state.value.categoryId

                if (_state.value.isDefault) {
                    throw Exception("Cannot delete default category")
                }

                val tasks = taskService.getTasksByCategoryId(categoryId)
                tasks.collect { taskList ->
                    taskList.forEach { task ->
                        task.id?.let { taskId ->
                            taskService.deleteTaskById(taskId)
                        }
                    }

                    categoryService.deleteCategoryById(categoryId)

                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = null
                        )
                    }

                    onSuccess()
                    return@collect
                }

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to delete category"
                    )
                }
            }
        }
    }
}