package com.easyhz.patchnote.ui.navigation.home

import kotlinx.serialization.Serializable

@Serializable
data class Home(
    val searchParam: List<String>? = null,
)