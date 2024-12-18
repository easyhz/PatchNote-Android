package com.easyhz.patchnote.core.model.defect

import android.content.Context

data class ExportDefect(
    val id: String,
    val site: String,
    val building: String,
    val unit: String,
    val space: String,
    val part: String,
    val workType: String,
    val progress: DefectProgress,
    val requesterName: String,
    val requesterPhone: String,
    val requestDate: String,
    val beforeDescription: String,
    val workerName: String,
    val workerPhone: String,
    val completionDate: String,
    val afterDescription: String,
    val thumbnail: String
) {
    fun toPair(context: Context): List<Pair<String, String>> = listOf(
        "id" to id,
        "site" to site,
        "building" to building,
        "unit" to unit,
        "space" to space,
        "part" to part,
        "workType" to workType,
        "progress" to context.getString(progress.progressStringId),
        "requesterName" to requesterName,
        "requesterPhone" to requesterPhone,
        "requestDate" to requestDate,
        "beforeDescription" to beforeDescription,
        "workerName" to workerName,
        "workerPhone" to workerPhone,
        "completionDate" to completionDate,
        "afterDescription" to afterDescription,
        "thumbnail" to thumbnail
    )
}