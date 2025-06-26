package com.sanaa.tudee_assistant.presentation.mainActivity

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun TudeeScaffold(
    modifier: Modifier = Modifier,
    statusBarColor: Color = Color.Transparent,
    isDarkIcon: Boolean = false,
    fab: @Composable (() -> Unit)? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    bottomSheet: (@Composable () -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(statusBarColor) {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = isDarkIcon
        )
    }
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = { fab?.invoke() },
            bottomBar = { bottomBar?.invoke() },
            content = content
        )

        bottomSheet?.let {
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                it()
            }
        }
    }
}