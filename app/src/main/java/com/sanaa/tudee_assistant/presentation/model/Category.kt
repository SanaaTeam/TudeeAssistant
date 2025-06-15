package com.sanaa.tudee_assistant.presentation.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class Category(
    val title: String,
    @get:DrawableRes
    val iconResource: Int,
    val tint: Color
)