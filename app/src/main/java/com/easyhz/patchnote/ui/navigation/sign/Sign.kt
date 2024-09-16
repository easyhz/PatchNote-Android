package com.easyhz.patchnote.ui.navigation.sign

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
internal object Sign: Parcelable {
    @Serializable
    data object Phone

    @Serializable
    data class Verification(
        val verificationId: String,
        val phoneNumber: String
    )

    @Serializable
    data class Name(
        val uid: String,
        val phoneNumber: String
    )

}