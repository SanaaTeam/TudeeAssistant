package com.sanaa.tudee_assistant.presentation.state

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class CategoryState(
    val title: String = "",
    val categoryPainter: Painter,
    val tint: Color = Color.Black,
)