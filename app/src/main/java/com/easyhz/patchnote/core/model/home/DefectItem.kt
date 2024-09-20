package com.easyhz.patchnote.core.model.home

/**
 * DefectItem
 *
 * @param id 하자 도큐먼트 아이디
 * @param site 현장
 * @param building 동
 * @param unit 호수
 * @param space 공간
 * @param part 부위
 * @param workType 공종
 * @param isDone 완료 처리 여부
 * @param date 등록일
 * @param thumbnailUrl 썸네일 URL
 */
data class DefectItem(
    val id: String,
    val site: String,
    val building: String,
    val unit: String,
    val space: String,
    val part: String,
    val workType: String,
    val isDone: Boolean,
    val date: String,
    val thumbnailUrl: String,
)
