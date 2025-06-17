package com.sanaa.tudee_assistant.presentation.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.component.BaseBottomSheet
import com.sanaa.tudee_assistant.presentation.design_system.component.TudeeTextField
import com.sanaa.tudee_assistant.presentation.design_system.component.UploadBox
import com.sanaa.tudee_assistant.presentation.design_system.component.button.PrimaryButton
import com.sanaa.tudee_assistant.presentation.design_system.component.button.SecondaryButton
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.dropShadowColor
import com.sanaa.tudee_assistant.presentation.utils.dropShadow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewCategory(
    onImageSelected: (Uri?) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showBottomSheet by remember { mutableStateOf(true) }
    var categoryTitle by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

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
                        Text(
                            text = stringResource(R.string.add_new_category),
                            style = Theme.textStyle.title.large,
                            color = Theme.color.title
                        )
                        TudeeTextField(
                            placeholder = stringResource(R.string.category_title),
                            value = categoryTitle,
                            icon = painterResource(R.drawable.menu_circle),
                            onValueChange = { newText -> categoryTitle = newText },
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
                                selectedImageUri = uri
                                onImageSelected(uri)
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
                            label = stringResource(R.string.add),
                            enabled = selectedImageUri != null && categoryTitle.isNotBlank(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = Theme.dimension.regular),
                            onClick = onAddClick
                        )
                        SecondaryButton(
                            lable = stringResource(R.string.cancel),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { showBottomSheet = false },

                            )
                    }
                }
            },
            onDismiss = { showBottomSheet = false }
        )
    }
}

@Preview
@Composable
fun AddNewCategoryScreenPreview() {
    AddNewCategory({}, {})
}
