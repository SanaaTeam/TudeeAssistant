package com.sanaa.tudee_assistant.presentation.screen.categoryTask.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme

@Composable
fun CategoryTaskHeaderComponent(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit = {},
    isEditable: Boolean = false,
) {
    Row(
        modifier = modifier
            .height(64.dp)
            .fillMaxWidth()
            .background(color = Theme.color.surfaceHigh)
            .padding(horizontal = Theme.dimension.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedIconButton(
                painter = painterResource(R.drawable.arrow_left),
                onClick = onBackClick
            )
            Text(
                text = title,
                style = Theme.textStyle.title.large,
                color = Theme.color.title,
                modifier = Modifier.padding(start = Theme.dimension.regular)
            )
        }
        if (isEditable)
            OutlinedIconButton(
                onClick = onEditClick,
            )
    }
}
