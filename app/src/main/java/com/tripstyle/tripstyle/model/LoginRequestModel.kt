package com.tripstyle.tripstyle.model

data class LoginRequestModel(
    val uniqueId: Long?,
    val email:String?,
    val profileImage:String?,
    val socialType:String
)