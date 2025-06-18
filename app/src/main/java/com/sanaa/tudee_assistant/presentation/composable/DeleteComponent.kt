package com.sanaa.tudee_assistant.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.component.BaseBottomSheet
import com.sanaa.tudee_assistant.presentation.design_system.component.button.NegativeButton
import com.sanaa.tudee_assistant.presentation.design_system.component.button.SecondaryButton
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.design_system.theme.dropShadowColor
import com.sanaa.tudee_assistant.presentation.utils.dropShadow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteComponent(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onDeleteClicked: () -> Unit,
    title: String = stringResource(R.string.delete_task_title),
    subtitle: String = stringResource(R.string.delete_task_subtitle),
) {
    BaseBottomSheet(
        onDismiss = onDismiss,
        content = {
            Column(
                verticalArrangement = Arrangement.spacedBy(Theme.dimension.large),
                modifier = modifier.background(color = Theme.color.surface)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(Theme.dimension.regular),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Theme.dimension.medium),
                ) {
                    Text(
                        text = title,
                        style = Theme.textStyle.title.large,
                        color = Theme.color.title,
                    )
                    Text(
                        text = subtitle,
                        style = Theme.textStyle.label.large,
                        color = Theme.color.body,
                    )
                    Image(
                        painter = painterResource(id = R.drawable.delete_tudee),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(width = 107.dp, height = 100.dp),
                        contentScale = ContentScale.FillBounds
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .dropShadow(
                            color = dropShadowColor,
                            offsetX = 0.dp,
                            offsetY = 4.dp,
                            blur = 20.dp
                        )
                        .background(Theme.color.surfaceHigh)
                        .padding(
                            horizontal = Theme.dimension.medium,
                            vertical = Theme.dimension.regular
                        ),
                    verticalArrangement = Arrangement.spacedBy(Theme.dimension.regular)
                ) {
                    NegativeButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onDeleteClicked,
                        label = stringResource(R.string.delete),
                    )
                    SecondaryButton(
                        onClick = onDismiss,
                        lable = stringResource(R.string.cancel),
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun PreviewDeleteButton() {
    TudeeTheme {
        DeleteComponent(
            onDismiss = {},
            onDeleteClicked = {}
        )
    }
}