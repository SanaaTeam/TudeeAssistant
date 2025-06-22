package com.sanaa.tudee_assistant.presentation.screen.category

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.ImageProcessor
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState
import com.sanaa.tudee_assistant.presentation.state.mapper.toNewCategory
import com.sanaa.tudee_assistant.presentation.state.mapper.toState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryService: CategoryService,
    private val taskService: TaskService,
    private val imageProcessor: ImageProcessor,

    ) : ViewModel() {

    private val _state = MutableStateFlow(CategoryScreenUiState())
    val state: StateFlow<CategoryScreenUiState>
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
                        .getTaskCountByCategoryId(category.id)
                        .first()
                    category.toState(taskCount)
                }

                _state.update {
                    it.copy(
                        currentDateCategory = mappedList, isLoading = false
                    )
                }
            }
        }
    }

    fun addNewCategory(categoryUiState: CategoryUiState) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val imagePath = imageProcessor.saveImageToInternalStorage(
                imageProcessor.processImage(categoryUiState.imagePath.toUri())
            )

            val newCategory = CategoryUiState(
                id = 0,
                name = categoryUiState.name,
                imagePath = imagePath,
                isDefault = false,
                tasksCount = 0,
            )

            categoryService.addCategory(newCategory.toNewCategory())
            _state.update { it.copy(isLoading = false) }
        }
    }
}
