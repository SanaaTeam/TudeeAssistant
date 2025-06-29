package com.sanaa.tudee_assistant.presentation.component.bottomSheet.task.taskDetailsBottomSheet

import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.utils.DateUtil

data class DetailsUiState(
    val id: Int = 0,
    val title: String = "",
    val description: String? = null,
    val dueDate: String = DateUtil.today.date.toString(),
    val priority: TaskUiPriority = TaskUiPriority.LOW,
    val status: TaskUiStatus = TaskUiStatus.IN_PROGRESS,
    val moveStatusToLabel: String = "",
    val categoryId: Int = 0,
    val categoryImagePath: String = "",
    val createdAt: String = DateUtil.today.toString(),
)

