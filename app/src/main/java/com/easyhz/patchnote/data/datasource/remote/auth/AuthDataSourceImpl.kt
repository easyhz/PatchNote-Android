package com.easyhz.patchnote.data.datasource.remote.auth

import android.app.Activity
import com.easyhz.patchnote.core.common.constant.Collection.USERS
import com.easyhz.patchnote.core.common.constant.Field.TEAM_ID_LIST
import com.easyhz.patchnote.core.common.constant.Field.TEAM_JOIN_DATES
import com.easyhz.patchnote.core.common.constant.Field.USER_NAME
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.common.util.documentHandler
import com.easyhz.patchnote.core.common.util.fetchHandler
import com.easyhz.patchnote.core.common.util.setHandler
import com.easyhz.patchnote.data.model.sign.request.SaveUserRequest
import com.easyhz.patchnote.data.model.sign.response.UserResponse
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query.Direction
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

    override fun logOut() = firebaseAuth.signOut()

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

    override suspend fun getUser(uid: String): Result<UserResponse> = documentHandler(dispatcher) {
        firestore.collection(USERS).document(uid).get()
    }

    override suspend fun deleteUser(uid: String): Result<Unit> = setHandler(dispatcher) {
        firestore.collection(USERS).document(uid).delete()
    }

    override suspend fun leaveTeam(uid: String, teamId: String): Result<Unit> = setHandler(dispatcher) {
        firestore.runTransaction { transaction ->
            val docRef = firestore.collection(USERS).document(uid)
            val user = transaction.get(docRef).toObject(UserResponse::class.java)
                ?: throw IllegalStateException("User not found")

            val teamIdList = user.teamIds.toMutableList()
            teamIdList.remove(teamId)

            val mutableJoinDates = user.teamJoinDates.toMutableList()
            mutableJoinDates.removeIf { it.teamId == teamId }

            transaction.update(docRef, TEAM_ID_LIST, teamIdList)
            transaction.update(docRef, TEAM_JOIN_DATES, mutableJoinDates)

            null
        }
    }

    override suspend fun fetchUsers(teamId: String): Result<List<UserResponse>> = fetchHandler(dispatcher) {
        firestore.collection(USERS)
            .whereArrayContains(TEAM_ID_LIST, teamId)
            .orderBy(USER_NAME, Direction.ASCENDING)
            .get()
    }
}