package com.easyhz.patchnote.ui.screen.setting.image

import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.model.image.DisplayImageType
import com.easyhz.patchnote.domain.usecase.image.GetImageSettingUseCase
import com.easyhz.patchnote.domain.usecase.image.SetImageSettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.easyhz.patchnote.ui.screen.setting.image.contract.ImageSettingIntent
import com.easyhz.patchnote.ui.screen.setting.image.contract.ImageSettingSideEffect
import com.easyhz.patchnote.ui.screen.setting.image.contract.ImageSettingState
import kotlinx.coroutines.launch

/**
 * Date: 2025. 4. 11.
 * Time: 오후 10:12
 */

@HiltViewModel
class ImageSettingViewModel @Inject constructor(
    private val getImageSettingUseCase: GetImageSettingUseCase,
    private val setImageSettingUseCase: SetImageSettingUseCase,
) : BaseViewModel<ImageSettingState, ImageSettingIntent, ImageSettingSideEffect>(
    initialState = ImageSettingState.init()
) {
    override fun handleIntent(intent: ImageSettingIntent) {
        when (intent) {
            is ImageSettingIntent.NavigateUp -> navigateUp()
            is ImageSettingIntent.ClickToggleButton -> onClickToggleButton(
                displayImageType = intent.displayImageType,
                isChecked = intent.newValue
            )

        }
    }

    init {
        init()
    }

    private fun init() {
        viewModelScope.launch {
            getImageSettingUseCase().collect { imageSetting ->
                reduce { copy(items = imageSetting) }
            }
        }
    }

    private fun navigateUp() {
        postSideEffect { ImageSettingSideEffect.NavigateUp }
    }

    private fun onClickToggleButton(displayImageType: DisplayImageType, isChecked: Boolean) {
        viewModelScope.launch {
            setImageSettingUseCase(displayImageType, isChecked).onFailure {
                // Handle error
            }
        }
    }
}