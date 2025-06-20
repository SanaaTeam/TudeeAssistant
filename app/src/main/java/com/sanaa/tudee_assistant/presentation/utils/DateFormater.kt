package com.sanaa.tudee_assistant.presentation.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import android.content.Context
import com.sanaa.tudee_assistant.R
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number

object DateFormater {
    fun LocalDate.getShortDayName(): String {
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

    fun LocalDate.getShortMonthName(): String {

        return this.month.name.take(3)
            .lowercase()
            .replaceFirstChar { it.uppercase() }
    }

    fun formatLongToDate(timestampMillis: Long): LocalDate {
        val instant = Instant.fromEpochMilliseconds(timestampMillis)
        return instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
    }

    fun localDateToEpochMillis(date: LocalDate?): Long? {
        val dateTime = date?.atStartOfDayIn(TimeZone.UTC)
        return dateTime?.toEpochMilliseconds()
    }

    fun getLocalDatesInMonth(year: Int, month: Int): List<LocalDate> {
        val totalDays = when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> if (isLeapYear(year)) 29 else 28
            else -> throw IllegalArgumentException("Invalid month")
        }

        return List(totalDays) { day ->
            LocalDate(year, month, day + 1)
        }
    }


    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }
}
