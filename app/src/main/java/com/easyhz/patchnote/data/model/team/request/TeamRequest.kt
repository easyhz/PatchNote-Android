package com.easyhz.patchnote.data.model.team.request

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName


data class TeamRequest(
    @PropertyName("id")
    val id: String = "",
    @PropertyName("name")
    val name: String = "",
    @get:PropertyName("adminID")
    @set:PropertyName("adminID")
    var adminId: String = "",
    @PropertyName("creationTime")
    val creationTime: Timestamp = Timestamp.now(),
    @PropertyName("inviteCode")
    val inviteCode: String = "",
)

