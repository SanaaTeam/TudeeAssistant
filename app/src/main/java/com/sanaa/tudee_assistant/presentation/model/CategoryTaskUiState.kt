package com.sanaa.tudee_assistant.presentation.state

import com.sanaa.tudee_assistant.presentation.model.TaskUiModel

data class CategoryTaskUiState(
    val isLoading: Boolean = false,
    val categoryId: Int = 1,
    val categoryName: String = "",
    val categoryImagePath: String = "",
    val isDefault: Boolean = true,
    val todoTasks: List<TaskUiModel> = emptyList(),
    val inProgressTasks: List<TaskUiModel> = emptyList(),
    val doneTasks: List<TaskUiModel> = emptyList(),
    val error: String? = null
) {
    val todoTasksCount: Int get() = todoTasks.size
    val inProgressTasksCount: Int get() = inProgressTasks.size
    val doneTasksCount: Int get() = doneTasks.size
    val totalTasksCount: Int get() = todoTasksCount + inProgressTasksCount + doneTasksCount

    fun isEmptyState(): Boolean = totalTasksCount == 0 && !isLoading && error == null
    fun hasError(): Boolean = error != null
    fun canEdit(): Boolean = !isDefault && categoryId != -1
}