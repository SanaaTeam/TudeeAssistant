package com.sanaa.tudee_assistant.presentation.screen.category

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.ImageProcessor
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState
import com.sanaa.tudee_assistant.presentation.state.mapper.toNewCategory
import com.sanaa.tudee_assistant.presentation.state.mapper.toState
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryService: CategoryService,
    private val taskService: TaskService,
    private val imageProcessor: ImageProcessor,

    ) : BaseViewModel<CategoryScreenUiState>(CategoryScreenUiState()), CategoryInteractionListener {

    init {
        loadCategoriesWithTasksCount()
    }

    fun loadCategoriesWithTasksCount() {
        _state.update { it.copy(isLoading = true) }

        tryToExecute(
            function = ::loadCategoriesWithTasksCountOperation,
            onSuccess = ::onLoadCategoriesWithTasksCountSuccess,
            onError = ::onLoadCategoriesWithTasksCountError
        )
    }

    private fun loadCategoriesWithTasksCountOperation() = combine(
        categoryService.getCategories(),
        taskService.getTaskCountsGroupedByCategoryId()
    ) { categories, taskCountsMap ->
        categories.map { category ->
            val count = taskCountsMap[category.id] ?: 0
            category.toState(count)
        }
    }

    private fun onLoadCategoriesWithTasksCountSuccess(flow: Flow<List<CategoryUiState>>) {
        viewModelScope.launch {
            flow.collect { mappedList ->
                _state.update {
                    it.copy(
                        allCategories = mappedList,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun onLoadCategoriesWithTasksCountError(exception: Exception) {
        _state.update {
            it.copy(isLoading = false, errorMessage = exception.message)
        }
    }

    override fun onAddCategory(categoryUiState: CategoryUiState) {
        _state.update { it.copy(isLoading = true) }

        tryToExecute(
            { addCategory(categoryUiState) },
            ::onAddCategorySuccess,
            ::onAddCategoryError,
        )
    }

    private suspend fun addCategory(categoryUiState: CategoryUiState) {
        val imagePath = imageProcessor.saveImageToInternalStorage(
            imageProcessor.processImage(categoryUiState.imagePath.toUri())
        )
        val newCategory = categoryUiState.copy(imagePath = imagePath)
        categoryService.addCategory(newCategory.toNewCategory())
    }

    private fun onAddCategorySuccess(unit: Unit) {
        _state.update {
            it.copy(
                isLoading = false,
                isAddCategorySheetVisible = false,
                isOperationSuccessful = true,
                successMessage = "Category added successfully",
                newCategory = CategoryUiState()
            )
        }

        //     loadCategoriesWithTasksCount()
    }

    private fun onAddCategoryError(exception: Exception) {
        _state.update { it.copy(isLoading = false) }
    }

    override fun onToggleAddCategorySheet(isVisible: Boolean) {
        _state.update { it.copy(isAddCategorySheetVisible = isVisible) }
    }

    override fun onSnackBarShown() {
        _state.update {
            it.copy(successMessage = null, errorMessage = null)
        }
    }

    override fun onCategoryTitleChange(title: String) {
        _state.update {
            it.copy(
                newCategory = it.newCategory.copy(name = title)
            )
        }
    }

    override fun onCategoryImageSelected(uri: Uri?) {
        _state.update {
            it.copy(
                newCategory = it.newCategory.copy(imagePath = uri?.toString().orEmpty())
            )
        }
    }

    override fun isFormValid(): Boolean {
        return (
                (_state.value.newCategory.name.isNotBlank()
                        && (_state.value.newCategory.name.length >= 2
                        && _state.value.newCategory.name.length <= 24))
                        && (_state.value.newCategory.imagePath.isNotBlank()))

    }

    override fun onShowSnackbar() {
        _state.update {
            it.copy(successMessage = null, errorMessage = null)
        }
    }
}
