package com.easyhz.patchnote.domain.usecase.sign

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.model.sign.SignType
import com.easyhz.patchnote.data.repository.user.UserRepository
import javax.inject.Inject

class CheckAlreadyUserUseCase @Inject constructor(
    private val userRepository: UserRepository
): BaseUseCase<String, SignType>() {
    override suspend fun invoke(param: String): Result<SignType> = runCatching {
        val result = userRepository.getUserFromRemote(param).getOrNull()
            ?: return@runCatching SignType.NewUser
        userRepository.updateUser(result)
        if (result.teamId.isBlank()) return@runCatching SignType.TeamRequired(result.id, result.phone, result.name)
        SignType.ExistingUser(result.id, result.phone, result.name, result.teamId)
    }
}