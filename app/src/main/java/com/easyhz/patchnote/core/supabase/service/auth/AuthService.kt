package com.easyhz.patchnote.core.supabase.service.auth

import io.github.jan.supabase.auth.user.UserInfo

interface AuthService {
    suspend fun signInWithPhone(phone: String)
    suspend fun verifyOTP(phone: String, otp: String)
    fun getCurrentUser(): UserInfo?
    suspend fun signOut()
    suspend fun clearSession()
}