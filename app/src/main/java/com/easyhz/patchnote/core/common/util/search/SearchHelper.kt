package com.easyhz.patchnote.core.common.util.search

/**
 * 검색 필터
 */
object SearchHelper {
    /**
     * 검색어를 포함하는 아이템들을 반환
     *
     * *주석으로 된 부분은 초성 검색을 위한 코드 (나중에 추가 예정)
     *
     * @param query 검색어
     * @param items 검색 대상 아이템들
     * @return 검색어를 포함하는 아이템들
     */
    fun search(query: String, items: List<String>): List<String> {
        if (items.isEmpty()) return emptyList()
        val queryJamo = query.flatMap { char ->
            decomposeKorean(char).toList().filterNotNull()
        }.joinToString("")
//        val queryInitials = query.map { char ->
//            decomposeKorean(char).first ?: char
//        }.joinToString("")

        return items.filter { item ->
            val itemJamo = item.flatMap { char ->
                decomposeKorean(char).toList().filterNotNull()
            }.joinToString("")

//            val itemInitials = item.map { char ->
//                decomposeKorean(char).first ?: char
//            }.joinToString("")
            itemJamo.contains(queryJamo, ignoreCase = true)
//                    || itemInitials.contains(queryInitials, ignoreCase = true)
        }
    }
    private fun decomposeKorean(char: Char): Triple<Char?, Char?, Char?> {
        if (char !in '가'..'힣') return Triple(char, null, null) // 한글이 아닌 경우 그대로 반환

        val unicodeValue = char.code - 0xAC00
        val initial = unicodeValue / (21 * 28)
        val vowel = (unicodeValue % (21 * 28)) / 28
        val final = unicodeValue % 28

        val initials = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ"
        val vowels = "ㅏㅐㅑㅒㅓㅔㅕㅖㅗㅘㅙㅚㅛㅜㅝㅞㅟㅠㅡㅢㅣ"
        val finals = " " + "ㄱㄲㄳㄴㄵㄶㄷㄹㄺㄻㄼㄽㄾㄿㅀㅁㅂㅄㅅㅆㅇㅈㅊㅋㅌㅍㅎ"

        return Triple(initials[initial], vowels[vowel], if (final != 0) finals[final] else null)
    }
}