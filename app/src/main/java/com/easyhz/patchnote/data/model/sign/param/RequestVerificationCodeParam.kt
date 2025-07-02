package com.easyhz.patchnote.data.model.sign.param

data class RequestVerificationCodeParam(
    val phoneNumber: String,
) {
    companion object {
        fun RequestVerificationCodeParam.phoneNumberToCountryCode(): Result<String> {
            return this.phoneNumber.convertToCountryCode()
        }
    }
}

private fun String.convertToCountryCode(): Result<String> {
    if (this.length !in 10.. 11) {
        return Result.failure(Exception("Invalid phone number"))
    }

    val numberMap = mapOf(
        "010" to "+8210",
        "011" to "+8211",
        "016" to "+8216",
        "017" to "+8217",
        "018" to "+8218",
        "019" to "+8219",
        "106" to "+82106"
    )

    val firstNumber = this.substring(0, 3)
    val phoneEdit = this.substring(3)

    val prefix = numberMap[firstNumber] ?: this.substring(0, this.length)
    val result = prefix + phoneEdit

    return Result.success(result)
}
