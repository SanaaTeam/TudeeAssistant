package com.sanaa.tudee_assistant.presentation.navigation

import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import kotlinx.serialization.Serializable

@Serializable
object HomeScreenRoute

@Serializable
data class TasksScreenRoute(
    val taskStatus: TaskUiStatus = TaskUiStatus.IN_PROGRESS
)

@Serializable
data class CategoriesScreenRoute(
    val taskUiStatus: TaskUiStatus,
)
