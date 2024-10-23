package com.easyhz.patchnote.domain.usecase.configuration

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.data.repository.configuration.ConfigurationRepository
import javax.inject.Inject

/**
 * 비밀번호 입력 여부 업데이트 UseCase
 */
class UpdateEnteredPasswordUseCase @Inject constructor(
    private val configurationRepository: ConfigurationRepository
): BaseUseCase<Boolean, Unit>() {
    override suspend fun invoke(param: Boolean): Result<Unit> = runCatching {
        configurationRepository.updateEnteredPassword(param)
    }
}