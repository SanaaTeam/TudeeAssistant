package com.sanaa.tudee_assistant.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme


@Composable
fun TextAppBar(
    modifier: Modifier = Modifier,
    title: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(color = Theme.color.surfaceHigh),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = Theme.textStyle.title.large,
            color = Theme.color.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}