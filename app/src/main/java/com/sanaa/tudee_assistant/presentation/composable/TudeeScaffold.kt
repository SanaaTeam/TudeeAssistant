package com.sanaa.tudee_assistant.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme

@Composable
fun TudeeScaffold(
    modifier: Modifier = Modifier,
    floatingActionButton: @Composable () -> Unit = {},
    backgroundColor: Color = Theme.color.primary,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    contentBackground: Color = Theme.color.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    content: @Composable () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = { topBar() },
        bottomBar = bottomBar,
        floatingActionButton = floatingActionButton,
        contentColor = contentColor,
        containerColor = backgroundColor,
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(contentBackground)
                .padding(innerPadding)
        ) {
            content()
        }
    }
}