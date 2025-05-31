package com.easyhz.patchnote.common.util

import com.easyhz.patchnote.core.common.util.urlDecode
import com.easyhz.patchnote.core.common.util.urlEncode
import org.junit.Test

class UrlManagerTest {
    @Test
    fun `슬래시_인코딩_확인`() {
        // given
        val word = "부엌/식당"

        // when
        val result = word.urlEncode().urlDecode()

        // then
        assert(result == word)
    }
}