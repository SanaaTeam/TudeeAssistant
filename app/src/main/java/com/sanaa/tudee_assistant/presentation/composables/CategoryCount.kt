package com.sanaa.tudee_assistant.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme

@Composable
fun CategoryCount(count: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 13.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Theme.color.surfaceLow,
                    shape = RoundedCornerShape(100.dp)
                )
                .padding(vertical = 2.dp, horizontal = 10.5.dp)
        ) {
            Text(
                text = count,
                style = Theme.textStyle.label.small,
                color = Theme.color.hint
            )
        }
    }
}