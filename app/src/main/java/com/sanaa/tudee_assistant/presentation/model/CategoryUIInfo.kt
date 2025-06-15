package com.sanaa.tudee_assistant.presentation.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class CategoryUIInfo(
    val title: String,
    val categoryPainter: Painter,
    val tint: Color
)