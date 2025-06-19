package com.sanaa.tudee_assistant.presentation.screens.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryService: CategoryService,
    private val taskService: TaskService
) : ViewModel() {

    private val _state = MutableStateFlow(CategoryUiState())
    val state: StateFlow<CategoryUiState>
        get() = _state

    init {
        loadCategoriesWithTasks()
    }

    private fun loadCategoriesWithTasks() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            categoryService.getCategories().collect { categoryList ->
                val mappedList = categoryList.map { category ->
                    val taskCount = taskService
                        .getTaskCountByCategoryId(category.id ?: 0)
                        .first()
                    category.toUiState(taskCount)
                }

                _state.update {
                    it.copy(
                        currentDateCategory = mappedList, isLoading = false
                    )
                }
            }
        }
    }

    fun addNewCategory(categoryUiModel: CategoryUiModel) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            categoryService.addCategory(categoryUiModel.toCategory())

            _state.update { it.copy(isLoading = false) }
        }
    }
}
