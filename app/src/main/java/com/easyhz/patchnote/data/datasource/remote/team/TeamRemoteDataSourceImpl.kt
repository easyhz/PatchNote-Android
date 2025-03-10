package com.easyhz.patchnote.data.datasource.remote.team

import com.easyhz.patchnote.core.common.constant.Collection.TEAMS
import com.easyhz.patchnote.core.common.constant.Field.INVITE_CODE
import com.easyhz.patchnote.core.common.di.dispatcher.Dispatcher
import com.easyhz.patchnote.core.common.di.dispatcher.PatchNoteDispatchers
import com.easyhz.patchnote.core.common.util.documentHandler
import com.easyhz.patchnote.core.common.util.documentInQueryHandler
import com.easyhz.patchnote.core.common.util.setHandler
import com.easyhz.patchnote.data.model.team.request.TeamRequest
import com.easyhz.patchnote.data.model.team.response.TeamResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class TeamRemoteDataSourceImpl @Inject constructor(
    @Dispatcher(PatchNoteDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val firestore: FirebaseFirestore
): TeamRemoteDateSource {
    override suspend fun findTeamByCode(inviteCode: String): Result<TeamResponse> =
        documentInQueryHandler(dispatcher) {
            firestore.collection(TEAMS).whereEqualTo(INVITE_CODE, inviteCode).limit(1).get()
        }

    override suspend fun createTeam(team: TeamRequest): Result<Unit> = setHandler(dispatcher) {
        firestore.collection(TEAMS).document(team.id).set(team)
    }

    override suspend fun findTeamById(teamId: String): Result<TeamResponse> = documentHandler(dispatcher) {
        firestore.collection(TEAMS).document(teamId).get()
    }
}