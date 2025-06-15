package com.sanaa.tudee_assistant.presentation.model

import kotlinx.datetime.LocalDateTime

data class Day(
    val dayDate: LocalDateTime,
    val isSelected: Boolean = false,
)