package com.sanaa.tudee_assistant.presentation.screen.tasks.addEditTask

import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.base.BaseViewModel
import com.sanaa.tudee_assistant.presentation.model.CategoryUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.model.mapper.toCreationRequest
import com.sanaa.tudee_assistant.presentation.model.mapper.toDomain
import com.sanaa.tudee_assistant.presentation.model.mapper.toState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate

class AddEditTaskViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseViewModel<AddTaskUiState>(AddTaskUiState(), defaultDispatcher = dispatcher),
    AddEditTaskListener {

    private var originalTaskUiState: TaskUiState? = null
    private var isEditMode: Boolean = false

    private val _showDatePickerDialog = MutableStateFlow(false)
    val showDatePickerDialog: StateFlow<Boolean> = _showDatePickerDialog.asStateFlow()
    private var selectedDate: LocalDate? = null

    private var _isInitialized: Boolean = false

    init {
        tryToExecute(
            callee = {
                categoryService.getCategories()
                    .catch { e -> handleError(e) }
                    .collect { categoryList ->
                        updateState {
                            it.copy(
                                categories = categoryList.toState(0)
                            )
                        }
                    }
            }
        )
    }

    private fun loadTask(task: TaskUiState) {
        isEditMode = true
        originalTaskUiState = task.copy()

        tryToExecute(
            callee = {
                val allCategories = categoryService.getCategories().firstOrNull() ?: emptyList()
                val selectedCategoryDomain = allCategories.find { it.id == task.categoryId }

                val categoriesUiState = allCategories.toState(0)
                val selectedCategoryUiState = selectedCategoryDomain?.toState(0)

                Pair(categoriesUiState, selectedCategoryUiState)
            },
            onSuccess = { (categoriesUiState, selectedCategoryUiState) ->

                updateState { currentState ->
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

    private fun loadCategoriesForNewTask() {
        isEditMode = false
        originalTaskUiState = null

        tryToExecute(
            callee = {
                categoryService.getCategories().firstOrNull() ?: emptyList()
            },
            onSuccess = { categories ->
                updateState { state ->
                    state.copy(
                        categories = categories.toState(0),
                        taskUiState = TaskUiState(
                            id = 0,
                            title = "",
                            description = "",
                            dueDate = selectedDate?.toString().orEmpty(),
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
        updateState {
            it.copy(taskUiState = it.taskUiState.copy(title = title))
        }
        validateInputs()
    }

    override fun onDescriptionChange(description: String) {
        updateState {
            it.copy(taskUiState = it.taskUiState.copy(description = description))
        }
        validateInputs()
    }

    override fun onDateSelected(date: LocalDate) {
        updateState {
            it.copy(taskUiState = it.taskUiState.copy(dueDate = date.toString()))
        }
        validateInputs()
    }

    override fun onPrioritySelected(priority: TaskUiPriority) {
        updateState {
            it.copy(taskUiState = it.taskUiState.copy(priority = priority))
        }
        validateInputs()
    }

    override fun onCategorySelected(category: CategoryUiState) {
        updateState {
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
        if (_isInitialized) return

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


    private fun addTask() {
        updateState { it.copy(isLoading = true, error = null) }
        tryToExecute(
            callee = {
                val newTask = state.value.taskUiState.copy(
                    status = TaskUiStatus.TODO
                ).toCreationRequest()
                taskService.addTask(newTask)
            },
            onSuccess = {
                updateState { it.copy(isOperationSuccessful = true, isLoading = false) }
            },
            onError = { exception ->
                handleError(exception)
            },
            dispatcher = defaultDispatcher

        )
    }

    private fun updateTask() {
        updateState { it.copy(isLoading = true, error = null) }
        tryToExecute(
            callee = {
                val task = state.value.taskUiState.toDomain()
                taskService.updateTask(task)
            },
            onSuccess = {
                updateState { it.copy(isOperationSuccessful = true, isLoading = false) }
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
        updateState {
            AddTaskUiState(categories = it.categories)
        }
        _isInitialized = false
    }

    private fun handleError(e: Throwable) {
        updateState {
            it.copy(isLoading = false, error = e.message ?: "Failed to process task")
        }
    }

    private fun validateInputs() {
        val currentState = state.value
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

        updateState {
            it.copy(isButtonEnabled = isButtonEnabled)
        }
    }
}