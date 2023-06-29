package com.tripstyle.tripstyle.model

data class TrailerResponseModel(
    val code: Int,
    val message: String,
    val data: List<TrailerItem>
)

data class TrailerItem(
    val trailerId: Int,
    val subject: String,
    val title: String,
    val subTitle: String,
    val thumbnail: String
)