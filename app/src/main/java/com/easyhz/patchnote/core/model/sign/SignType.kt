package com.easyhz.patchnote.core.model.sign
sealed class SignType {
    data object NewUser : SignType()
    data class TeamRequired(val uid: String, val phoneNumber: String, val userName: String) : SignType()
    data class ExistingUser(val uid: String, val phoneNumber: String, val userName: String, val teamId: String) : SignType()
}