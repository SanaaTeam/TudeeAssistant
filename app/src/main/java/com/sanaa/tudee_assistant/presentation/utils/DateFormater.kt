package com.sanaa.tudee_assistant.presentation.utils

import android.content.Context
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateFormater {

    fun LocalDate.getShortDayName(locale: Locale = Locale.getDefault()): String {
        val calendar = Calendar.getInstance().apply {
            set(
                this@getShortDayName.year,
                this@getShortDayName.monthNumber - 1,
                this@getShortDayName.dayOfMonth
            )
        }
        val sdf = SimpleDateFormat("EEE", locale)
        return sdf.format(calendar.time)
    }

    fun LocalDate.getShortMonthName(locale: Locale = Locale.getDefault()): String {
        val calendar = Calendar.getInstance().apply {
            set(
                this@getShortMonthName.year,
                this@getShortMonthName.monthNumber - 1,
                this@getShortMonthName.dayOfMonth
            )
        }
        val sdf = SimpleDateFormat("MMM", locale)
        return sdf.format(calendar.time)
    }

    fun LocalDateTime.formatDateTime(context: Context): String {
        val calendar = Calendar.getInstance().apply {
            set(year, monthNumber - 1, dayOfMonth, hour, minute, second)
        }
        val sdf = SimpleDateFormat("dd MMM yyyy", context.resources.configuration.locales[0])
        return sdf.format(calendar.time)
    }

    fun formatLongToDate(timestampMillis: Long): LocalDate {
        val date = Date(timestampMillis)
        val calendar = Calendar.getInstance().apply { time = date }
        return LocalDate(
            year = calendar.get(Calendar.YEAR),
            monthNumber = calendar.get(Calendar.MONTH) + 1,
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    fun localDateToEpochMillis(date: LocalDate?): Long? {
        return date?.atStartOfDayIn(TimeZone.currentSystemDefault())?.toEpochMilliseconds()
    }

    fun getLocalDatesInMonth(year: Int, month: Int): List<LocalDate> {
        val totalDays = when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> if (isLeapYear(year)) 29 else 28
            else -> throw IllegalArgumentException("Invalid month: $month")
        }
        return List(totalDays) { day -> LocalDate(year, month, day + 1) }
    }

    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }
}
