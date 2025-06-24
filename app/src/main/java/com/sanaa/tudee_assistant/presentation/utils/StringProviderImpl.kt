package com.sanaa.tudee_assistant.presentation.utils

import android.content.Context
import com.sanaa.tudee_assistant.domain.service.StringProvider

class StringProviderImpl(
    private val context: Context
): StringProvider {
    override fun getString(resId: Int): String {
        return context.getString(resId)
    }
}