package com.easyhz.patchnote.data.repository.defect

import com.easyhz.patchnote.core.model.defect.EntryDefect

interface DefectRepository {
    suspend fun createDefect(param: EntryDefect): Result<Unit>
}