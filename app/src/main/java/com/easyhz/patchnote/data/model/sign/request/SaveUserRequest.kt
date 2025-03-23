package com.easyhz.patchnote.data.model.sign.request

import com.easyhz.patchnote.data.model.sign.common.TeamJoinDateData
import com.google.firebase.firestore.PropertyName


data class SaveUserRequest(
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
)
