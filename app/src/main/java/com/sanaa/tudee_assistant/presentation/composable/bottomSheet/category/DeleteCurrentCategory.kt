package com.sanaa.tudee_assistant.presentation.composable.bottomSheet.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.component.BaseBottomSheet
import com.sanaa.tudee_assistant.presentation.designSystem.component.button.NegativeButton
import com.sanaa.tudee_assistant.presentation.designSystem.component.button.SecondaryButton
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.dropShadowColor
import com.sanaa.tudee_assistant.presentation.utils.dropShadow

@Composable
fun DeleteCurrentCategory(
    onDeleteClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var internalShowBottomSheet by remember { mutableStateOf(true) }

    DeleteCurrentCategoryContent(
        onDeleteClick = onDeleteClick,
        showBottomSheet = internalShowBottomSheet,
        onDismiss = {
            internalShowBottomSheet = false
            onDismiss()
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteCurrentCategoryContent(
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    onDismiss: () -> Unit = {},
    showBottomSheet: Boolean = true,
) {
    if (showBottomSheet) {
        BaseBottomSheet(
            content = {
                Column(
                    modifier = modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Theme.dimension.medium),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(Theme.dimension.small)
                    ) {
                        Text(
                            text = stringResource(R.string.delete_category),
                            style = Theme.textStyle.title.large,
                            color = Theme.color.title
                        )

                        Text(
                            text = stringResource(R.string.are_you_sure_to_continue),
                            style = Theme.textStyle.body.large,
                            color = Theme.color.body
                        )
                    }

                    Image(
                        painter = painterResource(R.drawable.delete_tudee),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.padding(vertical = Theme.dimension.medium)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .dropShadow(offsetY = 4.dp, blur = 20.dp, color = dropShadowColor)
                            .background(Theme.color.surfaceHigh)
                            .padding(
                                vertical = Theme.dimension.regular,
                                horizontal = Theme.dimension.medium
                            )
                    ) {
                        NegativeButton(
                            label = stringResource(R.string.delete),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = Theme.dimension.regular),
                            onClick = { onDeleteClick() }
                        )
                        SecondaryButton(
                            label = stringResource(R.string.cancel),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onDismiss() },
                        )
                    }
                }
            },
            onDismiss = { onDismiss() }
        )
    }
}