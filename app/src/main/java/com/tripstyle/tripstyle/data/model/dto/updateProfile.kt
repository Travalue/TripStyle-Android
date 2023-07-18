package com.tripstyle.tripstyle.data.model.dto

data class UpdateUserProfileRequestModel(
    var nickname: String,
    var description: String
)

data class UpdateUserProfileResponseModel(
    val code: Int,
    val message: String,
    val data:NicknameResponseModel
)