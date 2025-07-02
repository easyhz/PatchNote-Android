package com.easyhz.patchnote.data.mapper

import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.data.mapper.sign.toDto
import org.junit.Test

class UserMapperTest {
    @Test
    fun `dto 변환 확인`() {
        val user = User.Empty

        val result = user.toDto()

        assert(result.id == user.id)
    }
}