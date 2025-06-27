package com.sanaa.tudee_assistant.presentation.navigation

import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import kotlinx.serialization.Serializable

open class MainScreenRoutes

@Serializable
object HomeScreenRoute : MainScreenRoutes()

@Serializable
data class TasksScreenRoute(
    val taskStatus: TaskUiStatus = TaskUiStatus.IN_PROGRESS,
) : MainScreenRoutes()

@Serializable
data class CategoriesScreenRoute(
    val taskUiStatus: TaskUiStatus,
) : MainScreenRoutes()
