package com.easyhz.patchnote.data.model.sign.response

sealed class RequestVerificationCodeResponse {
    data class ReturnCodeSent(val verificationId: String) : RequestVerificationCodeResponse()
    data class ReturnUid(val uid: String): RequestVerificationCodeResponse()
}