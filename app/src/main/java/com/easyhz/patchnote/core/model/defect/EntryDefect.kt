package com.easyhz.patchnote.core.model.defect

import android.net.Uri
import com.easyhz.patchnote.core.model.category.CategoryType
import com.easyhz.patchnote.core.model.image.ImageSize
import java.time.LocalDateTime

data class EntryDefect(
    val id: String,
    val site: String,
    val building: String,
    val unit: String,
    val space: String,
    val part: String,
    val workType: String,
    val thumbnailUrl: String,
    val beforeDescription: String,
    val beforeImageUrls: List<String>,
    val beforeImageSizes: List<ImageSize>,
    val requesterId: String,
    val requesterName: String,
    val requesterPhone: String,
    val teamId: String,
    val creationTime: LocalDateTime? = null,
) {
    fun exportSearch(): List<String> {
        val searchMap = CategoryType.entries.associate {
            it.alias to when(it) {
                CategoryType.SITE -> site
                CategoryType.BUILDING -> building
                CategoryType.UNIT -> unit
                CategoryType.SPACE -> space
                CategoryType.PART -> part
                CategoryType.WORK_TYPE -> workType
            }
        }.toMutableMap()

        searchMap["requesterName"] = requesterName

        val combinations = mutableListOf<String>()
        val keys = searchMap.keys.toList()
        val totalCombinations = 1 shl keys.size

        (1 until totalCombinations).forEach { i ->
            val currentCombination = keys.filterIndexed { index, _ -> (i shr index) and 1 == 1 }
                .joinToString("||") { key -> "$key=${searchMap[key]}" }
            combinations.add(currentCombination)
        }

        return combinations
    }
}

data class EntryDefectParam(
    val id: String,
    val site: String,
    val building: String,
    val unit: String,
    val space: String,
    val part: String,
    val workType: String,
    val beforeDescription: String,
    val beforeImageUris: List<Uri>,
    val creationTime: LocalDateTime? = null,
) {
    companion object {
        fun EntryDefectParam.toEntryDefect(
            teamId: String,
            requesterId: String,
            requesterName: String,
            requesterPhone: String,
            thumbnailUrl: String,
            beforeImageUrls: List<String>,
            beforeImageSizes: List<ImageSize>,
        ): EntryDefect {
            return EntryDefect(
                id = id,
                site = site,
                building = building,
                unit = unit,
                space = space,
                part = part,
                workType = workType,
                thumbnailUrl = thumbnailUrl,
                beforeDescription = beforeDescription,
                beforeImageUrls = beforeImageUrls,
                beforeImageSizes = beforeImageSizes,
                requesterId = requesterId,
                requesterName = requesterName,
                requesterPhone = requesterPhone,
                teamId = teamId,
                creationTime = creationTime
            )
        }
    }
}