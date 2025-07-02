package com.easyhz.patchnote.domain.usecase.sign

import com.easyhz.patchnote.core.common.base.BaseUseCase
import com.easyhz.patchnote.data.model.sign.param.RequestVerificationCodeParam
import com.easyhz.patchnote.data.model.sign.param.RequestVerificationCodeParam.Companion.phoneNumberToCountryCode
import com.easyhz.patchnote.data.repository.sign.SignRepository
import javax.inject.Inject

class RequestVerificationCodeUseCase @Inject constructor(
    private val signRepository: SignRepository
): BaseUseCase<RequestVerificationCodeParam, String>() {
    override suspend fun invoke(param: RequestVerificationCodeParam): Result<String> = runCatching {
        val phoneNumber = param.phoneNumberToCountryCode().getOrThrow()
        signRepository.signInWithPhone(phoneNumber)
        return@runCatching phoneNumber
    }
}