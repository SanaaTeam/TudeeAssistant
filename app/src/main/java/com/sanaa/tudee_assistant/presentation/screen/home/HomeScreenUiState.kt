package com.sanaa.tudee_assistant.presentation.screen.home

import com.sanaa.tudee_assistant.presentation.model.TaskStatus
import com.sanaa.tudee_assistant.presentation.state.CategoryTaskState
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class HomeScreenUiState(
    val dayDate: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    val taskCounts: List<Pair<Int, TaskStatus>> = emptyList(),
    val tasks: List<CategoryTaskState> = listOf(),
)