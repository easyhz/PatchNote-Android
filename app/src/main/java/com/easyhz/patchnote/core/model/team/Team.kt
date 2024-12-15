package com.easyhz.patchnote.core.model.team

/**
 * team
 *
 * @property id team id
 * @property name team name
 * @property adminId team 만든 사람
 * @property inviteCode 초대 코드
 */
data class Team(
    val id: String,
    val name: String,
    val adminId: String,
    val inviteCode: String,
)