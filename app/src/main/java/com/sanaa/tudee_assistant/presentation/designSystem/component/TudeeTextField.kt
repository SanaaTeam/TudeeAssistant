package com.sanaa.tudee_assistant.presentation.designSystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme

@Composable
fun TudeeTextField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    icon: Painter? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val isFocused by interactionSource.collectIsFocusedAsState()
    val borderColor = if (isFocused) Theme.color.primary else Theme.color.stroke
    val iconTint = if (isFocused || value.isNotEmpty()) Theme.color.body else Theme.color.hint

    BasicTextField(
        interactionSource = interactionSource,
        value = value,
        enabled = enabled,
        readOnly = readOnly,
        onValueChange = onValueChange,
        modifier = modifier
            .height(56.dp)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(Theme.dimension.medium)
            ),
        textStyle = Theme.textStyle.body.medium.copy(color = Theme.color.body),
        singleLine = true,
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(all = Theme.dimension.regular)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Theme.dimension.regular)
            ) {
                if (icon != null) {
                    Image(
                        painter = icon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(Theme.dimension.large),
                        colorFilter = ColorFilter.tint(iconTint)
                    )
                    VerticalDivider(
                        modifier = Modifier
                            .height(30.dp)
                            .width(1.dp)
                            .background(color = Theme.color.stroke)
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = if (icon == null) Alignment.TopStart else Alignment.CenterStart
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = Theme.textStyle.label.medium,
                            color = Theme.color.hint
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewTudeeTextField() {
    TudeeTheme(false) {
        TudeeTextField(
            placeholder = "Name",
            icon = painterResource(R.drawable.user_icon),
            value = "",
            onValueChange = {}
        )
    }
}