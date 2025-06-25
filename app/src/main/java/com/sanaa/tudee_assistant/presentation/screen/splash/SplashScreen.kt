package com.sanaa.tudee_assistant.presentation.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.theme.CherryBomb
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.overlay)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ellipse_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(614.dp, 584.dp)
            )
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            val strokeColor = Theme.color.primary
            val fillColor = Color(0xDEFFFFFF)
            val strokeWidth = 6f

            val offsets = listOf(
                Offset(-strokeWidth, 0f),
                Offset(strokeWidth, 0f),
                Offset(0f, -strokeWidth),
                Offset(0f, strokeWidth),
                Offset(-strokeWidth, -strokeWidth),
                Offset(strokeWidth, -strokeWidth),
                Offset(-strokeWidth, strokeWidth),
                Offset(strokeWidth, strokeWidth)
            )

            offsets.forEach { offset ->
                Text(
                    text = stringResource(R.string.logoName),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = CherryBomb,
                    letterSpacing = (-0.05).em,
                    color = strokeColor,
                    modifier = Modifier.offset {
                        IntOffset(offset.x.toInt(), offset.y.toInt())
                    }
                )
            }

            Text(
                text = stringResource(R.string.logoName),
                fontSize = 48.sp,
                fontWeight = FontWeight.W400,
                fontFamily = CherryBomb,
                letterSpacing = (-0.05).em,
                color = fillColor
            )
        }
    }
}

@Preview()
@Composable
private fun SplashScreenPreview() {
    TudeeTheme(false) {
          SplashScreen()
    }
}