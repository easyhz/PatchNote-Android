package com.easyhz.patchnote.core.common.util

import com.github.f4b6a3.uuid.UuidCreator
import java.util.UUID

object Generate {
    fun randomUUID() = UuidCreator.getTimeOrderedEpoch().toString()
    fun randomUuid() = UUID.randomUUID().toString()
    fun randomInviteCode() = randomUUID().replace("-", "").slice(0..15)
}