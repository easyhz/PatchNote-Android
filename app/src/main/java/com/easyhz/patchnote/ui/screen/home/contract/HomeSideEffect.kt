package com.easyhz.patchnote.ui.screen.home.contract

import com.easyhz.patchnote.core.common.base.UiSideEffect
import com.easyhz.patchnote.core.model.defect.DefectItem
import java.io.File

sealed class HomeSideEffect: UiSideEffect() {
    data class NavigateToSetting(val url: String): HomeSideEffect()
    data object NavigateToDefectEntry: HomeSideEffect()
    data object NavigateToFilter: HomeSideEffect()
    data class NavigateToDefectDetail(val defectItem: DefectItem): HomeSideEffect()
    data class NavigateToVersionUpdate(val url: String): HomeSideEffect()
    data object RequestFocus: HomeSideEffect()
    data object RequestPermission: HomeSideEffect()
    data class ShareIntent(val file: File): HomeSideEffect()
}