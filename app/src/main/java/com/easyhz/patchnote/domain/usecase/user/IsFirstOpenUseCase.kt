package com.easyhz.patchnote.domain.usecase.user

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.data.repository.user.UserRepository
import javax.inject.Inject

class IsFirstOpenUseCase @Inject constructor(
    private val userRepository: UserRepository
): BaseUseCase<Unit, Boolean>() {
    override suspend fun invoke(param: Unit): Result<Boolean> {
        return userRepository.isFirstOpen()
    }
}