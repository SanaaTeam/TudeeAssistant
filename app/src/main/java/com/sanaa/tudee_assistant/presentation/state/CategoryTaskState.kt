package com.sanaa.tudee_assistant.presentation.state

import androidx.compose.ui.graphics.painter.Painter
import com.sanaa.tudee_assistant.presentation.model.TaskPriority
import com.sanaa.tudee_assistant.presentation.model.TaskStatus

data class CategoryTaskState(
    val icon: Painter,
    val title: String,
    val description: String? = null,
    val date: String? = null,
    val priority: TaskPriority = TaskPriority.LOW,
    val status: TaskStatus = TaskStatus.TODO,
)