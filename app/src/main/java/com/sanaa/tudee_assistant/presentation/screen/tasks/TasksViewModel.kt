package com.sanaa.tudee_assistant.presentation.screen.tasks

import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.state.TaskUiState
import com.sanaa.tudee_assistant.presentation.state.mapper.toState
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate


class TaskViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService,
    private val selectedStatusTab: TaskUiStatus
) : BaseViewModel<TasksScreenUiState>(TasksScreenUiState()), TaskInteractionListener {


    init {
        _state.update { it.copy(selectedStatusTab = selectedStatusTab) }
        viewModelScope.launch {
            categoryService.getCategories().collect { categoryList ->
                _state.update {
                    it.copy(
                        categories = categoryList.map { category -> category.toState(0) }
                    )
                }

            }
        }
        getTasksByDueDate()
    }

    private var dateJob: Job? = null
    private fun getTasksByDueDate() {

        dateJob?.takeIf { it.isActive }?.cancel()

           dateJob =  viewModelScope.launch {
                taskService.getTasksByDueDate(_state.value.selectedDate)
                    .collect { taskList ->
                        _state.update {
                            it.copy(
                                currentDateTasks = taskList.map { task ->
                                    task.toState() },
                            )
                        }
                    }

        }
    }



    fun onTaskSelected(task: TaskUiState) {
        _state.update { it.copy(selectedTask = task) }
    }

    override fun onTaskClicked(task: TaskUiState) {
        onTaskSelected(task)
        onDismissTaskDetails(true)
    }

    override fun onDeleteTask() {
        _state.value.selectedTask.let {
            viewModelScope.launch {
                runCatching {
                    if (it?.id == null) return@launch
                    taskService.deleteTaskById(it.id)
                }.onSuccess {
                    handleOnSuccess(messageStringId = R.string.task_delete_success)
                    getTasksByDueDate()
                }.onFailure {
                    handleOnError( messageStringId = R.string.snack_bar_error)

                }
            }
        }
    }

    override fun onDateSelected(date: LocalDate) {
        _state.update {
            it.copy(selectedDate = date)
        }
        getTasksByDueDate()
    }

    fun onShowDeleteDialogChange(show: Boolean) {
        _state.update { it.copy(showDeleteTaskBottomSheet = show) }
    }

    override fun onDismissTaskDetails(show: Boolean) {
        _state.update { it.copy(showTaskDetailsBottomSheet = show) }
    }

    override fun onShowSnackbar() {
        _state.update {
            it.copy(successMessageStringId = null, errorMessageStringId = null)
        }
    }

    override fun onDeleteDismiss() {
        _state.update { it.copy(showDeleteTaskBottomSheet = false) }
    }

    override fun onTaskSwipeToDelete(task: TaskUiState): Boolean {
        viewModelScope.launch {
            onTaskSelected(task)
            onShowDeleteDialogChange(true)
        }
        return false
    }

//    override fun onMoveTaskToAnotherStatus() {
//        viewModelScope.launch {
//            var newUpdatedTask: Task
//            state.value.selectedTask?.let {
//               when(it.status){
//                   TaskUiStatus.TODO -> {
//                       newUpdatedTask = it.copy(status = TaskUiStatus.IN_PROGRESS ).toTask()
//                       updateSelectedTaskStatus(newUpdatedTask)
//                   }
//                   TaskUiStatus.IN_PROGRESS -> {
//                       newUpdatedTask = it.copy(status = TaskUiStatus.DONE ).toTask()
//                       updateSelectedTaskStatus(newUpdatedTask)
//                   }
//                   TaskUiStatus.DONE -> {
//                       newUpdatedTask = it.copy(status = TaskUiStatus.DONE ).toTask()
//                       updateSelectedTaskStatus(newUpdatedTask)
//                   }
//               }
//            }
//        }
//    }
//
//    private suspend fun updateSelectedTaskStatus(newUpdatedTask: Task){
//        runCatching {
//            taskService.updateTask(newUpdatedTask)
//        }.onSuccess {
//        }.onFailure { handleOnError()}
//    }

    override fun handleOnMoveToStatusSuccess(){
        handleOnSuccess(messageStringId = R.string.task_update_success)
    }
    override fun handleOnMoveToStatusFail(){
        handleOnError()
    }


    private fun handleOnSuccess(messageStringId: Int? = null) {
        _state.update {
            it.copy(
                successMessageStringId = messageStringId,
                errorMessageStringId = null,
                showTaskDetailsBottomSheet = false,
                showDeleteTaskBottomSheet = false
            )
        }
    }

    private fun handleOnError(messageStringId: Int? = null) {
        _state.update {
            it.copy(
                successMessageStringId = null,
                errorMessageStringId = messageStringId,
                showTaskDetailsBottomSheet = false,
                showDeleteTaskBottomSheet = false
            )
        }
    }
}