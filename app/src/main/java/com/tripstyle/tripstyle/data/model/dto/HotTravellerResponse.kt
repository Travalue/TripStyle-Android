package com.tripstyle.tripstyle.data.model.dto

data class HotTravellerResponse(
    val code: Int,
    val message: String,
    val data: List<HotTravellerItem>
)

data class HotTravellerItem(
    val description: String,
    val nickname: String,
    val profileImage: String,
    val subTitle: String,
    val subject: String,
    val thumbnail: String,
    val title: String,
    val travellerId: Int
)