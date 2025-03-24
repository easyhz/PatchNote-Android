package com.easyhz.patchnote.domain.usecase.defect.defect

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.model.filter.FilterParam
import com.easyhz.patchnote.data.repository.defect.DefectRepository
import com.easyhz.patchnote.data.repository.user.UserRepository
import java.io.File
import javax.inject.Inject

class ExportDefectUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val defectRepository: DefectRepository
): BaseUseCase<FilterParam, File>() {
    override suspend fun invoke(param: FilterParam): Result<File> = runCatching {
        val user = userRepository.getUserFromLocal().getOrThrow()
        val defects = defectRepository.fetchDefects(param, user).getOrThrow()
        return defectRepository.exportDefects(defects)
    }
}