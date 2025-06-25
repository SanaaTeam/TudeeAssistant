package com.sanaa.tudee_assistant.presentation.screen.tasks

import com.sanaa.tudee_assistant.presentation.state.TaskUiState
import kotlinx.datetime.LocalDate

interface TaskInteractionListener {
    fun onDateSelected(date: LocalDate)
    fun onTaskClicked(task: TaskUiState)
    fun onTaskSwipeToDelete(task: TaskUiState): Boolean
    fun onDismissTaskDetails(show: Boolean)
    fun onDeleteTask()
    fun onDeleteDismiss()
    fun onShowSnackBar()
    fun handleOnMoveToStatusSuccess()
    fun handleOnMoveToStatusFail()
    fun onHideSnakeBar()
}
