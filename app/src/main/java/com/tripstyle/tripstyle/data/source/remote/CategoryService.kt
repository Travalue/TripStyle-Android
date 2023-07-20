package com.tripstyle.tripstyle.data.source.remote

import com.tripstyle.tripstyle.BuildConfig
import com.tripstyle.tripstyle.data.model.dto.BaseResponseModel
import com.tripstyle.tripstyle.data.model.dto.CategoryUpdateRequest
import retrofit2.Call
import retrofit2.http.*

interface CategoryService {

    // 카테고리 수정
    @Headers("Authorization: ${BuildConfig.TOKEN}")
    @PUT("/category/{categoryId}")
    fun updateCategory(
        @Path("categoryId")id:Int,
        @Body requestUpdateCategory: CategoryUpdateRequest) : Call<BaseResponseModel>

    // 카테고리 삭제
    @Headers("Authorization: ${BuildConfig.TOKEN}")
    @DELETE("/category/{categoryId}")
    fun deleteCategory(@Path("categoryId")id:Int) : Call<BaseResponseModel>

}