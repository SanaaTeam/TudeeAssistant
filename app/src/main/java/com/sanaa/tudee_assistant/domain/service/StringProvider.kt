package com.sanaa.tudee_assistant.domain.service

import androidx.annotation.StringRes

interface StringProvider {
    fun getString(@StringRes resId: Int): String
}