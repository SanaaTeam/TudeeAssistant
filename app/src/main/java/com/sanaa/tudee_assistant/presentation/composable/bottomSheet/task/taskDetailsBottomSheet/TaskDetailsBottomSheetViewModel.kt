package com.sanaa.tudee_assistant.presentation.composable.bottomSheet.task.taskDetailsBottomSheet

import android.util.Log
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
    fun onMoveTaskToAnotherStatus(onMoveStatusSuccess: () -> Unit, onMoveStatusFail: () -> Unit)
}
class TaskDetailsBottomSheetViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService,
    private val selectedTaskId: Int,
) : BaseViewModel<DetailsUiState>(DetailsUiState()),TaskDetailsInteractionListener{


    init {
        getSelectedTask(selectedTaskId)
    }

    private fun getSelectedTask(selectedTaskId: Int) {
        viewModelScope.launch {
            val task: Task = taskService.getTaskById(selectedTaskId)
            val categoryImagePath = categoryService.getCategoryById(task.categoryId).imagePath
            val detailsUiState = task.toDetailsState().copy(categoryImagePath = categoryImagePath)
            _state.update {
                detailsUiState
            }
        }
    }



    override fun onMoveTaskToAnotherStatus(onMoveStatusSuccess: () -> Unit, onMoveStatusFail: () -> Unit) {
        viewModelScope.launch {
            var newUpdatedTask: Task
            state.value.let {
                when (it.status) {
                    TaskUiStatus.TODO -> {
                        newUpdatedTask = it.copy(status = TaskUiStatus.IN_PROGRESS).toTask()
                        tryToExecute(
                            function = { taskService.updateTask(newUpdatedTask) },
                            onSuccess = {
                                _state.update {
                                    it.copy(status = TaskUiStatus.IN_PROGRESS)
                                }
                                onMoveStatusSuccess()
                                Log.d(
                                    "testing snack bar message : success",
                                    "updateSelectedTaskStatus: "
                                )
                            },
                            onError = {
                                onMoveStatusFail()
                                Log.d(
                                    "testing snack bar message : failure",
                                    "updateSelectedTaskStatus: "
                                )
                            },
                            dispatcher = Dispatchers.IO
                        )
                    }

                    TaskUiStatus.IN_PROGRESS -> {
                        newUpdatedTask = it.copy(status = TaskUiStatus.DONE).toTask()
                        tryToExecute(
                            function = { taskService.updateTask(newUpdatedTask) },
                            onSuccess = {
                                _state.update {
                                    it.copy(status = TaskUiStatus.DONE)
                                }
                                onMoveStatusSuccess()
                                Log.d(
                                    "testing snack bar message : success",
                                    "updateSelectedTaskStatus: "
                                )
                            },
                            onError = {
                                onMoveStatusFail()
                                Log.d(
                                    "testing snack bar message : failure",
                                    "updateSelectedTaskStatus: "
                                )
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