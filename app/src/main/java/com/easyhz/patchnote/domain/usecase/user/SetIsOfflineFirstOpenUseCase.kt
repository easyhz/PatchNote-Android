package com.easyhz.patchnote.domain.usecase.user

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.data.repository.user.UserRepository
import javax.inject.Inject

class SetIsOfflineFirstOpenUseCase @Inject constructor(
    private val userRepository: UserRepository
): BaseUseCase<Boolean, Unit>() {
    override suspend fun invoke(param: Boolean): Result<Unit> {
        return runCatching {
            userRepository.setIsOfflineFirstOpen(param)
        }
    }
}