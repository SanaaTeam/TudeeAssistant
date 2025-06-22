package com.sanaa.tudee_assistant.presentation.designSystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.model.SnackBarStatus

@Composable
fun TudeeSnackBar(snackBarHostState: SnackbarHostState, isError: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .systemBarsPadding()
    ) {
        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = Theme.dimension.medium)
        ) { data ->
            SnackBar(
                message = data.visuals.message,
                snackBarStatus = if (isError) SnackBarStatus.ERROR else SnackBarStatus.SUCCESS
            )
        }
    }
}