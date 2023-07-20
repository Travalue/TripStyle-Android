package com.tripstyle.tripstyle.data.model.dto

import okhttp3.MultipartBody
import okhttp3.RequestBody

// 일단 명목상으로 만들어 두었는데 지워야할듯
data class CategoryAddRequest(
    val thumbnail: MultipartBody.Part,
    val subject: RequestBody,
    val title: RequestBody,
    val region: RequestBody,
    val userId: RequestBody
    )

// 얘도 BaseResponseModel로 대체 가능하지만 일단 이걸로 사용함
data class CategoryAddResponse(
    val code: Int,
    val message: String
    // data는 비어있어서
)
