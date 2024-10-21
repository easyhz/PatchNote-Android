package com.easyhz.patchnote.domain.usecase.sign

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.data.repository.user.UserRepository
import javax.inject.Inject

class IsLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<Unit, Boolean>() {
    override suspend fun invoke(param: Unit): Result<Boolean> =
        runCatching {
            val isLogin = userRepository.isLogin()
            val user = userRepository.getUserFromLocal().getOrThrow()
            return Result.success(user.id.isNotBlank() && isLogin)
        }
}