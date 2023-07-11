package com.tripstyle.tripstyle.data.model.dto

data class ShareTravellerResponse(
    val code: Int,
    val message: String,
    val data: List<ShareTraveller>
)
data class ShareTraveller(
    val subTitle: String,
    val subject: String,
    val thumbnail: String,
    val title: String,
    val travelId: Int
)