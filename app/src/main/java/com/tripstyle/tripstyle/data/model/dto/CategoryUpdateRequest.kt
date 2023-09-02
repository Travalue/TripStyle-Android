package com.tripstyle.tripstyle.data.model.dto

import okhttp3.MultipartBody

data class CategoryUpdateRequest(
    val title:String,
    val subject:String,
    val region:String,
    val thumbnail: MultipartBody.Part
)
