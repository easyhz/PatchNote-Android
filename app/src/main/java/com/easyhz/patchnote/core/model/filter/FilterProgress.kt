package com.easyhz.patchnote.core.model.filter

import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.designSystem.util.button.RadioInterface
import com.easyhz.patchnote.core.model.defect.DefectProgress

enum class FilterProgress(
    val progress: DefectProgress?
): RadioInterface {
    NONE(
        progress = null
    ) {
        override val titleId: Int
            get() = R.string.filter_progress_none
    }, REQUESTED(
        progress = DefectProgress.REQUESTED
    ){
        override val titleId: Int
            get() = R.string.filter_progress_requested
    }, DONE(
        progress = DefectProgress.DONE
    ) {
        override val titleId: Int
            get() = R.string.filter_progress_done
    }
}