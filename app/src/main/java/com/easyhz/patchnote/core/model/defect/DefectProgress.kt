package com.easyhz.patchnote.core.model.defect

import androidx.annotation.StringRes
import com.easyhz.patchnote.R

enum class DefectProgress(
    @StringRes val stringId: Int
) {
    REQUESTED(
        stringId = R.string.requested_content
    ), IN_PROGRESS(
        stringId = R.string.in_progress_content
    ), DONE(
        stringId = R.string.done_content
    );
    companion object {
        fun DefectProgress.isDone(): Boolean {
            return this == DONE
        }

        fun toProgress(): List<DefectProgress> {
            return entries.filter { it != IN_PROGRESS }
        }
    }
}