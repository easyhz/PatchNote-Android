package com.easyhz.patchnote.ui.screen.image.detail

import com.easyhz.patchnote.core.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.easyhz.patchnote.ui.screen.image.detail.contract.ImageDetailIntent
import com.easyhz.patchnote.ui.screen.image.detail.contract.ImageDetailSideEffect
import com.easyhz.patchnote.ui.screen.image.detail.contract.ImageDetailState

/**
 * Date: 2025. 4. 9.
 * Time: 오후 10:05
 */

@HiltViewModel
class ImageDetailViewModel @Inject constructor(

) : BaseViewModel<ImageDetailState, ImageDetailIntent, ImageDetailSideEffect>(
    initialState = ImageDetailState.init()
) {
    override fun handleIntent(intent: ImageDetailIntent) {
        TODO("Not yet implemented")
        when (intent) {

        }
    }
}