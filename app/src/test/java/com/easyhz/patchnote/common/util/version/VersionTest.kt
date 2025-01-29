package com.easyhz.patchnote.common.util.version

import com.easyhz.patchnote.core.common.util.version.Version
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class VersionTest {

    @Test
    fun `버전_같으면_업데이트_필요_없음`() {
        // given
        val newVersion = "1.0.1"
        val currentVersion = "1.0.1"

        // when
        val result = Version.needsUpdate(newVersion, currentVersion)

        // then
        assertFalse(result)
    }

    @Test
    fun `버전_크면_업데이트_필요_없음`() {
        // given
        val newVersion = "1.0.0"
        val currentVersion = "1.0.1"

        // when
        val result = Version.needsUpdate(newVersion, currentVersion)

        // then
        assertFalse(result)
    }

    @Test
    fun `버전_작으면_업데이트_필요`() {
        // given
        val newVersion = "1.1.0"
        val currentVersion = "1.0.1"

        // when
        val result = Version.needsUpdate(newVersion, currentVersion)

        // then
        assertTrue(result)
    }
}