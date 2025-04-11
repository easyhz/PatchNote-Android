package com.easyhz.patchnote.ui.screen.image.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import com.easyhz.patchnote.ui.screen.image.detail.component.ImageDetailBottomBar
import com.easyhz.patchnote.ui.screen.image.detail.component.ImageDetailBottomBarType
import com.easyhz.patchnote.ui.screen.image.detail.component.ImageDetailTopBar
import com.easyhz.patchnote.ui.screen.image.detail.contract.ImageDetailIntent
import com.easyhz.patchnote.ui.screen.image.detail.contract.ImageDetailSideEffect
import com.easyhz.patchnote.ui.screen.image.detail.contract.ImageDetailState
import com.easyhz.patchnote.ui.theme.LocalSnackBarHostState
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
    viewModel: ImageDetailViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = LocalSnackBarHostState.current

    ImageDetailScreen(
        modifier = modifier,
        uiState = uiState,
        navigateUp = { viewModel.postIntent(ImageDetailIntent.NavigateUp) },
        onClickDisplayButton = { viewModel.postIntent(ImageDetailIntent.ClickDisplayButton(it)) },
        onClickSave = { type, currentImage -> viewModel.postIntent(ImageDetailIntent.ClickSaveButton(type, currentImage)) },
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is ImageDetailSideEffect.NavigateUp -> navigateUp()
            is ImageDetailSideEffect.ShowSnackBar -> snackBarHostState.showSnackbar(
                message = sideEffect.value,
                withDismissAction = true
            )
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
    onClickSave: (ImageDetailBottomBarType, Int) -> Unit,
) {
    val zoomState = rememberZoomState()
    val pagerState = rememberPagerState(
        initialPage = uiState.currentImage,
    ) {
        uiState.images.size
    }

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
                onClick = { onClickSave(it, pagerState.currentPage) }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 500.dp),
            state = pagerState,
        ) {
            GlideImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .zoomable(
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
                model = uiState.images[it],
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                loading = placeholder(ColorPainter(PlaceholderText)),
                failure = placeholder(ColorPainter(PlaceholderText)),
                transition = CrossFade
            )
        }

    }
}

private fun displayTitle(
    currentImage: Int,
    images: List<String>,
): String {
    return "${currentImage + 1}/${images.size}"
}

@Preview
@Composable
private fun ImageDetailScreenPreview() {
    ImageDetailScreen(
        uiState = ImageDetailState.init(),
        navigateUp = { },
        onClickDisplayButton = { },
        onClickSave = { _, _ -> },
    )
}