package com.sanaa.tudee_assistant.presentation.screen.home

import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.screens.category.CategoryUiModel
import com.sanaa.tudee_assistant.presentation.state.TaskUiState
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class HomeScreenUiState(
    val dayDate: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    val taskCounts: List<Pair<Int, TaskUiStatus>> = emptyList(),
    val tasks: List<TaskUiState> = emptyList(),
    val categories: List<CategoryUiModel> = emptyList(),
) {
}