package com.easyhz.patchnote.core.common.util

import com.google.firebase.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateFormatUtil {
    private const val PATTERN = "yyyy.MM.dd"
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN)


    fun formatTimestampToDateString(timestamp: Timestamp): String {
        val localDateTime = LocalDateTime.ofInstant(timestamp.toDate().toInstant(), ZoneId.systemDefault())
        return formatter.format(localDateTime)
    }

    fun formatTimestampToDateNullString(timestamp: Timestamp?): String? {
        if (timestamp == null) return null
        val localDateTime = LocalDateTime.ofInstant(timestamp.toDate().toInstant(), ZoneId.systemDefault())
        return formatter.format(localDateTime)
    }

    fun convertMillisToDate(millis: Long): String {
        val localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault())
        return formatter.format(localDateTime)
    }

    fun convertStringToMillis(string: String): Long {
        val dateFormatter = DateTimeFormatter.ofPattern(PATTERN, Locale.getDefault())
        val localDate = LocalDate.parse(string, dateFormatter)
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    /**
     * String 을 TimeStamp 로 변환
     *
     * @param plusDays 숫자만큼 날짜 더함 (ex. [plusDays] = 1 -> 2024-06-18 => 2024년 6월 19일 00:00시
     *
     * @return [Timestamp]
     */
    fun convertStringToTimeStamp(string: String, plusDays: Long = 0): Timestamp {
        val dateFormatter = DateTimeFormatter.ofPattern(PATTERN, Locale.getDefault())

        val localDate = LocalDate.parse(string, dateFormatter).plusDays(plusDays)
        val localDateTime = localDate.atStartOfDay()
        val instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
        return Timestamp(instant.epochSecond, instant.nano)
    }

}