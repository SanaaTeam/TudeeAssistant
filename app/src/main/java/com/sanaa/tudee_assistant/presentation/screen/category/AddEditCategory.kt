package com.sanaa.tudee_assistant.presentation.screen.category

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.component.BaseBottomSheet
import com.sanaa.tudee_assistant.presentation.designSystem.component.TudeeTextField
import com.sanaa.tudee_assistant.presentation.designSystem.component.UploadBox
import com.sanaa.tudee_assistant.presentation.designSystem.component.button.PrimaryButton
import com.sanaa.tudee_assistant.presentation.designSystem.component.button.SecondaryButton
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.dropShadowColor
import com.sanaa.tudee_assistant.presentation.model.CategoryUiState
import com.sanaa.tudee_assistant.presentation.utils.HelperFunctions
import com.sanaa.tudee_assistant.presentation.utils.dropShadow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditCategoryBottomSheet(
    onImageSelected: (Uri?) -> Unit,
    onTitleChange: (String) -> Unit,
    onSaveClick: (CategoryUiState) -> Unit,
    onDismiss: () -> Unit,
    category: CategoryUiState,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit = {},
    isFormValid: () -> Boolean = { false },
    isEditMode: Boolean = false,
) {
    var processedImageBytes by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    BaseBottomSheet(
        content = {
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .background(color = Theme.color.surface)
            ) {
                Column(
                    modifier = Modifier

                        .padding(horizontal = Theme.dimension.medium)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (isEditMode)
                                stringResource(R.string.edit_category)
                            else
                                stringResource(R.string.add_new_category),
                            style = Theme.textStyle.title.large,
                            color = Theme.color.title
                        )
                        if (isEditMode) {
                            Text(
                                text = stringResource(R.string.delete),
                                style = Theme.textStyle.title.large,
                                color = Theme.color.error,
                                modifier = Modifier.clickable(
                                    interactionSource = null,
                                    indication = null,
                                    onClick = onDeleteClick
                                )
                            )
                        }
                    }
                    TudeeTextField(
                        placeholder = stringResource(R.string.category_title),
                        value = category.name,
                        icon = painterResource(R.drawable.menu),
                        onValueChange = onTitleChange,
                        modifier = Modifier.padding(top = Theme.dimension.regular)
                    )
                    Text(
                        text = stringResource(R.string.category_image),
                        style = Theme.textStyle.title.medium,
                        color = Theme.color.title,
                        modifier = Modifier.padding(top = Theme.dimension.regular)
                    )
                    UploadBox(
                        modifier = Modifier.padding(top = Theme.dimension.small),
                        onImageSelected = { uri ->
                            onImageSelected(uri)
                            uri?.let {
                                scope.launch {
                                    processedImageBytes =
                                        HelperFunctions.processImage(context, it)
                                }
                            } ?: run {
                                processedImageBytes = null
                            }

                        },
                        initialImagePath = category.imagePath
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .dropShadow(offsetY = 4.dp, blur = 20.dp, color = dropShadowColor)
                        .background(Theme.color.surfaceHigh)
                        .padding(
                            vertical = Theme.dimension.regular,
                            horizontal = Theme.dimension.medium
                        )
                ) {
                    PrimaryButton(
                        label = if (isEditMode) stringResource(R.string.save) else stringResource(R.string.add),
                        enabled = isFormValid(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = Theme.dimension.regular),
                        onClick = { onSaveClick(category) }
                    )
                    SecondaryButton(
                        label = stringResource(R.string.cancel),
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onDismiss
                    )
                }
            }


        },
        onDismiss = onDismiss
    )
}

@PreviewLightDark
@Composable
private fun AddEditCategoryBottomSheetPreview() {
    TudeeTheme(isDark = isSystemInDarkTheme()) {
        AddEditCategoryBottomSheet(
            onImageSelected = {},
            onSaveClick = {},
            onTitleChange = {},
            onDismiss = {},
            category = CategoryUiState(
                id = 0,
                name = "Work",
                imagePath = "",
                isDefault = false,
                tasksCount = 0
            ),
            isEditMode = true,
            isFormValid = { true },
        )
    }
}