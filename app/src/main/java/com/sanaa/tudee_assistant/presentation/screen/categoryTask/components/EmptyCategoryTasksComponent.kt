package com.sanaa.tudee_assistant.presentation.screen.categoryTask.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.component.EmptyScreen

@Composable
fun EmptyCategoryTasksComponent(categoryName: String) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        EmptyScreen(
            title = stringResource(
                R.string.no_tasks_in,
                categoryName,
            ),
            caption = null
        )
    }
}