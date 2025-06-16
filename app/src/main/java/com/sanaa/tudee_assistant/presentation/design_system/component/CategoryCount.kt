package com.sanaa.tudee_assistant.presentation.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme

@Composable
fun CategoryCount(count: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopEnd
    ) {
        Box(
            modifier = Modifier
                .width(36.dp)
                .background(
                    color = Theme.color.surfaceLow,
                    shape = RoundedCornerShape(100.dp)
                )
                .padding(vertical = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = count,
                style = Theme.textStyle.label.small,
                color = Theme.color.hint
            )
        }
    }
}

@Preview()
@Composable
private fun CategoryCountPreview() {
    TudeeTheme(isDarkTheme = false) {
        CategoryCount("16")
    }
}