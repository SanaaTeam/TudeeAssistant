package com.sanaa.tudee_assistant.presentation.utils

import android.icu.text.SimpleDateFormat
import kotlinx.datetime.LocalDateTime
import java.util.Calendar
import java.util.Locale

object DateFormater {
    fun LocalDateTime.getShortDayName(): String {
        val dayOfWeek = this.dayOfWeek

        return dayOfWeek.name.take(3)
            .lowercase()
            .replaceFirstChar { it.uppercase() }
    }

    fun formatLongToDate(millis: Long, pattern:String): String {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = millis
        }
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(calendar.time)
    }

    fun formatDateToLong(date: LocalDateTime): Long {
        return Calendar.getInstance().apply {
            set(date.year, date.monthNumber, date.dayOfMonth) // Set your desired initial date
        }.timeInMillis
    }

}
