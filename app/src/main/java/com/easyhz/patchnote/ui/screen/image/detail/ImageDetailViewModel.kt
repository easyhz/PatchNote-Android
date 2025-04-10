package com.easyhz.patchnote.ui.screen.image.detail

import androidx.lifecycle.SavedStateHandle
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.util.serializable.SerializableHelper
import com.easyhz.patchnote.ui.navigation.image.route.ImageDetailArgs
import com.easyhz.patchnote.ui.screen.image.detail.component.ImageDetailBottomBarType
import com.easyhz.patchnote.ui.screen.image.detail.contract.ImageDetailIntent
import com.easyhz.patchnote.ui.screen.image.detail.contract.ImageDetailSideEffect
import com.easyhz.patchnote.ui.screen.image.detail.contract.ImageDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Date: 2025. 4. 9.
 * Time: 오후 10:05
 */

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val serializableHelper: SerializableHelper,
) : BaseViewModel<ImageDetailState, ImageDetailIntent, ImageDetailSideEffect>(
    initialState = ImageDetailState.init()
) {
    override fun handleIntent(intent: ImageDetailIntent) {
        when (intent) {
            is ImageDetailIntent.NavigateUp -> navigateUp()
            is ImageDetailIntent.ClickDisplayButton -> onClickDisplayButton(intent.isChecked)
            is ImageDetailIntent.ClickSaveButton -> onClickSaveButton(intent.imageDetailBottomBarType, intent.currentImage)
            is ImageDetailIntent.ChangedCurrentImage -> changeCurrentImage(intent.currentImage)
        }
    }
    init {
        initData()
    }

    private fun initData() {
        val imageDetailArgs: String = savedStateHandle["imageDetailArgs"] ?: return navigateUp()
        val imageDetail = serializableHelper.deserialize(imageDetailArgs, ImageDetailArgs::class.java) ?: return navigateUp()

        reduce {
            copy(
                images = imageDetail.images,
                currentImage = imageDetail.currentImage,
            )
        }
    }

    private fun navigateUp() {
        postSideEffect {  ImageDetailSideEffect.NavigateUp }
    }

    private fun onClickDisplayButton(isChecked: Boolean) {
        reduce { copy(isDisplayInformation = isChecked) }
    }

    private fun onClickSaveButton(type: ImageDetailBottomBarType, currentImage: Int) {
        when(type) {
            ImageDetailBottomBarType.SAVE_ONE -> {
                // Save ONe
            }
            ImageDetailBottomBarType.SAVE_ALL -> {
                // Save ALl
            }
        }
    }

    private fun changeCurrentImage(currentImage: Int) {
        reduce { copy(currentImage = currentImage) }
    }
}