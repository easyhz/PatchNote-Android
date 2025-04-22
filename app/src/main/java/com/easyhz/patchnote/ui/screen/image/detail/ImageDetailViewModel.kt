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
import com.easyhz.patchnote.core.common.util.toDateString
import com.easyhz.patchnote.core.model.defect.DefectItem
import com.easyhz.patchnote.core.model.defect.DefectProgress
import com.easyhz.patchnote.core.model.image.DisplayImage
import com.easyhz.patchnote.core.model.image.DisplayImageType
import com.easyhz.patchnote.domain.usecase.image.CaptureParam
import com.easyhz.patchnote.domain.usecase.image.DisplayParam
import com.easyhz.patchnote.domain.usecase.image.GenerateBitmapFromComposableUseCase
import com.easyhz.patchnote.domain.usecase.image.GetImageSettingUseCase
import com.easyhz.patchnote.domain.usecase.image.SaveImagesToDisplayInformationUseCase
import com.easyhz.patchnote.domain.usecase.image.SaveImagesUseCase
import com.easyhz.patchnote.ui.navigation.image.route.ImageDetailArgs
import com.easyhz.patchnote.ui.navigation.image.route.toDefectItem
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
    private val getImageSettingUseCase: GetImageSettingUseCase,
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
            is ImageDetailIntent.ToggleShowTopBar -> {
                reduce { copy(isShowTopBar = !currentState.isShowTopBar) }
            }
        }
    }

    init {
        initData()
        getImageSetting()
    }

    private fun initData() {
        val imageDetailArgs: String = savedStateHandle["imageDetailArgs"] ?: return navigateUp()
        val imageDetail =
            serializableHelper.deserialize(imageDetailArgs, ImageDetailArgs::class.java)
                ?: return navigateUp()

        val defectItem = imageDetail.defectItem.toDefectItem()
        val images = when (imageDetail.selectedTab ) {
            DefectProgress.REQUESTED.name -> defectItem.beforeImageUrls
            DefectProgress.DONE.name -> defectItem.afterImageUrls
            else -> defectItem.beforeImageUrls
        }

        reduce {
            copy(
                defectItem = defectItem,
                images = images,
                currentImage = imageDetail.currentImage,
            )
        }
    }

    private fun getImageSetting() = viewModelScope.launch {
        getImageSettingUseCase.invoke().collect {
            reduce { copy(settingOption = it) }
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
        val defectItem = currentState.defectItem ?: return DisplayImage()
        val settingOption = currentState.settingOption
        return DisplayImage(
            site = settingOption.getIfEnabled(DisplayImageType.SITE) { defectItem.site },
            buildingUnit = settingOption.getIfEnabled(DisplayImageType.BUILDING_UNIT) {
                displayBuildingUnit(defectItem.building, defectItem.unit)
            },
            space = settingOption.getIfEnabled(DisplayImageType.SPACE) { defectItem.space },
            part = settingOption.getIfEnabled(DisplayImageType.PART) { defectItem.part },
            workType = settingOption.getIfEnabled(DisplayImageType.WORK_TYPE) { defectItem.workType },
            request = settingOption.getIfEnabled(DisplayImageType.REQUEST) {
                defectItem.requestDate.toDateString()
            },
            completion = settingOption.getIfEnabled(DisplayImageType.COMPLETION) {
                getCompletion(defectItem)
            },
            beforeDescription = settingOption.getIfEnabled(DisplayImageType.BEFORE_DESCRIPTION) {
                getDescription(defectItem.beforeDescription)
            },
            afterDescription = settingOption.getIfEnabled(DisplayImageType.AFTER_DESCRIPTION) {
                getAfterDescription(defectItem)
            },

        )
    }

    private inline fun <T> Map<DisplayImageType, Boolean>.getIfEnabled(
        key: DisplayImageType,
        block: () -> T
    ): T? = if (this[key] == true) block() else null


    private fun displayBuildingUnit(building: String, unit: String): String {
        return resourceHelper.getString(R.string.home_defect_building_unit_format, building, unit)
    }

    private fun getCompletion(defectItem: DefectItem): String? {
        if(defectItem.progress != DefectProgress.DONE) return null
        if (defectItem.completionDate == null || defectItem.workerName == null) return null
        return defectItem.completionDate.toDateString()
    }

    private fun getAfterDescription(defectItem: DefectItem): String? {
        if(defectItem.progress != DefectProgress.DONE) return null
        return getDescription(defectItem.afterDescription)
    }

    private fun getDescription(description: String): String? {
        if (description.isBlank()) return null
        return description
    }
}