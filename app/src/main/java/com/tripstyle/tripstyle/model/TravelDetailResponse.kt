package com.tripstyle.tripstyle.model

data class TravelDetailResponse(
    val code: Int,
    val message: String,
    val data: Data
)

data class Data(
    val contents: List<Content>,
    val date: String,
    val schedules: List<Schedule>,
    val statistics: Statistics,
    val subTitle: String,
    val subject: String,
    val thumbnail: String,
    val title: String,
    val writer: Writer
)
data class Writer(
    val description: Any,
    val nickname: String,
    val profileImageURL: String,
    val userId: Int
)

data class Content(
    val content: String,
    val imageURL: String
)

data class Schedule(
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val name: String
)

data class Statistics(
    val likeCount: Int,
    val liked: Boolean,
    val viewCount: Int
)