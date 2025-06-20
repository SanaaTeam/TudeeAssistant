package com.sanaa.tudee_assistant.presentation.design_system.component

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.SnackBarStatus
import com.sanaa.tudee_assistant.presentation.utils.dropShadow

@Composable
fun SnackBar(
    modifier: Modifier = Modifier,
    snackBarStatus: SnackBarStatus = SnackBarStatus.SUCCESS,
    @StringRes message: Int,
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
                    color = if (snackBarStatus == SnackBarStatus.ERROR) Theme.color.errorVariant else Theme.color.greenVariant,
                    shape = RoundedCornerShape(Theme.dimension.regular)
                )
                .padding(Theme.dimension.small)
        ) {
            Image(
                painter = painterResource(
                    id = if (snackBarStatus == SnackBarStatus.ERROR) R.drawable.snack_bar_error else R.drawable.snack_bar_success
                ),
                contentDescription = message.toString(),
                modifier = Modifier.size(Theme.dimension.large),

                )
        }

        Text(
            text = stringResource(id = message),
            color = Theme.color.body,
            style = Theme.textStyle.body.medium,
            modifier = Modifier.padding(start = Theme.dimension.regular)

        )
    }
}

@Preview(name = "Dark Theme")
@Composable
private fun SnackBarDarkPreview() {
    TudeeTheme(isDarkTheme = true) {
        Box(
            modifier = Modifier
                .background(color = Theme.color.surface)
                .padding(Theme.dimension.extraSmall)
        ) {
            SnackBar(message = R.string.snack_bar_success)
        }
    }
}

@Preview(name = "Light Theme")
@Composable
private fun SnackBarLightPreview() {
    TudeeTheme(isDarkTheme = false) {
        Box(
            modifier = Modifier
                .background(color = Theme.color.surface)
                .padding(Theme.dimension.extraSmall)
        ) {
            SnackBar(snackBarStatus = SnackBarStatus.ERROR, message = R.string.snack_bar_success)
        }
    }
}