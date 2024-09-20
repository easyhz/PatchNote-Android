package com.easyhz.patchnote.data.model.sign.request

import com.google.firebase.firestore.PropertyName


data class SaveUserRequest(
    @PropertyName("id")
    val id: String = "",
    @PropertyName("name")
    val name: String = "",
    @PropertyName("phone")
    val phone: String = "",
)
