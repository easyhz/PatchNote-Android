package com.easyhz.patchnote.core.supabase.service.user

import com.easyhz.patchnote.core.supabase.constant.Table
import com.easyhz.patchnote.core.supabase.model.user.UserDto
import com.easyhz.patchnote.core.supabase.model.user.UserWithTeamDto
import com.easyhz.patchnote.core.supabase.model.user.UserWithUserTeamMapDto
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class UserServiceImpl @Inject constructor(
    private val postgrest: Postgrest
) : UserService {
    override suspend fun insertUser(userDto: UserDto) {
        postgrest.from(Table.Users.tableName).insert(userDto)
    }

    override suspend fun fetchUser(userId: String): UserDto? {
        return postgrest.from(Table.Users.tableName).select {
            filter {
                eq(Table.Users.ID, userId)
            }
        }.decodeSingleOrNull()
    }

    override suspend fun fetchUserWithTeams(userId: String): UserWithTeamDto {
        val userWithMap = postgrest.from(Table.Users.tableName)
            .select(
                Columns.raw(
                    """
                        ${Table.Users.ID}, ${Table.Users.NAME}, ${Table.Users.PHONE}, ${Table.Users.CREATED_AT},
                        ${Table.UserTeamMap.tableName}(
                            ${Table.Teams.DTO_NAME} : ${Table.Teams.tableName}(
                                ${Table.Teams.ID}, ${Table.Teams.NAME}, ${Table.Teams.INVITE_CODE}, ${Table.Teams.CREATED_AT}
                            )
                        )
                    """.trimIndent()
                ),
                request = {
                    filter {
                        eq(Table.Users.ID, userId)
                    }
                }
            )
            .decodeSingle<UserWithUserTeamMapDto>()

        val userWithTeam = UserWithTeamDto(
            id = userWithMap.id,
            name = userWithMap.name,
            phone = userWithMap.phone,
            createdAt = userWithMap.createdAt,
            teamList = userWithMap.userTeamMap.map { it.team }
        )

        return userWithTeam
    }
}