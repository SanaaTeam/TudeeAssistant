package com.sanaa.tudee_assistant.presentation.model

import androidx.compose.ui.graphics.painter.Painter

data class CategoryTaskState(
    val icon: Painter,
    val title: String,
    val description: String? = null,
    val date: String?,
    val priority: Priority,
)