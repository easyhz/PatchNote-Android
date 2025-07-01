package com.easyhz.patchnote.core.supabase.constant

sealed class Table(val tableName: String) {
    object Users : Table("PN_USERS") {
        const val ID = "id"
        const val NAME = "name"
        const val PHONE = "phone"
        const val CREATED_AT = "created_at"
    }

    object Teams : Table("TEAMS") {
        const val ID = "id"
        const val NAME = "name"
        const val INVITE_CODE = "invite_code"
        const val CREATED_AT = "created_at"

        const val DTO_NAME = "TEAM"
    }

    object UserTeamMap: Table("USER_TEAM_MAP") {
        const val ID = "id"
        const val USER_ID = "user_id"
        const val TEAM_ID = "team_id"
        const val CREATED_AT = "created_at"

        const val DTO_NAME = "USER_TEAM_MAP"
    }
}