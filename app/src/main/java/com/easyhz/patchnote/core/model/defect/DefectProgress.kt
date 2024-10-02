package com.easyhz.patchnote.core.model.defect

enum class DefectProgress {
    REQUESTED, IN_PROGRESS, DONE;
    companion object {
        fun DefectProgress.isDone(): Boolean {
            return this == DONE
        }
    }
}