package com.sanaa.tudee_assistant.presentation.screen.category

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.ImageProcessor
import com.sanaa.tudee_assistant.domain.service.StringProvider
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.base.BaseViewModel
import com.sanaa.tudee_assistant.presentation.model.CategoryUiState
import com.sanaa.tudee_assistant.presentation.model.SnackBarState
import com.sanaa.tudee_assistant.presentation.model.mapper.toCreationRequest
import com.sanaa.tudee_assistant.presentation.model.mapper.toState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryService: CategoryService,
    private val taskService: TaskService,
    private val imageProcessor: ImageProcessor,
    private val stringProvider: StringProvider,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseViewModel<CategoryScreenUiState>(CategoryScreenUiState(), dispatcher),
    CategoryInteractionListener {

    init {
        loadCategoriesWithTasksCount()
    }

    private val _effect = MutableSharedFlow<CategoryEffects>()
    val effect = _effect.asSharedFlow()

    private fun loadCategoriesWithTasksCount() {
        updateState { it.copy(isLoading = true) }

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
                    updateState {
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
        updateState {
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
        updateState { it.copy(isLoading = true) }

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
        categoryService.addCategory(newCategory.toCreationRequest())
    }

    private fun onAddCategorySuccess(unit: Unit) {
        updateState {
            it.copy(
                isLoading = false,
                isAddCategorySheetVisible = false,
                snackBarState = SnackBarState.getInstance(stringProvider.categoryAddedSuccessfully),
                newCategory = CategoryUiState()
            )
        }
    }

    private fun onAddCategoryError(exception: Exception) {
        updateState { it.copy(isLoading = false) }
    }

    override fun onToggleAddCategorySheet(isVisible: Boolean) {
        updateState { it.copy(isAddCategorySheetVisible = isVisible) }
    }

    override fun onCategoryTitleChange(title: String) {
        updateState {
            it.copy(
                newCategory = it.newCategory.copy(name = title)
            )
        }
    }

    override fun onCategoryImageSelected(uri: Uri?) {
        updateState {
            it.copy(
                newCategory = it.newCategory.copy(imagePath = uri?.toString().orEmpty())
            )
        }
    }

    override fun isFormValid(): Boolean {
        return (
                (state.value.newCategory.name.isNotBlank()
                        && (state.value.newCategory.name.length in 2..24))
                        && (state.value.newCategory.imagePath.isNotBlank()))

    }

    override fun onHideSnakeBar() {
        updateState {
            it.copy(snackBarState = SnackBarState.hide())
        }
    }

    override fun onCategoryClicked(categoryId: Int) {
        viewModelScope.launch {
            _effect.emit(CategoryEffects.NavigateToCategoryTasks(categoryId))
        }
    }

    // Only used in unit tests
    fun onCategoryImageSelectedFromPath(path: String) {
        updateState {
            it.copy(
                newCategory = it.newCategory.copy(imagePath = path)
            )
        }
    }
}
