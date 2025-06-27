package com.sanaa.tudee_assistant.presentation.screen.category

import android.net.Uri
import androidx.core.net.toUri
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.ImageProcessor
import com.sanaa.tudee_assistant.domain.service.StringProvider
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.CategoryUiState
import com.sanaa.tudee_assistant.presentation.model.SnackBarState
import com.sanaa.tudee_assistant.presentation.model.mapper.toNewCategory
import com.sanaa.tudee_assistant.presentation.model.mapper.toState
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class CategoryViewModel(
    private val categoryService: CategoryService,
    private val taskService: TaskService,
    private val imageProcessor: ImageProcessor,
    private val stringProvider: StringProvider,
) : BaseViewModel<CategoryScreenUiState>(CategoryScreenUiState()), CategoryInteractionListener {

    init {
        loadCategoriesWithTasksCount()
    }

    private fun loadCategoriesWithTasksCount() {
        _state.update { it.copy(isLoading = true) }

        tryToExecute(
            callee = ::loadCategoriesWithTasksCountOperation,
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
        tryToExecute(
            callee = {
                flow.collect { mappedList ->
                    _state.update {
                        it.copy(
                            allCategories = mappedList,
                            isLoading = false
                        )
                    }
                }
            }
        )
    }

    private fun onLoadCategoriesWithTasksCountError(exception: Exception) {
        _state.update {
            it.copy(
                isLoading = false,
                snackBarState = SnackBarState.getErrorInstance(
                    exception.message
                        ?: stringProvider.unknownError
                )
            )
        }
    }

    override fun onAddCategory(newCategory: CategoryUiState) {
        _state.update { it.copy(isLoading = true) }

        tryToExecute(
            { addCategory(newCategory) },
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
                snackBarState = SnackBarState.getInstance(stringProvider.categoryAddedSuccessfully),
                newCategory = CategoryUiState()
            )
        }
    }

    private fun onAddCategoryError(exception: Exception) {
        _state.update { it.copy(isLoading = false) }
    }

    override fun onToggleAddCategorySheet(isVisible: Boolean) {
        _state.update { it.copy(isAddCategorySheetVisible = isVisible) }
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
                        && (_state.value.newCategory.name.length in 2..24))
                        && (_state.value.newCategory.imagePath.isNotBlank()))

    }

    override fun onHideSnakeBar() {
        _state.update {
            it.copy(snackBarState = SnackBarState.hide())
        }
    }
}
