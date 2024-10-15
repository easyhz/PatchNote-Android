package com.easyhz.patchnote.core.model.filter

import androidx.annotation.StringRes
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.common.util.toLinkedHashMap

enum class Filter(
    @StringRes val nameId: Int,
    val alias: String,
    val filterType: FilterType,
    val isInSearchField: Boolean = false
) {
    PROGRESS(
        nameId = R.string.filter_progress,
        alias = "progress",
        filterType = FilterType.RADIO,
    ),REQUESTER(
        nameId = R.string.filter_requester,
        alias = "requesterName",
        filterType = FilterType.FREE_FORM,
        isInSearchField = true
    ), REQUEST_DATE(
        nameId = R.string.filter_request_date,
        alias = "requestDate",
        filterType = FilterType.DATE
    ), WORKER(
        nameId = R.string.filter_worker,
        alias = "workerName",
        filterType = FilterType.FREE_FORM
    ), COMPLETION_DATE(
        nameId = R.string.filter_completion_date,
        alias = "completionDate",
        filterType = FilterType.DATE
    );


    fun createEmptyValue(): FilterValue {
        return when (filterType) {
            FilterType.RADIO -> FilterValue.IntValue(0)
            FilterType.FREE_FORM -> FilterValue.StringValue("")
            FilterType.DATE -> FilterValue.LongValue(null)
        }
    }

    companion object {
        fun toLinkedHashMap(): LinkedHashMap<Filter, FilterValue> =
            LinkedHashMap(entries.associateWith { it.createEmptyValue() })

        fun associateFilterValue(value: LinkedHashMap<String, String>): LinkedHashMap<Filter, FilterValue> {
            return entries.associateWith { FilterValue.StringValue(value[it.alias] ?: "") }.toLinkedHashMap()
        }
    }

}

sealed class FilterValue {
    abstract val value: Any?
    data class IntValue(override val value: Int) : FilterValue()
    data class StringValue(override val value: String) : FilterValue()
    data class LongValue(override val value: Long?) : FilterValue()

    companion object{
        fun FilterValue.asInt(): Int = (this as? IntValue)?.value ?: 0
        fun FilterValue.asString(): String = (this as? StringValue)?.value ?: ""
        fun FilterValue.asLong(): Long? = (this as? LongValue)?.value
    }
}