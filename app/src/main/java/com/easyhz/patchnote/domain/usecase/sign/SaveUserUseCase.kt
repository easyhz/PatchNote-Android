package com.easyhz.patchnote.domain.usecase.sign

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.data.repository.user.UserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val userRepository: UserRepository
): BaseUseCase<User, Unit>() {
    override suspend fun invoke(param: User): Result<Unit> = runCatching {
        userRepository.saveUser(param)
        userRepository.updateUser(param)
    }
}