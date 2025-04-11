package com.easyhz.patchnote.ui.screen.image.detail

import android.app.Activity
import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.core.common.util.log.Logger
import com.easyhz.patchnote.core.common.util.resource.ResourceHelper
import com.easyhz.patchnote.core.common.util.serializable.SerializableHelper
import com.easyhz.patchnote.core.model.image.DisplayImage
import com.easyhz.patchnote.domain.usecase.image.CaptureParam
import com.easyhz.patchnote.domain.usecase.image.DisplayParam
import com.easyhz.patchnote.domain.usecase.image.GenerateBitmapFromComposableUseCase
import com.easyhz.patchnote.domain.usecase.image.SaveImagesToDisplayInformationUseCase
import com.easyhz.patchnote.domain.usecase.image.SaveImagesUseCase
import com.easyhz.patchnote.ui.navigation.image.route.ImageDetailArgs
import com.easyhz.patchnote.ui.screen.image.detail.component.ImageDetailBottomBarType
import com.easyhz.patchnote.ui.screen.image.detail.component.ImageDisplayInformation
import com.easyhz.patchnote.ui.screen.image.detail.contract.ImageDetailIntent
import com.easyhz.patchnote.ui.screen.image.detail.contract.ImageDetailSideEffect
import com.easyhz.patchnote.ui.screen.image.detail.contract.ImageDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Date: 2025. 4. 9.
 * Time: 오후 10:05
 */

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    private val logger: Logger,
    private val savedStateHandle: SavedStateHandle,
    private val serializableHelper: SerializableHelper,
    private val resourceHelper: ResourceHelper,
    private val saveImagesUseCase: SaveImagesUseCase,
    private val saveImagesToDisplayInformationUseCase: SaveImagesToDisplayInformationUseCase,
    private val captureComposableUseCase: GenerateBitmapFromComposableUseCase,
) : BaseViewModel<ImageDetailState, ImageDetailIntent, ImageDetailSideEffect>(
    initialState = ImageDetailState.init()
) {
    private val tag = "ImageDetailViewModel"
    override fun handleIntent(intent: ImageDetailIntent) {
        when (intent) {
            is ImageDetailIntent.NavigateUp -> navigateUp()
            is ImageDetailIntent.ClickDisplayButton -> onClickDisplayButton(intent.isChecked)
            is ImageDetailIntent.ClickSaveButton -> onClickSaveButton(
                intent.imageDetailBottomBarType,
                intent.currentImage,
                intent.activity
            )

            is ImageDetailIntent.ChangedCurrentImage -> changeCurrentImage(intent.currentImage)
        }
    }

    init {
        initData()
    }

    private fun initData() {
        val imageDetailArgs: String = savedStateHandle["imageDetailArgs"] ?: return navigateUp()
        val imageDetail =
            serializableHelper.deserialize(imageDetailArgs, ImageDetailArgs::class.java)
                ?: return navigateUp()

        reduce {
            copy(
                images = imageDetail.images,
                currentImage = imageDetail.currentImage,
            )
        }
    }

    private fun navigateUp() {
        postSideEffect { ImageDetailSideEffect.NavigateUp }
    }

    private fun onClickDisplayButton(isChecked: Boolean) {
        reduce { copy(isDisplayInformation = isChecked) }
    }

    private fun onClickSaveButton(type: ImageDetailBottomBarType, currentImage: Int, activity: Activity) {
        val images = when (type) {
            ImageDetailBottomBarType.SAVE_ONE -> {
                listOf(currentState.images[currentImage])
            }

            ImageDetailBottomBarType.SAVE_ALL -> {
                currentState.images
            }
        }

        if (currentState.isDisplayInformation) {
            saveImagesToDisplayInformation(images, activity)
        } else {
            saveImages(images)
        }
    }

    private fun saveImagesToDisplayInformation(images: List<String>, activity: Activity) {
        viewModelScope.launch {
            setLoading(true)
            val information = captureComposable(activity)
            saveImagesToDisplayInformationUseCase.invoke(DisplayParam(images, information))
                .onSuccess {
                    showSnackBar(resourceHelper, R.string.image_detail_save_success) {
                        ImageDetailSideEffect.ShowSnackBar(it)
                    }
                }.onFailure { e ->
                logger.e(tag, "saveImagesToDisplayInformation : ${e.message}", e)
                showSnackBar(resourceHelper, e.handleError()) {
                    ImageDetailSideEffect.ShowSnackBar(it)
                }
            }.also {
                setLoading(false)
            }
        }
    }

    private fun saveImages(images: List<String>) {
        viewModelScope.launch {
            setLoading(true)
            saveImagesUseCase.invoke(images).onSuccess {
                showSnackBar(resourceHelper, R.string.image_detail_save_success) {
                    ImageDetailSideEffect.ShowSnackBar(it)
                }
            }.onFailure { e ->
                logger.e(tag, "saveImages : ${e.message}", e)
                showSnackBar(resourceHelper, e.handleError()) {
                    ImageDetailSideEffect.ShowSnackBar(it)
                }
            }.also {
                setLoading(false)
            }
        }
    }

    private fun changeCurrentImage(currentImage: Int) {
        reduce { copy(currentImage = currentImage) }
    }

    private fun setLoading(isLoading: Boolean) {
        reduce { copy(isLoading = isLoading) }
    }

    private suspend fun captureComposable(
        activity: Activity
    ): Bitmap {
        val param = CaptureParam(
            activity = activity,
            content = {
                ImageDisplayInformation(
                    displayImage = getDisplayImage()
                )
            }
        )
        return captureComposableUseCase.invoke(param).getOrThrow()
    }

    private fun getDisplayImage(): DisplayImage {
        return DisplayImage(
            site = "Sample Site",
            buildingUnit = "Sample Building Unit",
            space = "Sample Space",
            part = "Sample Part",
            workType = "Sample Work Type",
            request = "2024.04.09 김민수",
            completion = "2025.04.09 김민수"
        )
    }
}