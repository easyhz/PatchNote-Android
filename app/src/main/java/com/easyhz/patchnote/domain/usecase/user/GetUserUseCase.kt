package com.easyhz.patchnote.domain.usecase.user

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.data.repository.user.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
): BaseUseCase<Unit, User>() {
    override suspend fun invoke(param: Unit): Result<User> {
        return userRepository.getUserFromLocal()
    }
}