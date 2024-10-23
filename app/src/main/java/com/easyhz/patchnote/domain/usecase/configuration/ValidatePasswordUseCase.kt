package com.easyhz.patchnote.domain.usecase.configuration

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.data.repository.configuration.ConfigurationRepository
import javax.inject.Inject

/**
 *  받아온 비밀번호가 유효한지 확인
 *  - 입력한 적 있으면 -> true
 *  - 변경되면 -> false
 *  - 입력한 적 없으면 -> false
 *  */
class ValidatePasswordUseCase @Inject constructor(
    private val configurationRepository: ConfigurationRepository
): BaseUseCase<String, Boolean>() {
    override suspend fun invoke(param: String): Result<Boolean> = runCatching {
        val password = configurationRepository.getPassword().getOrNull()
        return@runCatching if(password == param) {
            configurationRepository.checkEnteredPassword().getOrNull() ?: false
        } else {
            configurationRepository.updatePassword(param)
            configurationRepository.updateEnteredPassword(false)
            false
        }
    }
}