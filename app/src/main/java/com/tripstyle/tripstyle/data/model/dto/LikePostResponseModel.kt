package com.tripstyle.tripstyle.data.model.dto

data class LikePostResponseModel (
    val code:Int,
    val data:ArrayList<TrailerItem>,
    val message: String
    )