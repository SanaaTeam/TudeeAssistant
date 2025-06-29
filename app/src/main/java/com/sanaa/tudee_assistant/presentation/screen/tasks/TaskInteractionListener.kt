package com.sanaa.tudee_assistant.presentation.screen.tasks

import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import kotlinx.datetime.LocalDate

interface TaskInteractionListener {
    fun onDateSelected(date: LocalDate)
    fun onTaskClicked(task: TaskUiState)
    fun onTaskSwipeToDelete(task: TaskUiState): Boolean
    fun onDismissTaskDetails(show: Boolean)
    fun onDeleteTask()
    fun onDeleteDismiss()
    fun onAddTaskSuccess()
    fun onEditTaskSuccess()
    fun handleOnMoveToStatusSuccess()
    fun handleOnMoveToStatusFail()
    fun onHideSnakeBar()
}
