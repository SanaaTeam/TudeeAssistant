package com.sanaa.tudee_assistant.presentation.screen.tasks

import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import kotlinx.datetime.LocalDate

interface TaskInteractionListener {
    fun onDateSelected(date: LocalDate)
    fun onTaskClicked(task: TaskUiState)
    fun onTaskSwipeToDelete(task: TaskUiState): Boolean
    fun onDismissTaskDetails(show: Boolean)
    fun onDeleteTask()
    fun onTapClick(status: TaskUiStatus)
    fun onDeleteDismiss()
    fun onAddTaskSuccess()
    fun onEditTaskSuccess()
    fun handleOnMoveToStatusSuccess()
    fun handleOnMoveToStatusFail()
    fun onHideSnakeBar()
}
