package com.easyhz.patchnote.core.model.category

sealed class Category(
    val type: CategoryType,
    open val values: List<String>
) {
    data class Site(override val values: List<String>) : Category(CategoryType.SITE, values)
    data class Space(override val values: List<String>) : Category(CategoryType.SPACE, values)
    data class Part(override val values: List<String>) : Category(CategoryType.PART, values)
    data class WorkType(override val values: List<String>) : Category(CategoryType.WORK_TYPE, values)

    fun updateValues(newValues: List<String>): Category {
        return when (this) {
            is Site -> this.copy(values = newValues)
            is Space -> this.copy(values = newValues)
            is Part -> this.copy(values = newValues)
            is WorkType -> this.copy(values = newValues)
        }
    }
}