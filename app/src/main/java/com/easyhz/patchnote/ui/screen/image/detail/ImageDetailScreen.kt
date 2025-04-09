package com.easyhz.patchnote.ui.screen.image.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easyhz.patchnote.core.common.util.collectInSideEffectWithLifecycle
import com.easyhz.patchnote.core.model.image.DefectImage
import com.easyhz.patchnote.ui.screen.image.detail.component.ImageDetailBottomBar
import com.easyhz.patchnote.ui.screen.image.detail.component.ImageDetailTopBar
import com.easyhz.patchnote.ui.screen.image.detail.contract.ImageDetailState
import com.easyhz.patchnote.ui.theme.MainText

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
        uiState = uiState
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        TODO("Not yet implemented")
        when (sideEffect) {

        }
    }
}

@Composable
private fun ImageDetailScreen(
    modifier: Modifier = Modifier,
    uiState: ImageDetailState,
) {
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
                navigateUp = { /* TODO: Handle navigation */ },
                onClickDisplayButton = { }
            )
        },
        floatingActionButton = {
            ImageDetailBottomBar(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 20.dp)
            ) {

            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { _ ->
        Box {

        }
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
        uiState = ImageDetailState.init()
    )
}