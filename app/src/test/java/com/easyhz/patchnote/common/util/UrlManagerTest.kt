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
        val result = word.urlEncode()

        // then
        assert(result == "%EB%B6%80%EC%97%8C%2F%EC%8B%9D%EB%8B%B9")
    }

    @Test
    fun `디코딩_확인`() {
        // given
        val word = "%EB%B6%80%EC%97%8C%2F%EC%8B%9D%EB%8B%B9"

        // when
        val result = word.urlDecode()

        // then
        assert(result == "부엌/식당")
    }
}