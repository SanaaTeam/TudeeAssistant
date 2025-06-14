package com.sanaa.tudee_assistant.presentation.model

import androidx.annotation.DrawableRes

data class Category(
    val title: String,
    @get:DrawableRes
    val iconResource: Int
)