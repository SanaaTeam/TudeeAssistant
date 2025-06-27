package com.sanaa.tudee_assistant.presentation.designSystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.SnackBarState
import com.sanaa.tudee_assistant.presentation.model.SnackBarStatus
import com.sanaa.tudee_assistant.presentation.modifire.dropShadow

@Composable
fun SnackBar(
    state: SnackBarState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .width(328.dp)
            .height(56.dp)
            .dropShadow(
                blur = Theme.dimension.medium,
                offsetY = Theme.dimension.extraSmall,
                color = Color.Black.copy(0.12f)
            )
            .background(
                color = Theme.color.surfaceHigh,
                shape = RoundedCornerShape(Theme.dimension.medium)
            )
            .padding(Theme.dimension.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (state.snackBarStatus == SnackBarStatus.ERROR) Theme.color.errorVariant else Theme.color.greenVariant,
                    shape = RoundedCornerShape(Theme.dimension.regular)
                )
                .padding(Theme.dimension.small)
        ) {
            Image(
                painter = painterResource(
                    id = if (state.snackBarStatus == SnackBarStatus.ERROR) R.drawable.snack_bar_error else R.drawable.snack_bar_success
                ),
                contentDescription = state.message,
                modifier = Modifier.size(Theme.dimension.large),

                )
        }

        Text(
            text = state.message,
            color = Theme.color.body,
            style = Theme.textStyle.body.medium,
            modifier = Modifier.padding(start = Theme.dimension.regular)
        )
    }
}

@PreviewLightDark
@Composable
private fun SnackBarDarkPreview() {
    TudeeTheme(isSystemInDarkTheme()) {
        Box(
            modifier = Modifier
                .background(color = Theme.color.surface)
                .padding(Theme.dimension.extraSmall)
        ) {
            SnackBar(state = SnackBarState.getInstance(stringResource(R.string.snack_bar_success)))
        }
    }
}