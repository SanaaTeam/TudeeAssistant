package com.sanaa.tudee_assistant.presentation.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

object DateFormater {
    fun LocalDate.getShortDayName(): String {
        val dayOfWeek = this.dayOfWeek

        return dayOfWeek.name.take(3)
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
}
