package com.android.example.travalue.model

data class LoginRequestModel(
    val uniqueId: Long?,
    val email:String?,
    val profileImage:String?,
    val socialType:String
)