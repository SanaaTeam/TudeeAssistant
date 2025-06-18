package com.sanaa.tudee_assistant.presentation.utils

import android.content.Context
import com.sanaa.tudee_assistant.R
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number

object DateFormater {
    fun LocalDateTime.getShortDayName(): String {
        val dayOfWeek = this.dayOfWeek

        return dayOfWeek.name.take(3)
            .lowercase()
            .replaceFirstChar { it.uppercase() }
    }

    fun LocalDateTime.formatDateTime(context: Context): String {
        val day = this.dayOfMonth.toString().padStart(2, '0')
        val month = this.month.number
        val monthAbbreviations = context.resources.getStringArray(R.array.month_abbreviations)
        val monthAbbrev = monthAbbreviations[month - 1]
        return "$day $monthAbbrev $year"
    }
}