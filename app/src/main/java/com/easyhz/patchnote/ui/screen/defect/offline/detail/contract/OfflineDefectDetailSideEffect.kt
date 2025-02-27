package com.easyhz.patchnote.ui.screen.defect.offline.detail.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect

sealed class OfflineDefectDetailSideEffect: UiSideEffect() {
    data object NavigateToUp: OfflineDefectDetailSideEffect()
}