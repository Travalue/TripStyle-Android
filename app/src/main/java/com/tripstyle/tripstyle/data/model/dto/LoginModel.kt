package com.tripstyle.tripstyle.data.model.dto

data class LoginRequestModel(
    val uniqueId: Long?,
    val email:String?,
    val profileImage:String?,
    val socialType:String
)

data class LoginResponseModel(
    val token:String,
    val userId:Long
)