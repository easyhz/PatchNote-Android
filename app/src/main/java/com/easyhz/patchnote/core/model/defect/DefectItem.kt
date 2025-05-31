package com.easyhz.patchnote.core.model.defect

import com.easyhz.patchnote.core.common.util.urlDecode
import com.easyhz.patchnote.core.model.image.ImageSize
import java.time.LocalDateTime

/**
 * 하자 데이터
 *
 * @property id 하자 id
 * @property site 현장
 * @property building 동
 * @property unit 호수
 * @property space 공간
 * @property part 유형
 * @property workType 공종
 * @property progress 하자 처리 단계
 * @property thumbnailUrl 썸네일 URL
 * @property beforeDescription 접수 설명
 * @property beforeImageSizes 접수 이미지 사이즈
 * @property beforeImageUrls 접수 이미지 URL
 * @property afterDescription 처리 완료 설명
 * @property afterImageSizes 처리 완료 이미지 사이즈
 * @property afterImageUrls 처리 완료 이미지 URL
 * @property requesterId 접수자 id
 * @property requesterName 접수자 이름
 * @property requesterPhone 접수자 전화번호
 * @property workerId 처리자 id
 * @property workerName 처리자 이름
 * @property workerPhone 처리자 전화번호
 * @property requestDate 접수일
 * @property completionDate 처리 완료 날짜
 * @property search 필터 쿼리를 위한 서치 필드 ( 검색 항목 리스트 )
 */
data class DefectItem(
    val id: String,
    val site: String,
    val building: String,
    val unit: String,
    val space: String,
    val part: String,
    val workType: String,
    val progress: DefectProgress,
    val thumbnailUrl: String,
    val beforeDescription: String,
    val beforeImageSizes: List<ImageSize>,
    val beforeImageUrls: List<String>,
    val afterDescription: String,
    val afterImageSizes: List<ImageSize>,
    val afterImageUrls: List<String>,
    val requesterId: String,
    val requesterName: String,
    val requesterPhone: String,
    val workerId: String?,
    val workerName: String?,
    val workerPhone: String?,
    val requestDate: LocalDateTime,
    val completionDate: LocalDateTime?,
    val search: List<String>,
    val teamId: String
) {
    fun createDefectContent(): List<DefectContent> {
        val defectContentList = mutableListOf<DefectContent>()

        defectContentList.add(
            DefectContent(
                progress = DefectProgress.REQUESTED,
                description = beforeDescription,
                imageSizes = beforeImageSizes,
                imageUrls = beforeImageUrls
            )
        )

        if (workerId.isNullOrBlank()) {
            defectContentList.add(
                DefectContent(
                    progress = DefectProgress.DONE,
                    description = "",
                    imageSizes = emptyList(),
                    imageUrls = emptyList()
                )
            )
        } else {
            defectContentList.add(
                DefectContent(
                    progress = DefectProgress.DONE,
                    description = afterDescription,
                    imageSizes = afterImageSizes,
                    imageUrls = afterImageUrls
                )
            )
        }
        return defectContentList
    }

    fun toDecode(): DefectItem = DefectItem(
        id = id,
        site = site.urlDecode(),
        building = building.urlDecode(),
        unit = unit.urlDecode(),
        space = space.urlDecode(),
        part = part.urlDecode(),
        workType = workType.urlDecode(),
        progress = progress,
        thumbnailUrl = thumbnailUrl,
        beforeDescription = beforeDescription.urlDecode(),
        beforeImageSizes = beforeImageSizes,
        beforeImageUrls = beforeImageUrls,
        afterDescription = afterDescription.urlDecode(),
        afterImageSizes = afterImageSizes,
        afterImageUrls = afterImageUrls,
        requesterId = requesterId,
        requesterName = requesterName.urlDecode(),
        requesterPhone = requesterPhone.urlDecode(),
        workerId = workerId,
        workerName = workerName?.urlDecode(),
        workerPhone = workerPhone?.urlDecode(),
        requestDate = requestDate,
        completionDate = completionDate,
        search = search.map { it.urlDecode() },
        teamId = teamId
    )
}
