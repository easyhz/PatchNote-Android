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
 * @property completionDate 처리 완료 날짜
 * @property requesterId 접수자 id
 * @property requesterDate 접수일
 * @property requesterName 접수자 이름
 * @property requesterPhone 접수자 전화번호
 * @property workerId 처리자 id
 * @property workerName 처리자 이름
 * @property workerPhone 처리자 전화번호
 */
data class DefectData(
    @PropertyName("id")
    val id: String = "",
    @PropertyName("requestDate")
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
    val beforeImageSizes: List<ImageSize> = emptyList(),
    @get:PropertyName("beforeImageURLs")
    @set:PropertyName("beforeImageURLs")
    var beforeImageUrls: List<String> = emptyList(),
    @PropertyName("afterDescription")
    val afterDescription: String = "",
    @PropertyName("afterImageSizes")
    val afterImageSizes: List<ImageSize> = emptyList(),
    @get:PropertyName("afterImageURLs")
    @set:PropertyName("afterImageURLs")
    var afterImageUrls: List<String> = emptyList(),
    @PropertyName("completionDate")
    val completionDate: Timestamp? = null,
    @get:PropertyName("requesterID")
    @set:PropertyName("requesterID")
    var requesterId: String = "",
    @PropertyName("requesterDate")
    val requesterDate: Timestamp = Timestamp.now(),
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
)