package com.easyhz.patchnote.ui.screen.image.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.model.image.DefectImage
import com.easyhz.patchnote.ui.screen.image.detail.component.ImageDetailBottomBar
import com.easyhz.patchnote.ui.screen.image.detail.component.ImageDetailBottomBarType
import com.easyhz.patchnote.ui.screen.image.detail.component.ImageDetailTopBar
import com.easyhz.patchnote.ui.screen.image.detail.contract.ImageDetailState
import com.easyhz.patchnote.ui.theme.MainText
import com.easyhz.patchnote.ui.theme.PlaceholderText
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

/**
 * Date: 2025. 4. 9.
 * Time: 오후 10:05
 */

@Composable
fun ImageDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ImageDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ImageDetailScreen(
        modifier = modifier,
        uiState = uiState,
        navigateUp = { },
        onClickDisplayButton = { },
        onClickSave = { }
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        TODO("Not yet implemented")
        when (sideEffect) {

        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ImageDetailScreen(
    modifier: Modifier = Modifier,
    uiState: ImageDetailState,
    navigateUp: () -> Unit,
    onClickDisplayButton: (Boolean) -> Unit,
    onClickSave: (ImageDetailBottomBarType) -> Unit,
) {
    val zoomState = rememberZoomState()

    Scaffold(
        modifier = modifier,
        containerColor = MainText,
        topBar = {
            ImageDetailTopBar(
                checked = uiState.isDisplayInformation,
                title = displayTitle(
                    currentImage = uiState.currentImage,
                    images = uiState.images
                ),
                navigateUp = navigateUp,
                onClickDisplayButton = onClickDisplayButton
            )
        },
        floatingActionButton = {
            ImageDetailBottomBar(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 20.dp),
                onClick = onClickSave
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        GlideImage(
            modifier = Modifier.fillMaxWidth().zoomable(
                zoomState = zoomState,
                onDoubleTap = { position ->
                    val targetScale = when {
                        zoomState.scale < 2f -> 2f
                        zoomState.scale < 4f -> 4f
                        else -> 1f
                    }
                    zoomState.changeScale(targetScale, position)
                }
            ),
            model = uiState.currentImage?.uri,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            loading = placeholder(ColorPainter(PlaceholderText)),
            failure = placeholder(ColorPainter(PlaceholderText)),
            transition = CrossFade
        )

    }
}

private fun displayTitle(
    currentImage: DefectImage?,
    images: List<DefectImage>,
): String {
    if (currentImage == null) return "0/0"
    return "${images.indexOf(currentImage) + 1}/${images.size}"
}

@Preview
@Composable
private fun ImageDetailScreenPreview() {
    ImageDetailScreen(
        uiState = ImageDetailState.init(),
        navigateUp = { },
        onClickDisplayButton = { },
        onClickSave = { }
    )
}