package com.easyhz.patchnote.data.mapper

import com.easyhz.patchnote.core.model.user.TeamJoinDate
import com.easyhz.patchnote.core.model.user.User
import com.easyhz.patchnote.data.mapper.sign.toRequest
import org.junit.Test

class UserMapperTest {
    @Test
    fun `팀 아이디 중복이 있는지 확인`() {
        val user = User.Empty.copy(
            teamIds = listOf("team1", "team2", "team1", "team2"),
            teamJoinDates = listOf(
                TeamJoinDate.create("team1"),
                TeamJoinDate.create("team2"),
                TeamJoinDate.create("team1"),
                TeamJoinDate.create("team2"),
            )
        )

        val result = user.toRequest()

        assert(result.teamIds.size == 2)
        assert(result.teamJoinDates.size == 2)
        assert(result.teamIds.last() == result.teamJoinDates.last().teamId)
    }
}