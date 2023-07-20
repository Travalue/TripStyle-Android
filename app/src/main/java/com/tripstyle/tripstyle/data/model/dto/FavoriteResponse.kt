package com.tripstyle.tripstyle.data.model.dto

data class FavoriteResponse(
    val code : Int,
    val message : String,
    val data : Favorite,
)
data class Favorite(
    val likeCount:Int
)
