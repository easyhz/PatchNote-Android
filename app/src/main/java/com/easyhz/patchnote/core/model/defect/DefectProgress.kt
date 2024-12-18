package com.easyhz.patchnote.core.model.defect

import androidx.annotation.StringRes
import com.easyhz.patchnote.R

enum class DefectProgress(
    @StringRes val stringId: Int,
    @StringRes val progressStringId: Int,
) {
    REQUESTED(
        stringId = R.string.requested_content,
        progressStringId = R.string.filter_progress_requested
    ), IN_PROGRESS(
        stringId = R.string.in_progress_content,
        progressStringId = R.string.filter_progress_requested
    ), DONE(
        stringId = R.string.done_content,
        progressStringId = R.string.filter_progress_done
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