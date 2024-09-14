package com.easyhz.patchnote.model.sign.request

import android.app.Activity
import com.easyhz.patchnote.data.model.sign.param.RequestVerificationCodeParam
import com.easyhz.patchnote.data.model.sign.param.RequestVerificationCodeParam.Companion.phoneNumberToCountryCode
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RequestVerificationCodeParamTest {
    private lateinit var activity: Activity

    @Before
    fun setUp() {
        activity = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `🚀 (FAILURE) phoneNumberToCountryCode() - 국가 코드 변환 실패 `() {
        val param = RequestVerificationCodeParam("123", activity)

        val result = param.phoneNumberToCountryCode()
        assertTrue(result.isFailure)
    }

    @Test
    fun `🚀 (SUCCESS) phoneNumberToCountryCode() - 국가 코드 변환 성공 `() {
        val param = RequestVerificationCodeParam("01012345678", activity)

        val result = param.phoneNumberToCountryCode()
        val exception = "+821012345678"
        assertTrue(result.isSuccess)
        assertEquals(exception, result.getOrNull())
    }
}