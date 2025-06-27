package com.sanaa.tudee_assistant.presentation.composable.bottomSheet.task.taskDetailsBottomSheet

import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.StringProvider
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.model.mapper.toDetailsState
import com.sanaa.tudee_assistant.presentation.model.mapper.toTask
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface TaskDetailsInteractionListener{
    fun onMoveTaskToAnotherStatus(onMoveStatusSuccess: () -> Unit, onMoveStatusFail: () -> Unit)
}
class TaskDetailsBottomSheetViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService,
    private val stringProvider: StringProvider,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseViewModel<DetailsUiState>(DetailsUiState(), defaultDispatcher = Dispatchers.IO),
    TaskDetailsInteractionListener {

     fun getSelectedTask(selectedTaskId: Int) {
        viewModelScope.launch {
            var task: Task?
             taskService.getTaskById(selectedTaskId).collectLatest {
                 it?.let {
                     val categoryImagePath = categoryService.getCategoryById(it.categoryId).imagePath
                     val detailsUiState = it.toDetailsState().copy(
                         categoryImagePath = categoryImagePath,
                         moveStatusToLabel = when (it.status) {
                             Task.TaskStatus.TODO -> stringProvider.markAsInProgress
                             Task.TaskStatus.IN_PROGRESS -> stringProvider.markAsDone
                             Task.TaskStatus.DONE -> ""
                         }
                     )
                     _state.update {
                         detailsUiState
                     }
                 }


            }

        }
    }

    override fun onMoveTaskToAnotherStatus(
        onMoveStatusSuccess: () -> Unit,
        onMoveStatusFail: () -> Unit
    ) {
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
                                onMoveStatusSuccess()
                            },
                            onError = {
                                onMoveStatusFail()
                            },
                            dispatcher = dispatcher
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
                                onMoveStatusSuccess()
                            },
                            onError = {
                                onMoveStatusFail()
                            },
                            dispatcher = dispatcher
                        )
                    }
                    else -> {}
                }
            }

    }
}