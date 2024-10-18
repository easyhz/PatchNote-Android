package com.easyhz.patchnote.core.model.filter

import android.content.Context
import com.easyhz.patchnote.R
import kotlinx.serialization.Serializable

@Serializable
data class FilterParam(
    val searchFieldParam: LinkedHashMap<String, String> = linkedMapOf(),
    val indexFieldParam: LinkedHashMap<String, String> = linkedMapOf(),
) {
    fun toIndexField(): IndexField {
        return IndexField(
            progress = indexFieldParam["progress"],
            requestDate = indexFieldParam["requestDate"],
            workerName = indexFieldParam["workerName"],
            completionDate = indexFieldParam["completionDate"],
        )
    }

    fun toList(context: Context): List<String> {
        val searchField = searchFieldParam.entries.map { entry ->
            formatField(entry.key, entry.value, context)
        }

        val indexField = indexFieldParam.entries.map { entry ->
            formatIndexField(entry.key, entry.value, context)
        }

        return searchField + indexField
    }

    private fun formatField(key: String, value: String, context: Context): String {
        return when (key) {
            "requesterName" -> "$value ${context.getString(R.string.defect_request)}"
            else -> value
        }
    }

    private fun formatIndexField(key: String, value: String, context: Context): String {
        return when (key) {
            "requestDate" -> "$value ${context.getString(R.string.defect_request)}"
            "workerName", "completionDate" -> "$value ${context.getString(R.string.defect_done)}"
            "progress" -> context.getString(FilterProgress.valueOf(value).titleId)
            else -> value
        }
    }
}

data class IndexField(
    val progress: String?,
    val requestDate: String?,
    val workerName: String?,
    val completionDate: String?,
)