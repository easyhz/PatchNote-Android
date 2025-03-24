package com.easyhz.patchnote.domain.usecase.defect.defect

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.model.defect.Defect
import com.easyhz.patchnote.data.repository.defect.DefectRepository
import com.easyhz.patchnote.data.repository.user.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchDefectUseCase @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val defectRepository: DefectRepository,
    private val userRepository: UserRepository,
): BaseUseCase<String, Defect>() {

    override suspend fun invoke(param: String): Result<Defect> = runCatching {
        withContext(dispatcher) {
            val defectDeferred = async { defectRepository.fetchDefect(param) }
            val userIdDeferred = async { userRepository.getUserId() }

            val userId = userIdDeferred.await()
            val defect = defectDeferred.await().getOrThrow()

            Defect(
                defectItem = defect,
                isOwner = defect.requesterId == userId
            )
        }
    }
}