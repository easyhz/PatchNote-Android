package com.easyhz.patchnote.core.common.util

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatUtil {
    private const val PATTERN = "yyyy.MM.dd"

    fun formatTimestampToDateString(timestamp: Timestamp): String {
        return SimpleDateFormat(PATTERN, Locale.getDefault()).format(timestamp.toDate())
    }

    fun formatTimestampToDateNullString(timestamp: Timestamp?): String? {
        if (timestamp == null) return null
        return SimpleDateFormat(PATTERN, Locale.getDefault()).format(timestamp.toDate())
    }

    fun convertMillisToDate(millis: Long): String {
        return SimpleDateFormat(PATTERN, Locale.getDefault()).format(Date(millis))
    }

}