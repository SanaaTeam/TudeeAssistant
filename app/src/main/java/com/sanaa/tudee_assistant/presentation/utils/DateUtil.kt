package com.sanaa.tudee_assistant.presentation.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DateUtil {
    var today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
}