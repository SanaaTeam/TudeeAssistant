package com.sanaa.tudee_assistant.presentation.model

import androidx.compose.ui.graphics.painter.Painter

data class CategoryTask(
    val icon: Painter,
    val title: String,
    val description: String,
    val date: String?,
    val priority: Priority,
)