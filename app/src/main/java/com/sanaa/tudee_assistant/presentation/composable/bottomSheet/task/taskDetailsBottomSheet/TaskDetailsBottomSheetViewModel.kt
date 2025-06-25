package com.sanaa.tudee_assistant.presentation.composable.bottomSheet.task.taskDetailsBottomSheet

import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface TaskDetailsInteractionListener{
    fun onMoveTaskToAnotherStatus(onMoveStatusSuccess: (TaskUiStatus) -> Unit, onMoveStatusFail: () -> Unit)
}
class TaskDetailsBottomSheetViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService,
    selectedTaskId: Int,
) : BaseViewModel<DetailsUiState>(DetailsUiState()),TaskDetailsInteractionListener{
    init {
        observeSelectedTask(selectedTaskId)
    }

    private fun observeSelectedTask(selectedTaskId: Int) {
        viewModelScope.launch {
            taskService.getTaskById(selectedTaskId).collect { task ->
                val categoryImagePath = categoryService.getCategoryById(task.categoryId).imagePath
                val detailsUiState = task.toDetailsState().copy(categoryImagePath = categoryImagePath)
                _state.update {
                    detailsUiState
                }
            }
        }
    }
    override fun onMoveTaskToAnotherStatus(onMoveStatusSuccess: (TaskUiStatus) -> Unit, onMoveStatusFail: () -> Unit) {
        viewModelScope.launch {
            var newUpdatedTask: Task
            state.value.let { state ->
                when (state.status) {
                    TaskUiStatus.TODO -> {
                        newUpdatedTask = state.copy(status = TaskUiStatus.IN_PROGRESS).toTask()
                        tryToExecute(
                            callee = { taskService.updateTask(newUpdatedTask) },
                            onSuccess = {
                                _state.update {
                                    it.copy(status = TaskUiStatus.IN_PROGRESS)
                                }
                                onMoveStatusSuccess(TaskUiStatus.IN_PROGRESS)
                            },
                            onError = {
                                onMoveStatusFail()
                            },
                            dispatcher = Dispatchers.IO
                        )
                    }

                    TaskUiStatus.IN_PROGRESS -> {
                        newUpdatedTask = state.copy(status = TaskUiStatus.DONE).toTask()
                        tryToExecute(
                            callee = { taskService.updateTask(newUpdatedTask) },
                            onSuccess = {
                                _state.update {
                                    it.copy(status = TaskUiStatus.DONE)
                                }
                                onMoveStatusSuccess(TaskUiStatus.DONE)
                            },
                            onError = {
                                onMoveStatusFail()
                            },
                            dispatcher = Dispatchers.IO
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}