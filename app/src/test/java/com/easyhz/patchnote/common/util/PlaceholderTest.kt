package com.easyhz.patchnote.common.util

import com.easyhz.patchnote.core.common.util.getPostposition
import org.junit.Test

class PlaceholderTest {
    @Test
    fun `"를" 조사 확인`() {
        // given
        val word = "부위"

        // when
        val result = getPostposition(word)

        // then
        assert(result == "${word}를")
    }

    @Test
    fun `"을" 조사 확인`() {
        // given
        val word = "사람"

        // when
        val result = getPostposition(word)

        // then
        assert(result == "${word}을")
    }

    @Test
    fun `조사 확인 - 영어일 때는 없어야 한다`() {
        // given
        val word = "people"

        // when
        val result = getPostposition(word)

        // then
        assert(result == word)
    }
}