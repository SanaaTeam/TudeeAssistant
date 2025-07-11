package com.sanaa.tudee_assistant.presentation.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    content: @Composable () -> Unit,
    onDismiss: () -> Unit,
) {
    LaunchedEffect(Unit) {
        sheetState.expand()
    }
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = onDismiss,
            scrimColor = Color.Black.copy(alpha = 0.6f),
            shape = RoundedCornerShape(Theme.dimension.large, Theme.dimension.large),
            containerColor = Theme.color.surface,
            dragHandle = {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(Theme.dimension.medium),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .height(Theme.dimension.extraSmall)
                            .width(Theme.dimension.extraLarge)
                            .alpha(0.4f)
                            .background(
                                color = Theme.color.body,
                                shape = RoundedCornerShape(100.dp)
                            )
                    )
                }
            },
            content = {
                Box(
                    modifier = Modifier.wrapContentHeight(),
                )
                { content() }
            }
        )
}
