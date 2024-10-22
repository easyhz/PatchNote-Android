package com.easyhz.patchnote.domain.usecase.sign

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.data.repository.user.UserRepository
import javax.inject.Inject

class CheckAlreadyUserUseCase @Inject constructor(
    private val userRepository: UserRepository
): BaseUseCase<String, Boolean>() {
    override suspend fun invoke(param: String): Result<Boolean> = runCatching {
        val result = userRepository.getUserFromRemote(param).getOrNull()
        if (result != null) { userRepository.updateUser(result) }
        result != null
    }
}