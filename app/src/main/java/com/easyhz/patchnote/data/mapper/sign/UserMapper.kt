package com.easyhz.patchnote.data.mapper.sign

import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.data.model.sign.request.SaveUserRequest
import com.easyhz.patchnote.data.model.sign.response.UserResponse

fun User.toRequest() = SaveUserRequest(
    id = id,
    name = name,
    phone = phone,
    teamId = teamId
)

fun UserResponse.toModel() = User(
    id = id,
    name = name,
    phone = phone,
    teamId = teamId
)