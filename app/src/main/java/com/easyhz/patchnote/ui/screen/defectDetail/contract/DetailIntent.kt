package com.easyhz.patchnote.ui.screen.defectDetail.contract

import com.easyhz.patchnote.core.common.base.UiIntent

sealed class DetailIntent: UiIntent() {
    data class FetchData(val defectId: String): DetailIntent()
    data object NavigateToUp: DetailIntent()
    data object CompleteDefect: DetailIntent()
}