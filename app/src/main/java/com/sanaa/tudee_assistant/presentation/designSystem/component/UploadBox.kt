package com.sanaa.tudee_assistant.presentation.designSystem.component

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.composable.CategoryThumbnail
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.utils.drawDashedBorder

@Composable
fun UploadBox(
    modifier: Modifier = Modifier,
    strokeColor: Color = Theme.color.stroke,
    onImageSelected: (Uri?) -> Unit,
    cornerRadius: Dp = Theme.dimension.medium,
    initialImagePath: String? = null
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        onImageSelected(uri)
    }
    UploadBoxContent(
        imageUri = imageUri,
        modifier = modifier,
        strokeColor = strokeColor,
        launcher = launcher,
        onImageSelected = onImageSelected,
        cornerRadius = cornerRadius,
        defaultImagePath = initialImagePath
    )
}

@Composable
fun UploadBoxContent(
    imageUri: Uri?,
    modifier: Modifier = Modifier,
    launcher: ManagedActivityResultLauncher<String, Uri?>,
    onImageSelected: (Uri?) -> Unit,
    defaultImagePath: String? = null,
    strokeColor: Color = Theme.color.stroke,
    cornerRadius: Dp = Theme.dimension.medium
) {

    Box(
        modifier = modifier
            .size(width = 112.dp, height = 113.dp)
            .background(color = Theme.color.surface)
            .clip(RoundedCornerShape(16.dp))
            .clickable { launcher.launch("image/*") }
            .drawDashedBorder(strokeColor = strokeColor, cornerRadius = cornerRadius),
        contentAlignment = Alignment.Center
    ) {
        imageUri?.let { uri ->
            SelectedImageView(strokeColor, cornerRadius, uri, launcher)
        } ?: run {
            UploadPlaceholder(defaultImagePath)
        }
    }
}

@Composable
fun SelectedImageView(
    strokeColor: Color = Theme.color.stroke,
    cornerRadius: Dp = Theme.dimension.medium,
    imageUri: Uri,
    launcher: ManagedActivityResultLauncher<String, Uri?>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawDashedBorder(strokeColor = strokeColor, cornerRadius = cornerRadius),
        contentAlignment = Alignment.Center,

        ) {

        AsyncImage(
            model = imageUri,
            contentDescription = "Selected Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .size(Theme.dimension.extraLarge)
                .background(Color.White.copy(alpha = 0.8f), CircleShape)
                .clickable { launcher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.edit_icon),
                contentDescription = "Edit Icon",
                modifier = Modifier.size(Theme.dimension.extraLarge),
            )
        }
    }
}

@Composable
fun UploadPlaceholder(defaultImagePath: String? = null) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!defaultImagePath.isNullOrEmpty()) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                CategoryThumbnail(
                    modifier = Modifier.fillMaxSize(),
                    imagePath = defaultImagePath
                )
                Image(
                    painter = painterResource(R.drawable.edit_icon),
                    contentDescription = "Edit Icon",
                    modifier = Modifier.size(Theme.dimension.extraLarge),
                )
            }
        }else {
            Image(
                painter = painterResource(R.drawable.uplaoad_image),
                contentDescription = "Upload Icon",
                modifier = Modifier.size(Theme.dimension.large),
            )
            Text(
                text = stringResource(R.string.upload),
                style = Theme.textStyle.label.medium,
                color = Theme.color.hint,
                modifier = Modifier.padding(top = Theme.dimension.small),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UploadBoxLightPreview() {
    TudeeTheme(false) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            UploadBox(onImageSelected = { })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UploadBoxDarkPreview() {
    TudeeTheme(true) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            UploadBox(onImageSelected = { })
        }
    }
}