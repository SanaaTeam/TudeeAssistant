package com.sanaa.tudee_assistant.presentation.utils

import kotlinx.datetime.LocalDateTime
import java.util.Locale

object DateFormater {
    fun LocalDateTime.getShortDayName(): String {
        val dayOfWeek = this.dayOfWeek

        return dayOfWeek.name.take(3)
            .lowercase()
            .replaceFirstChar { it.titlecase(Locale.ROOT) }
    }
}