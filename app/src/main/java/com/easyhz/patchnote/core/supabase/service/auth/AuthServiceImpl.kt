package com.easyhz.patchnote.core.supabase.service.auth

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.providers.builtin.OTP
import io.github.jan.supabase.auth.user.UserInfo
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
    private val auth: Auth
): AuthService {
    override suspend fun signInWithPhone(phone: String) {
        auth.signInWith(OTP) {
            this.phone = phone
        }
    }

    override suspend fun verifyOTP(
        phone: String,
        otp: String
    ) {
        auth.verifyPhoneOtp(
            type = OtpType.Phone.SMS,
            phone = phone,
            token = otp
        )
    }

    override fun getCurrentUser(): UserInfo? {
        return auth.currentUserOrNull()
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun clearSession() {
        auth.clearSession()
    }
}
