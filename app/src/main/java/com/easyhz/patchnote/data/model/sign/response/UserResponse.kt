package com.easyhz.patchnote.data.model.sign.response

import com.google.firebase.firestore.PropertyName

data class UserResponse(
    @PropertyName("id")
    val id: String = "",
    @PropertyName("name")
    val name: String = "",
    @PropertyName("phone")
    val phone: String = "",
    @get:PropertyName("teamID")
    @set:PropertyName("teamID")
    var teamId: String = "",
)
