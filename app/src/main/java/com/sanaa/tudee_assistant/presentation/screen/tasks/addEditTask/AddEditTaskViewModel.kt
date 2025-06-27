package com.sanaa.tudee_assistant.presentation.screen.tasks.addEditTask

import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.CategoryUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.model.mapper.toNewTask
import com.sanaa.tudee_assistant.presentation.model.mapper.toState
import com.sanaa.tudee_assistant.presentation.model.mapper.toStateList
import com.sanaa.tudee_assistant.presentation.model.mapper.toTask
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class AddEditTaskViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseViewModel<AddTaskUiState>(AddTaskUiState(), defaultDispatcher = dispatcher),
    AddEditTaskListener {

    private var originalTaskUiState: TaskUiState? = null
    private var isEditMode: Boolean = false

    private val _showDatePickerDialog = MutableStateFlow(false)
    val showDatePickerDialog: StateFlow<Boolean> = _showDatePickerDialog.asStateFlow()
    private var selectedDate: LocalDate? = null

    private var _isInitialized: Boolean = false

    init {
        viewModelScope.launch(defaultDispatcher) {
            categoryService.getCategories()
                .catch { e -> handleError(e) }
                .collect { categoryList ->
                    _state.update {
                        it.copy(
                            categories = categoryList.toStateList(0)
                        )
                    }
                }
        }
    }

    fun loadTask(task: TaskUiState) {
        isEditMode = true
        originalTaskUiState = task.copy()

        tryToExecute(
            callee = {
                val allCategories = categoryService.getCategories().firstOrNull() ?: emptyList()
                val selectedCategoryDomain = allCategories.find { it.id == task.categoryId }

                val categoriesUiState = allCategories.toStateList(0)
                val selectedCategoryUiState = selectedCategoryDomain?.toState(0)

                Pair(categoriesUiState, selectedCategoryUiState)
            },
            onSuccess = { (categoriesUiState, selectedCategoryUiState) ->

                _state.update { currentState ->
                    currentState.copy(
                        taskUiState = task,
                        categories = categoriesUiState,
                        selectedCategory = selectedCategoryUiState,
                        error = null,
                        isLoading = false
                    )
                }
                validateInputs()
            },
            onError = { exception ->
                handleError(exception)
            },
            dispatcher = defaultDispatcher
        )
    }

    fun loadCategoriesForNewTask() {
        isEditMode = false
        originalTaskUiState = null

        tryToExecute(
            callee = {
                categoryService.getCategories().firstOrNull() ?: emptyList()
            },
            onSuccess = { categories ->
                _state.update { state ->
                    state.copy(
                        categories = categories.toStateList(0),
                        taskUiState = TaskUiState(
                            id = 0,
                            title = "",
                            description = "",
                            dueDate = selectedDate?.toString() ?: "",
                            categoryId = -1,
                            priority = TaskUiPriority.LOW,
                            status = TaskUiStatus.TODO
                        )
                    )
                }
            },
            onError = { e -> handleError(e) },
            dispatcher = Dispatchers.IO
        )
    }

    override fun onTitleChange(title: String) {
        _state.update {
            it.copy(taskUiState = it.taskUiState.copy(title = title))
        }
        validateInputs()
    }

    override fun onDescriptionChange(description: String) {
        _state.update {
            it.copy(taskUiState = it.taskUiState.copy(description = description))
        }
        validateInputs()
    }

    override fun onDateSelected(date: LocalDate) {
        _state.update {
            it.copy(taskUiState = it.taskUiState.copy(dueDate = date.toString()))
        }
        validateInputs()
    }

    override fun onPrioritySelected(priority: TaskUiPriority) {
        _state.update {
            it.copy(taskUiState = it.taskUiState.copy(priority = priority))
        }
        validateInputs()
    }

    override fun onCategorySelected(category: CategoryUiState) {
        _state.update {
            it.copy(
                selectedCategory = category,
                taskUiState = it.taskUiState.copy(categoryId = category.id)
            )
        }
        validateInputs()
    }

    override fun onPrimaryButtonClick() {
        if (!state.value.isButtonEnabled) return
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

    fun initTaskState(isEditMode: Boolean, taskToEdit: TaskUiState?, initialDate: LocalDate?) {
        if (!isEditMode || !_isInitialized || this.isEditMode != isEditMode || this.originalTaskUiState != taskToEdit || this.selectedDate != initialDate) {
            this.isEditMode = isEditMode
            this.originalTaskUiState = taskToEdit
            this.selectedDate = initialDate

            if (!isEditMode) {
                resetState()
                loadCategoriesForNewTask()
            } else if (taskToEdit != null) {
                loadTask(taskToEdit)
            }
            _isInitialized = true
        }
    }


    private fun addTask() {
        _state.update { it.copy(isLoading = true, error = null) }
        tryToExecute(
            callee = {
                val newTask = state.value.taskUiState.copy(
                    status = TaskUiStatus.TODO
                ).toNewTask()
                taskService.addTask(newTask)
            },
            onSuccess = {
                _state.update { it.copy(isOperationSuccessful = true, isLoading = false) }
            },
            onError = { exception ->
                handleError(exception)
            },
            dispatcher = defaultDispatcher

        )
    }

    private fun updateTask() {
        _state.update { it.copy(isLoading = true, error = null) }
        tryToExecute(
            callee = {
                val task = state.value.taskUiState.toTask()
                taskService.updateTask(task)
            },
            onSuccess = {
                _state.update { it.copy(isOperationSuccessful = true, isLoading = false) }
            },
            onError = { exception ->
                handleError(exception)
            },
            dispatcher = defaultDispatcher

        )
    }

    fun resetState() {
        isEditMode = false
        originalTaskUiState = null
        _showDatePickerDialog.update { false }
        _state.update {
            AddTaskUiState(categories = it.categories)
        }
        _isInitialized = false
    }

    private fun handleError(e: Throwable) {
        _state.update {
            it.copy(isLoading = false, error = e.message ?: "Failed to process task")
        }
    }

    private fun validateInputs() {
        val currentState = _state.value
        val isButtonEnabled = if (isEditMode) {
            originalTaskUiState?.let { original ->
                currentState.taskUiState.title != original.title ||
                        currentState.taskUiState.description != original.description ||
                        currentState.taskUiState.dueDate != original.dueDate ||
                        currentState.taskUiState.priority != original.priority ||
                        currentState.taskUiState.categoryId != original.categoryId
            } ?: false
        } else {
            currentState.taskUiState.title.isNotBlank() && currentState.selectedCategory != null
        }

        _state.update {
            it.copy(isButtonEnabled = isButtonEnabled)
        }
    }
}