package com.sanaa.tudee_assistant.presentation.screen.tasks.addEditTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState
import com.sanaa.tudee_assistant.presentation.state.TaskUiState
import com.sanaa.tudee_assistant.presentation.state.mapper.toNewTask
import com.sanaa.tudee_assistant.presentation.state.mapper.toState
import com.sanaa.tudee_assistant.presentation.state.mapper.toTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class AddEditTaskViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService,
) : ViewModel(), AddEditTaskListener {

    private val _uiState = MutableStateFlow(AddTaskUiState())
    val uiState: StateFlow<AddTaskUiState> = _uiState.asStateFlow()

    private var originalTaskUiState: TaskUiState? = null
    private var isEditMode: Boolean = false

    private val _showDatePickerDialog = MutableStateFlow(false)
    val showDatePickerDialog: StateFlow<Boolean> = _showDatePickerDialog.asStateFlow()

    init {
        viewModelScope.launch {
            categoryService.getCategories().collect { categoryList ->
                _uiState.update {
                    it.copy(
                        categories = categoryList.map { category -> category.toState(0) }
                    )
                }
            }
        }
    }

    fun loadTask(task: TaskUiState) {
        isEditMode = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                taskService.getTaskCountByCategoryId(task.categoryId).collect { taskCount ->
                    val category = categoryService
                        .getCategoryById(task.categoryId).toState(taskCount)
                    originalTaskUiState = task.copy()
                    _uiState.update {
                        it.copy(taskUiState = task, selectedCategory = category)
                    }
                    validateInputs()
                    categoryService.getCategories().collect { categories ->
                        _uiState.update { state ->
                            state.copy(categories = categories.map { it.toState(taskCount) })
                        }
                    }
                }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun loadCategoriesForNewTask() {
        isEditMode = false
        originalTaskUiState = null
        viewModelScope.launch(Dispatchers.IO) {
            try {
                categoryService.getCategories().collect { categories ->
                    _uiState.update { state ->
                        state.copy(categories = categories.map { it.toState(0) })
                    }
                }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    override fun onTitleChange(title: String) {
        _uiState.update {
            it.copy(taskUiState = it.taskUiState.copy(title = title))
        }
        validateInputs()
    }

    override fun onDescriptionChange(description: String) {
        _uiState.update {
            it.copy(taskUiState = it.taskUiState.copy(description = description))
        }
        validateInputs()
    }

    override fun onDateSelected(date: LocalDate) {
        _uiState.update {
            it.copy(taskUiState = it.taskUiState.copy(dueDate = date.toString()))
        }
        validateInputs()
    }

    override fun onPrioritySelected(priority: TaskUiPriority) {
        _uiState.update {
            it.copy(taskUiState = it.taskUiState.copy(priority = priority))
        }
        validateInputs()
    }

    override fun onCategorySelected(category: CategoryUiState) {
        _uiState.update {
            it.copy(
                selectedCategory = category,
                taskUiState = it.taskUiState.copy(categoryId = category.id)
            )
        }
        validateInputs()
    }

    override fun onPrimaryButtonClick() {
        if (!uiState.value.isButtonEnabled) return
        if (isEditMode) {
            updateTask()
        } else {
            addTask()
        }
    }

    override fun onDatePickerShow() {
        _showDatePickerDialog.update { true }
    }

    override fun onDatePickerDismiss() {
        _showDatePickerDialog.update { false }
    }

    private fun addTask() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }
                val newTask = uiState.value.taskUiState.copy(
                    status = TaskUiStatus.TODO
                ).toNewTask()
                taskService.addTask(newTask)
                _uiState.update { it.copy(isOperationSuccessful = true, isLoading = false) }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun updateTask() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }
                val task = uiState.value.taskUiState.toTask()
                taskService.updateTask(task)
                _uiState.update { it.copy(isOperationSuccessful = true, isLoading = false) }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun resetState() {
        isEditMode = false
        originalTaskUiState = null
        _showDatePickerDialog.update { false }
        _uiState.update {
            AddTaskUiState(categories = it.categories)
        }
    }

    private fun handleError(e: Exception) {
        _uiState.update {
            it.copy(isLoading = false, error = e.message ?: "Failed to process task")
        }
    }

    private fun validateInputs() {
        val state = _uiState.value
        val isButtonEnabled = if (isEditMode) {
            originalTaskUiState?.let { original ->
                state.taskUiState.title != original.title ||
                        state.taskUiState.description != original.description ||
                        state.taskUiState.dueDate != original.dueDate ||
                        state.taskUiState.priority != original.priority ||
                        state.taskUiState.categoryId != original.categoryId
            } ?: false
        } else {
            state.taskUiState.title.isNotBlank() && state.selectedCategory != null
        }

        _uiState.update {
            it.copy(isButtonEnabled = isButtonEnabled)
        }
    }
}