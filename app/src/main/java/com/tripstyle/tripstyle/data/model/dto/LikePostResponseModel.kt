package com.tripstyle.tripstyle.data.model.dto

data class LikePostResponseModel (
    val code:Int,
    val data:ArrayList<LikePostModel>,
    val message: String
    )

data class LikePostModel(
    val subTitle:String,
    val subject:String,
    val thumbnail:String,
    val title: String,
    val trailerId: Int
)