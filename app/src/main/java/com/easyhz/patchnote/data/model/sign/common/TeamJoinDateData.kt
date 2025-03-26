package com.easyhz.patchnote.data.model.sign.common

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class TeamJoinDateData(
    @set:PropertyName("teamID")
    @get:PropertyName("teamID")
    var teamId: String = "",
    @PropertyName("joinDate")
    val joinDate: Timestamp = Timestamp.now(),
)
