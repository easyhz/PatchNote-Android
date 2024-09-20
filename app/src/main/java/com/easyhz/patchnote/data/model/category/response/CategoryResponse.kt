package com.easyhz.patchnote.data.model.category.response

import com.google.firebase.firestore.PropertyName

data class CategoryResponse(
    @PropertyName("site")
    val site: List<String> = emptyList(),
    @PropertyName("space")
    val space: List<String> = emptyList(),
    @PropertyName("part")
    val part: List<String> = emptyList(),
    @PropertyName("workType")
    val workType: List<String> = emptyList(),
)
