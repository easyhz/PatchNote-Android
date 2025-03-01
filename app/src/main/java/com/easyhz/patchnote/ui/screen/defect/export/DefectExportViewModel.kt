package com.easyhz.patchnote.ui.screen.defect.export

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.easyhz.patchnote.core.common.base.BaseViewModel
import com.easyhz.patchnote.core.common.error.handleError
import com.easyhz.patchnote.core.common.util.DateFormatUtil
import com.easyhz.patchnote.core.common.util.toLinkedHashMap
import com.easyhz.patchnote.core.model.filter.Filter
import com.easyhz.patchnote.core.model.filter.FilterParam
import com.easyhz.patchnote.core.model.filter.FilterProgress
import com.easyhz.patchnote.core.model.filter.FilterValue
import com.easyhz.patchnote.core.model.filter.FilterValue.Companion.asInt
import com.easyhz.patchnote.core.model.filter.FilterValue.Companion.asLong
import com.easyhz.patchnote.core.model.filter.FilterValue.Companion.asString
import com.easyhz.patchnote.domain.usecase.defect.ExportDefectUseCase
import com.easyhz.patchnote.ui.screen.defect.export.contract.DefectExportIntent
import com.easyhz.patchnote.ui.screen.defect.export.contract.DefectExportSideEffect
import com.easyhz.patchnote.ui.screen.defect.export.contract.DefectExportState
import com.easyhz.patchnote.ui.screen.defect.export.contract.DefectExportState.Companion.updateFilterItemValue
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DefectExportViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val exportDefectUseCase: ExportDefectUseCase,
): BaseViewModel<DefectExportState, DefectExportIntent, DefectExportSideEffect>(
    DefectExportState.init()
) {
    private val tag = "DefectExportViewModel"

    override fun handleIntent(intent: DefectExportIntent) {
        when (intent) {
            is DefectExportIntent.InitFilter -> {
                initFilter(intent.filterParam)
            }
            is DefectExportIntent.ChangeFilterValue -> {
                changeFilterValue(intent.filter, intent.value)
            }

            is DefectExportIntent.Export -> {
                exportDefect(intent.item)
            }

            is DefectExportIntent.NavigateToUp -> {
                navigateToUp()
            }

            is DefectExportIntent.ClearFilterValue -> {
                clearFilterValue(intent.filter)
            }
            is DefectExportIntent.Reset -> {
                reduce { DefectExportState.init() }
            }
            is DefectExportIntent.HideExportDialog -> setIsShowExportDialog(false)
            is DefectExportIntent.ShowExportDialog -> setIsShowExportDialog(true)
            is DefectExportIntent.SetLoading -> reduce { copy(isLoading = intent.value) }
            is DefectExportIntent.ClickExport -> onClickExport()
            is DefectExportIntent.HideDateDialog -> setIsShowDateDialog(false)
        }
    }

    private fun initFilter(filterParam: FilterParam) {
        reduce { copy(filterItem = Filter.associateFilterValue(filterParam), isShowDateDialog = filterParam.isDateAllEmpty()) }
    }

    private fun changeFilterValue(filter: Filter, value: FilterValue) {
        reduce { updateFilterItemValue(filter, value) }
    }

    private fun exportDefect(item: LinkedHashMap<String, String>) {
        setIsShowExportDialog(false)
        reduce { copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            currentState.filterItem[Filter.REQUESTER]?.asString().takeIf { !it.isNullOrBlank() }?.let {
                item[Filter.REQUESTER.alias] = it
            }
            val indexFieldParam = currentState.filterItem.entries
                .filter { !it.key.isInSearchField }
                .filter {
                    it.value.asLong() != null || it.value.asInt() != 0 || it.value.asString().isNotEmpty()
                }
                .associate { entry ->
                    val value = when {
                        entry.value.asInt() != 0 -> FilterProgress.entries[entry.value.asInt()].progress.toString()
                        entry.value.asLong() != null -> DateFormatUtil.convertMillisToDate(entry.value.asLong()!!)
                        else -> entry.value.asString()
                    }
                    entry.key.alias to value
                }
                .toLinkedHashMap()

            val filterParam = FilterParam(
                searchFieldParam = item,
                indexFieldParam = indexFieldParam,
            )

            exportDefectUseCase.invoke(filterParam).onSuccess {
                postSideEffect { DefectExportSideEffect.ShareIntent(it) }
            }.onFailure { e ->
                Log.e(tag, "exportDefect : $e", e)
                showSnackBar(context, e.handleError()) {
                    DefectExportSideEffect.ShowSnackBar(it)
                }
            }.also {
                reduce { copy(isLoading = false) }
                clearFocus()
            }
        }
    }

    private fun onClickExport() {
        if (currentState.isDateAllEmpty()) {
            setIsShowDateDialog(true)
            return
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            postSideEffect { DefectExportSideEffect.RequestPermission }
        } else {
            setIsShowExportDialog(true)
        }
    }

    private fun clearFocus() {
        postSideEffect { DefectExportSideEffect.ClearFocus }
    }

    private fun navigateToUp() {
        postSideEffect { DefectExportSideEffect.NavigateToUp }
    }

    private fun clearFilterValue(filter: Filter) {
        reduce { updateFilterItemValue(filter, filter.createEmptyValue()) }
    }

    private fun setIsShowExportDialog(value: Boolean) {
        reduce { copy(isShowExportDialog = value) }
    }

    private fun setIsShowDateDialog(value: Boolean) {
        reduce { copy(isShowDateDialog = value) }
    }

}