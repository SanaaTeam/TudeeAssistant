package com.sanaa.tudee_assistant.presentation.screen.category

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.ImageProcessor
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState
import com.sanaa.tudee_assistant.presentation.state.mapper.toNewCategory
import com.sanaa.tudee_assistant.presentation.state.mapper.toState
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

class CategoryViewModel(
    private val categoryService: CategoryService,
    private val taskService: TaskService,
    private val imageProcessor: ImageProcessor,

    ) : BaseViewModel<CategoryScreenUiState>(CategoryScreenUiState()), CategoryInteractionListener {

    init {
        loadCategoriesWithTasks()
    }

    private fun loadCategoriesWithTasks() {
        _state.update { it.copy(isLoading = true) }

        tryToExecute(
            { loadCategoriesWithTasksInternal() },
            ::onLoadCategoriesSuccess,
            ::onLoadCategoriesError
        )
    }

    private suspend fun loadCategoriesWithTasksInternal(): List<CategoryUiState> {
        val categoryList = categoryService.getCategories().first()

        return categoryList.map { category ->
            val taskCount = taskService
                .getTaskCountByCategoryId(category.id)
                .first()
            category.toState(taskCount)
        }
    }

    private fun onLoadCategoriesSuccess(categories: List<CategoryUiState>) {
        _state.update {
            it.copy(
                currentDateCategory = categories,
                isLoading = false
            )
        }
    }

    private fun onLoadCategoriesError(exception: Exception) {
        Log.e("Category", "Error Load category", exception)

        _state.update {
            it.copy(
                isLoading = false,
                errorMessage = "Failed to load categories "
            )
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

        val newCategory = CategoryUiState(
            id = 0,
            name = categoryUiState.name,
            imagePath = imagePath,
            isDefault = false,
            tasksCount = 0,
        )

        categoryService.addCategory(newCategory.toNewCategory())
    }

    private fun onAddCategorySuccess(unit: Unit) {
        _state.update {
            it.copy(
                isLoading = false,
                isAddCategorySheetVisible = false,
                isOperationSuccessful = true,
                successMessage = "Category added successfully"
            )
        }

        loadCategoriesWithTasks()
    }

    private fun onAddCategoryError(exception: Exception) {
        _state.update { it.copy(isLoading = false) }
        Log.e("Category", "Error adding category", exception)
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
                categoryTitle = title,
                currentCategory = it.currentCategory.copy(name = title)
            )
        }
    }

    override fun onCategoryImageSelected(uri: Uri?) {
        _state.update {
            it.copy(
                selectedImageUri = uri,
                currentCategory = it.currentCategory.copy(imagePath = uri?.toString().orEmpty())
            )
        }
    }

    override fun isFormValid(): Boolean {
        return _state.value.categoryTitle.isNotBlank()
    }


}
