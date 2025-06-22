package com.sanaa.tudee_assistant.presentation.screen.category_task

import com.sanaa.tudee_assistant.presentation.state.TaskUiState

data class CategoryTaskScreenUiState(
    val isLoading: Boolean = false,
    val categoryId: Int = 1,
    val categoryName: String = "",
    val categoryImagePath: String = "",
    val isDefault: Boolean = true,
    val todoTasks: List<TaskUiState> = emptyList(),
    val inProgressTasks: List<TaskUiState> = emptyList(),
    val doneTasks: List<TaskUiState> = emptyList(),
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