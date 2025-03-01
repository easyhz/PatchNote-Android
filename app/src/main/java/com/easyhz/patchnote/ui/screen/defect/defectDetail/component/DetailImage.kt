package com.easyhz.patchnote.ui.screen.defect.defectDetail.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.easyhz.patchnote.core.model.image.ImageSize
import com.easyhz.patchnote.ui.theme.PlaceholderText

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailImage(
    modifier: Modifier = Modifier,
    url: String,
    imageSize: ImageSize,
) {
    val defaultSize = 938
    val (imageWidth, imageHeight) = if (url.isNotBlank()) imageSize.width to imageSize.height else defaultSize to defaultSize

    val screenWidthPx = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx().toInt()
    }

    val calculatedHeight = (imageHeight.toInt() / (imageWidth.toInt() / screenWidthPx.toDouble())).toInt()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(calculatedHeight.toDp())
            .clip(RoundedCornerShape(8.dp))
    ) {
        GlideImage(
            modifier = Modifier.fillMaxSize(),
            model = url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            loading = placeholder(ColorPainter(PlaceholderText)),
            failure = placeholder(ColorPainter(PlaceholderText)),
            transition = CrossFade
        )
    }
}
@Composable
fun Int.toDp(): Dp {
    val density = LocalDensity.current.density
    return (this / density).dp
}