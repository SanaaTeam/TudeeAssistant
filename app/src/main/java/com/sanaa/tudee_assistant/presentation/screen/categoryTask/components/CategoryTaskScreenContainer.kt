package com.sanaa.tudee_assistant.presentation.screen.categoryTask.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme

@Composable
fun CategoryTaskScreenContainer(
    topBar: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .background(color = Theme.color.surface)
            .fillMaxWidth()
    ) {
        topBar()
        content()

    }
}