package com.easyhz.patchnote.data.model.sign.response

import com.easyhz.patchnote.data.model.sign.common.TeamJoinDateData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class UserResponse(
    @PropertyName("id")
    val id: String = "",
    @PropertyName("name")
    val name: String = "",
    @PropertyName("phone")
    val phone: String = "",
    @get:PropertyName("teamIDs")
    @set:PropertyName("teamIDs")
    var teamIds: List<String> = emptyList(),
    @get:PropertyName("teamJoinDates")
    @set:PropertyName("teamJoinDates")
    var teamJoinDates: List<TeamJoinDateData> = emptyList(),
    @PropertyName("creationTime")
    val creationTime: Timestamp = Timestamp.now()
)