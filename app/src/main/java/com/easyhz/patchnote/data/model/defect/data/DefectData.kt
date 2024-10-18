package com.easyhz.patchnote.data.model.defect.data

import com.easyhz.patchnote.core.model.defect.DefectProgress
import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName


/**
 * 하자 데이터
 *
 * @property id 하자 id
 * @property site 현장
 * @property building 동
 * @property unit 호수
 * @property space 공간
 * @property part 부위
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
data class DefectData(
    @PropertyName("id")
    val id: String = "",
    @PropertyName("site")
    val site: String = "",
    @PropertyName("building")
    val building: String = "",
    @PropertyName("unit")
    val unit: String = "",
    @PropertyName("space")
    val space: String = "",
    @PropertyName("part")
    val part: String = "",
    @PropertyName("workType")
    val workType: String = "",
    @PropertyName("progress")
    var progress: String = DefectProgress.REQUESTED.name,
    @get:PropertyName("thumbnailURL")
    @set:PropertyName("thumbnailURL")
    var thumbnailUrl: String = "",
    @PropertyName("beforeDescription")
    val beforeDescription: String = "",
    @PropertyName("beforeImageSizes")
    val beforeImageSizes: List<ImageSizeData> = emptyList(),
    @get:PropertyName("beforeImageURLs")
    @set:PropertyName("beforeImageURLs")
    var beforeImageUrls: List<String> = emptyList(),
    @PropertyName("afterDescription")
    val afterDescription: String = "",
    @PropertyName("afterImageSizes")
    val afterImageSizes: List<ImageSizeData> = emptyList(),
    @get:PropertyName("afterImageURLs")
    @set:PropertyName("afterImageURLs")
    var afterImageUrls: List<String> = emptyList(),
    @get:PropertyName("requesterID")
    @set:PropertyName("requesterID")
    var requesterId: String = "",
    @PropertyName("requesterName")
    val requesterName: String = "",
    @PropertyName("requesterPhone")
    val requesterPhone: String = "",
    @get:PropertyName("workerID")
    @set:PropertyName("workerID")
    var workerId: String? = null,
    @PropertyName("workerName")
    val workerName: String? = null,
    @PropertyName("workerPhone")
    val workerPhone: String? = null,
    @PropertyName("requestDate")
    val requestDate: Timestamp = Timestamp.now(),
    @PropertyName("completionDate")
    val completionDate: Timestamp? = null,
    @PropertyName("search")
    val search: List<String> = emptyList(),
    @PropertyName("completionDateStr")
    val completionDateStr: String = ""
)