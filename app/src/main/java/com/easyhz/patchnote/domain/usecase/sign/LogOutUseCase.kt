package com.easyhz.patchnote.domain.usecase.sign

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.data.repository.user.UserRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val userRepository: UserRepository,
): BaseUseCase<Unit, Unit>() {
    override suspend fun invoke(param: Unit): Result<Unit> = runCatching {
        userRepository.deleteUserFromLocal()
        userRepository.logOut()
    }
}