package com.easyhz.patchnote.core.model.defect

import androidx.annotation.StringRes
import com.easyhz.patchnote.R

data class DefectUser(
    val name: String,
    val date: String,
) {
    companion object {
        fun create(
            name: String?,
            date: String?
        ) : DefectUser? {
            if (name == null || date == null) {
                return null
            }
            return DefectUser(
                name = name,
                date = date
            )
        }
    }
}

enum class DefectUserType(
    @StringRes val title: Int
) {
    REQUESTER(
        title = R.string.defect_entry_receipt
    ), WORKER(
        title = R.string.defect_done
    ), OFFLINE(
        title = R.string.defect_offline
    )
}