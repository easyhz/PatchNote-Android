package com.easyhz.patchnote.data.datasource.auth

import android.app.Activity
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.common.util.setHandler
import com.easyhz.patchnote.data.model.sign.request.SaveUserRequest
import com.easyhz.patchnote.data.util.Collections.USERS
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): AuthDataSource {
    override fun isLogin(): Boolean = firebaseAuth.currentUser != null

    override fun getUserId(): String? = firebaseAuth.currentUser?.uid

    override fun logout() = firebaseAuth.signOut()

    override fun verifyPhoneNumber(
        phoneNumber: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ): Result<Unit> = runCatching {
        val optionsCompat = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(optionsCompat)
        firebaseAuth.setLanguageCode("kr")
    }

    override suspend fun getCredentials(verificationId: String, code: String): Result<PhoneAuthCredential> = runCatching {
        PhoneAuthProvider.getCredential(verificationId, code)
    }

    override suspend fun signInWithPhone(credential: PhoneAuthCredential): Result<AuthResult> = runCatching {
        return@runCatching firebaseAuth.signInWithCredential(credential).await()
    }

    override suspend fun saveUser(saveUserRequest: SaveUserRequest): Result<Unit> = setHandler(dispatcher) {
        firestore.collection(USERS).document(saveUserRequest.id).set(saveUserRequest)
    }
}