package com.sanaa.tudee_assistant.presentation.composable.bottomSheet.task.taskDetailsBottomSheet

import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class DetailsUiState(
    val id: Int = 0,
    val title: String = "",
    val description: String? = null,
    val dueDate: String = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date.toString(),
    val priority: TaskUiPriority = TaskUiPriority.LOW,
    val status: TaskUiStatus = TaskUiStatus.IN_PROGRESS,
    val moveStatusToLabel: String = "",
    val categoryId: Int = 0,
    val categoryImagePath: String = "",
    val createdAt: String = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).toString(),
)

