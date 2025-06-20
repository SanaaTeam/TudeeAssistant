package com.sanaa.tudee_assistant.presentation.route

import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import kotlinx.serialization.Serializable

@Serializable
object HomeScreenRoute

@Serializable
object TasksScreenRoute

@Serializable
data class CategoriesScreenRoute(
    val taskUiStatus: TaskUiStatus,
)
