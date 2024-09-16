package com.easyhz.patchnote.domain.usecase.sign

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.data.model.sign.request.SaveUserRequest
import com.easyhz.patchnote.data.repository.user.UserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val userRepository: UserRepository
): BaseUseCase<SaveUserRequest, Unit>() {
    override suspend fun invoke(param: SaveUserRequest): Result<Unit> {
        return userRepository.saveUser(param)
    }
}