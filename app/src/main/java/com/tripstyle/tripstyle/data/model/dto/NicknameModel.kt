package com.tripstyle.tripstyle.data.model.dto

data class NicknameRequestModel(
    val nickname:String
)

data class NicknameResponseModel(
    val isDuplicate : Boolean
)