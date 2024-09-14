package com.easyhz.patchnote.data.repository.sign

import android.util.Log
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.data.model.sign.param.RequestVerificationCodeParam
import com.easyhz.patchnote.data.model.sign.param.RequestVerificationCodeParam.Companion.phoneNumberToCountryCode
import com.easyhz.patchnote.data.model.sign.response.RequestVerificationCodeResponse
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume

class SignRepositoryImpl @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val firebaseAuth: FirebaseAuth
) : SignRepository {

    override suspend fun requestVerificationCode(param: RequestVerificationCodeParam): Result<RequestVerificationCodeResponse> {
        return suspendCancellableCoroutine { continuation ->
            try {
                val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        handleVerificationCompleted(credential, continuation)
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        continuation.resume(Result.failure(e))
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        continuation.resume(
                            Result.success(
                                RequestVerificationCodeResponse.ReturnCodeSent(verificationId = verificationId)
                            )
                        )
                    }
                }

                val phoneNumber = param.phoneNumberToCountryCode().getOrNull()
                if (phoneNumber == null) {
                    continuation.resume(Result.failure(Exception("Invalid phone number")))
                    return@suspendCancellableCoroutine
                }

                val optionsCompat = PhoneAuthOptions.newBuilder(firebaseAuth)
                    .setPhoneNumber(phoneNumber)
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(param.activity)
                    .setCallbacks(callbacks)
                    .build()

                PhoneAuthProvider.verifyPhoneNumber(optionsCompat)
                firebaseAuth.setLanguageCode("kr")

                // 취소
                continuation.invokeOnCancellation {
                    Log.d("SignRepositoryImpl", "requestVerificationCode: cancelled")
                }
            } catch (e: Exception) {
                continuation.resume(Result.failure(e))
            }
        }
    }

    private fun handleVerificationCompleted(
        credential: PhoneAuthCredential,
        continuation: CancellableContinuation<Result<RequestVerificationCodeResponse>>
    ) {
        val scope = CoroutineScope(continuation.context)
        scope.launch {
            try {
                val authResult = signInWithPhone(credential).getOrNull()
                    ?: throw Exception("Sign in failed")
                authResult.user?.uid?.let { uid ->
                    continuation.resume(
                        Result.success(
                            RequestVerificationCodeResponse.ReturnUid(uid = uid)
                        )
                    )
                } ?: throw Exception("User is null")
            } catch (e: Exception) {
                continuation.resume(Result.failure(e))
            }
        }
    }
    override suspend fun getCredentials(verificationId: String, code: String): Result<PhoneAuthCredential> = runCatching {
        PhoneAuthProvider.getCredential(verificationId, code)
    }

    override suspend fun signInWithPhone(credential: PhoneAuthCredential): Result<AuthResult> = runCatching {
        return@runCatching firebaseAuth.signInWithCredential(credential).await()
    }
}