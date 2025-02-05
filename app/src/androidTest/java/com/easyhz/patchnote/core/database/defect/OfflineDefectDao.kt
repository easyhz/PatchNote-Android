package com.easyhz.patchnote.core.database.defect

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.easyhz.patchnote.core.database.LocalDatabase
import com.easyhz.patchnote.core.database.defect.dao.OfflineDefectDao
import com.easyhz.patchnote.core.database.mock.defect.MockDefect
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OfflineDefectDao: LocalDatabase() {
    private lateinit var offlineDefectDao: OfflineDefectDao


    @Before
    fun setUp() {
        offlineDefectDao = database.offlineDefectDao
    }

    @Test
    fun `하자_임시저장_저장_및_조회`() = runTest {
        // Given
        val offlineDefect = createMockDefect()
        val offlineDefectImage = createMockImages()

        // When
        offlineDefectDao.saveOfflineDefectWithImages(
            defect = offlineDefect,
            images = offlineDefectImage
        )
        val result = offlineDefectDao.findOfflineDefects("teamId1", "requesterId1")


        // Then
        assertEquals(offlineDefect.id, result[0].defect.id)
        assertEquals(offlineDefectImage.size, result[0].images.size)
    }

    @Test
    fun `하자_임시저장_저장_및_삭제_후_조회`() = runTest {
        // Given
        val offlineDefect = createMockDefect()
        val offlineDefectImage = createMockImages()
        val offlineDefect2 = createMockDefect(defectId = "defectId2")

        // When
        offlineDefectDao.saveOfflineDefectWithImages(
            defect = offlineDefect,
            images = offlineDefectImage
        )
        offlineDefectDao.saveOfflineDefectWithImages(
            defect = offlineDefect2,
            images = offlineDefectImage
        )
        offlineDefectDao.deleteOfflineDefects("defectId1")
        val result = offlineDefectDao.findOfflineDefects("teamId1", "requesterId1")

        // Then
        assertEquals(1, result.size)
        assertEquals(offlineDefect2.id, result[0].defect.id)
    }

    // ✅ Mock 하자 데이터 생성 함수
    private fun createMockDefect(defectId: String = "defectId1") = MockDefect.offlineDefectEntity(
        defectId = defectId,
        teamId = "teamId1",
        requesterId = "requesterId1"
    )

    // ✅ Mock 이미지 데이터 생성 함수
    private fun createMockImages(defectId: String = "defectId1") = MockDefect.offlineDefectImageEntityList(size = 3, defectId = defectId)

}