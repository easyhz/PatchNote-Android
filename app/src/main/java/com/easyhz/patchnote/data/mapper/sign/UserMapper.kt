package com.easyhz.patchnote.data.mapper.sign

import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.data.model.sign.request.SaveUserRequest

fun User.toRequest() = SaveUserRequest(
    id = id,
    name = name,
    phone = phone
)