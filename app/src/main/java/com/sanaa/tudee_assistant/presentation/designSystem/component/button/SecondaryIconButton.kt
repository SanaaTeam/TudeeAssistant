package com.sanaa.tudee_assistant.presentation.designSystem.component.button

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme

@Composable
fun SecondaryIconButton(
    iconRes: Painter,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {}

) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
            .border(
                width = 1.dp,
                color = Theme.color.stroke,
                shape = RoundedCornerShape(100.dp)
            )
            .padding(horizontal = Theme.dimension.large, vertical = Theme.dimension.medium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            Theme.dimension.small,
            Alignment.CenterHorizontally
        )
    ) {
        Icon(
            painter = iconRes,
            contentDescription = null,
            tint = Theme.color.primary
        )
    }
}