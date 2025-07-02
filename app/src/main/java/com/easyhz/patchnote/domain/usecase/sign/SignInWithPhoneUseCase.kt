package com.easyhz.patchnote.domain.usecase.sign

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.data.model.sign.param.SignInWithPhoneParam
import com.easyhz.patchnote.data.repository.sign.SignRepository
import com.easyhz.patchnote.data.repository.user.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInWithPhoneUseCase @Inject constructor(
    @Dispatcher(dispatcher = PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val signRepository: SignRepository,
    private val userRepository: UserRepository,
) : BaseUseCase<SignInWithPhoneParam, String>() {
    override suspend fun invoke(param: SignInWithPhoneParam): Result<String> = withContext(dispatcher) {
        return@withContext runCatching {
            signRepository.verifyOTP(phoneNumber = param.phoneNumber, otp = param.code).getOrThrow()
            val userId = userRepository.getUserId()
            userId ?: throw Exception("User not found")
        }
    }
}