package com.sanaa.tudee_assistant.presentation.component.button

import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.component.button.utils.SpinnerIcon
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme


@Composable
fun FloatingActionButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    @DrawableRes iconRes: Int = R.drawable.ic_loading,
    onClick: () -> Unit = {}
) {

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .size(64.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            )
            .clip(RoundedCornerShape(100.dp))
            .then(
                when (enabled) {
                    true -> Modifier.background(
                        brush = Brush.linearGradient(
                            listOf(
                                Theme.color.primaryGradientStart,
                                Theme.color.primaryGradientEnd
                            )
                        )
                    )

                    false -> Modifier.background(color = Theme.color.disable)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        when(isLoading){
            true -> SpinnerIcon(tint = if(enabled) Theme.color.onPrimary else Theme.color.stroke)
            false -> Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = if(enabled) Theme.color.onPrimary else Theme.color.stroke
            )
        }
    }
}


@Preview
@Composable
fun FloatingActionButtonP(modifier: Modifier = Modifier) {
    FloatingActionButton(
        enabled = false,
        isLoading = false,
        onClick = {}
    )
}