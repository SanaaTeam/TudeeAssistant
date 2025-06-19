package com.sanaa.tudee_assistant.presentation.screen.category_task.composable

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.component.BaseBottomSheet
import com.sanaa.tudee_assistant.presentation.design_system.component.TudeeTextField
import com.sanaa.tudee_assistant.presentation.design_system.component.UploadBox
import com.sanaa.tudee_assistant.presentation.design_system.component.button.NegativeTextButton
import com.sanaa.tudee_assistant.presentation.design_system.component.button.PrimaryButton
import com.sanaa.tudee_assistant.presentation.design_system.component.button.SecondaryButton
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.dropShadowColor
import com.sanaa.tudee_assistant.presentation.utils.HelperFunctions
import com.sanaa.tudee_assistant.presentation.utils.dropShadow
import kotlinx.coroutines.launch

@Composable
fun UpdateCurrentCategory(
    onImageSelected: (Uri?) -> Unit,
    onEditClick: (title: String, imageUri: Uri?) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var internalShowBottomSheet by remember { mutableStateOf(true) }
    var categoryTitle by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    UpdateCurrentCategoryContent(
        onEditClick = { onEditClick(categoryTitle, selectedImageUri) },
        showBottomSheet = internalShowBottomSheet,
        categoryTitle = categoryTitle,
        onCategoryTitleChange = { categoryTitle = it },
        onDismiss = {
            internalShowBottomSheet = false
            onDismiss()
        },
        onImageSelected = { uri ->
            selectedImageUri = uri
            onImageSelected(uri)
        },
        modifier = modifier,
        onDeleteClick = {}
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateCurrentCategoryContent(
    modifier: Modifier = Modifier,
    onImageSelected: (Uri?) -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onCategoryTitleChange: (String) -> Unit,
    onDismiss: () -> Unit = {},
    showBottomSheet: Boolean = true,
    categoryTitle: String = "",
) {
    var processedImageBytes by remember { mutableStateOf<Bitmap?>(null) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    if (showBottomSheet) {
        BaseBottomSheet(
            content = {
                Column(
                    modifier = modifier
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = Theme.dimension.medium)
                            .background(color = Theme.color.surface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(R.string.edit_category),
                                style = Theme.textStyle.title.large,
                                color = Theme.color.title
                            )

                            NegativeTextButton(
                                modifier = Modifier,
                                label = stringResource(R.string.delete),
                                enabled = true,
                                isLoading = false,
                                onClick = {
                                    onDeleteClick()
                                    onDismiss()
                                }
                            )
                        }
                        TudeeTextField(
                            placeholder = categoryTitle,
                            value = "",
                            icon = painterResource(R.drawable.menu_circle),
                            onValueChange = onCategoryTitleChange,
                            modifier = Modifier.padding(top = Theme.dimension.regular)
                        )
                        Text(
                            text = stringResource(R.string.category_image),
                            style = Theme.textStyle.title.large,
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
                            }
                        )
                    }

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
                        PrimaryButton(
                            label = stringResource(R.string.save),
                            enabled = processedImageBytes != null && categoryTitle.isNotBlank(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = Theme.dimension.regular),
                            onClick = { onEditClick() }
                        )
                        SecondaryButton(
                            lable = stringResource(R.string.cancel),
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