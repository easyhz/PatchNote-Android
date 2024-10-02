package com.easyhz.patchnote.core.common.util

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat

object DateFormatUtil {
    private const val PATTERN = "yyyy.MM.dd"

    fun formatTimestampToDateString(timestamp: Timestamp): String {
        return SimpleDateFormat(PATTERN, java.util.Locale.getDefault()).format(timestamp.toDate())
    }

    fun formatTimestampToDateNullString(timestamp: Timestamp?): String? {
        if (timestamp == null) return null
        return SimpleDateFormat(PATTERN, java.util.Locale.getDefault()).format(timestamp.toDate())
    }


}