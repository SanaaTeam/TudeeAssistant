package com.sanaa.tudee_assistant.presentation.utils

import kotlinx.datetime.LocalDateTime

object DateFormater {
    fun LocalDateTime.getShortDayName(): String {
        val dayOfWeek = this.dayOfWeek

        return dayOfWeek.name.take(3)
            .lowercase()
            .replaceFirstChar { it.uppercase() }
    }
}