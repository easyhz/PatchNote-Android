package com.easyhz.patchnote.data.model.sign.common

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class TeamJoinDate(
    @PropertyName("teamID")
    val teamId: String = "",
    @PropertyName("joinDate")
    val joinDate: Timestamp = Timestamp.now(),
)
