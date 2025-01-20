package com.easyhz.patchnote.ui.screen.export.contract

import com.easyhz.patchnote.core.common.base.UiIntent
import com.easyhz.patchnote.core.model.filter.Filter
import com.easyhz.patchnote.core.model.filter.FilterParam
import com.easyhz.patchnote.core.model.filter.FilterValue

sealed class DefectExportIntent: UiIntent() {
    data class InitFilter(val filterParam: FilterParam): DefectExportIntent()
    data class ChangeFilterValue(val filter: Filter, val value: FilterValue): DefectExportIntent()
    data class Export(val item: LinkedHashMap<String, String>): DefectExportIntent()
    data object NavigateToUp: DefectExportIntent()
    data class ClearFilterValue(val filter: Filter): DefectExportIntent()
    data object Reset: DefectExportIntent()
    data object HideExportDialog: DefectExportIntent()
    data object ShowExportDialog: DefectExportIntent()
    data object ClickExport: DefectExportIntent()
    data class SetLoading(val value: Boolean): DefectExportIntent()
    data object HideDateDialog: DefectExportIntent()
}