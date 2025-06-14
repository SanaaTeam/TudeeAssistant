package com.sanaa.tudee_assistant.domain.model

import androidx.annotation.DrawableRes

/**
 * This allows us to treat them identically in the UI.
 */
interface Category {
    val id: String
    val title: String
    @get:DrawableRes val iconResource: Int
}