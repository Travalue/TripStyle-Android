package com.tripstyle.tripstyle.data.model.dto

data class CategoryReadResponse(
    val code: Int,
    val message: String,
    val data: List<CategoryItem>
)

data class CategoryItem(
    val title: String,
    val travellerCount: Int
)
