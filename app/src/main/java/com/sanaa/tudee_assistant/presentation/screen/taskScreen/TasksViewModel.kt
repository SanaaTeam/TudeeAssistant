package com.sanaa.tudee_assistant.presentation.screen.taskScreen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.screen.taskScreen.mapper.toTask
import com.sanaa.tudee_assistant.presentation.screen.taskScreen.mapper.toUiModel
import com.sanaa.tudee_assistant.presentation.state.TaskUiModel
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate


class TaskViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService,
) : BaseViewModel<TasksScreenUiState>(TasksScreenUiState()) {

    lateinit var categories: List<Category>

    init {
        viewModelScope.launch {
            categoryService.getCategories().collect { categoryList ->
                categories = categoryList
            }
        }
        getTasksByDueDate()
    }

    private fun getTasksByDueDate() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            taskService.getTasksByDueDate(_state.value.selectedDate)
                .collect { taskList ->
                    _state.update {
                        it.copy(
                            currentDateTasks = taskList.map { task ->
                                task.toUiModel(
                                    categories.firstOrNull { category ->
                                        category.id == task.categoryId
                                    }?.imagePath ?: ""
                                )
                            },
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onTaskStatusSelectedChange(taskUiStatus: TaskUiStatus) {
        _state.update { it.copy(selectedTaskUiStatus = taskUiStatus) }
    }

    fun onTaskSelected(task: TaskUiModel) {
        _state.update { it.copy(selectedTask = task) }
    }

    fun onTaskClick(task: TaskUiModel) {
        onTaskSelected(task)
        onShowTaskDetailsDialogChange(true)
    }

    fun onTaskDeleted() {
        _state.value.selectedTask.let {
            viewModelScope.launch {
                _state.update { it.copy(isLoading = true) }
                runCatching {
                    if (it?.id == null) return@launch
                    taskService.deleteTaskById(it.id)
                }.onSuccess {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            showDeleteDialog = false,
                            selectedTask = null,
                        )
                    }
                    getTasksByDueDate()
                }.onFailure {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            showDeleteDialog = false,
                            selectedTask = null,
                        )
                    }
                }
            }
        }
    }

    fun onDueDateChange(date: LocalDate) {
        _state.update {
            it.copy(selectedDate = date).also { Log.i("test", date.toString()) }
        }
        getTasksByDueDate()
    }

    fun onShowDeleteDialogChange(show: Boolean) {
        _state.update { it.copy(showDeleteDialog = show) }
    }

    fun onShowTaskDetailsDialogChange(show: Boolean) {
        _state.update { it.copy(showTaskDetailsDialog = show) }
    }

    fun onShowEditDialogChange(show: Boolean) {
        _state.update { it.copy(showEditDialog = show) }
    }

    fun onEditTask(taskUiModel: TaskUiModel) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            runCatching {
                taskService.updateTask(taskUiModel.toTask())
            }.onSuccess {
                _state.update {
                    it.copy(
                        isLoading = false,
                        showEditDialog = false,
                        selectedTask = null,
                    )
                }
                getTasksByDueDate()
            }.onFailure {
                _state.update {
                    it.copy(
                        isLoading = false,
                        showEditDialog = false,
                        selectedTask = null,
                    )
                }
            }
        }
    }


    fun onTaskSwipeToDelete(task: TaskUiModel) {
        onTaskSelected(task)
        onShowDeleteDialogChange(true)
    }
}
