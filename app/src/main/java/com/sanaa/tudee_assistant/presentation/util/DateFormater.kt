package com.sanaa.tudee_assistant.presentation.util

import kotlinx.datetime.LocalDateTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormater {
    fun String.formatTo12Hour(format: String): String {
        val inputFormat = SimpleDateFormat(format, Locale.getDefault())
        val outputFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
        val date: Date = inputFormat.parse(this) ?: return ""
        return outputFormat.format(date)
    }

    fun LocalDateTime.getDayName(): String {
        val dayOfWeek = this.dayOfWeek

        return dayOfWeek.name.take(3)
            .lowercase()
            .replaceFirstChar { it.titlecase(Locale.ROOT) }
    }
}