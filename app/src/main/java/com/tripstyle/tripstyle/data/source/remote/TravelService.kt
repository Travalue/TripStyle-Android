package com.tripstyle.tripstyle.data.source.remote

import com.tripstyle.tripstyle.BuildConfig
import com.tripstyle.tripstyle.data.model.dto.BaseResponseModel
import com.tripstyle.tripstyle.data.model.dto.ShareTravellerResponse
import com.tripstyle.tripstyle.data.model.dto.TrailerResponseModel
import com.tripstyle.tripstyle.data.model.dto.TravelDetailResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface TravelService {

    //Trailer 전체조회
    @GET("/post/trailer")
    fun getTrailerList() : Call<TrailerResponseModel>

    // Trailer, Travler 상세조회
    @Headers("Authorization: ${BuildConfig.TOKEN}")
    @GET("/post/{id}")
    fun getTravelPost(@Path("id")id:Int) : Call<TravelDetailResponse>

    //게시물 좋아요
    @Headers("Authorization: ${BuildConfig.TOKEN}")
    @POST("/post/{postId}/like")
    fun getLikePost(@Path("postId")id:Int) : Call<BaseResponseModel>

    //게시물 좋아요
    @Headers("Authorization: ${BuildConfig.TOKEN}")
    @DELETE("/post/{postId}/unlike")
    fun getUnlikePost(@Path("postId")id:Int) : Call<BaseResponseModel>

    // 공유중인 traveller 전체조회
    @Headers("Authorization: ${BuildConfig.TOKEN}")
    @GET("/post/traveller/share/{userId}")
    fun getTravellerShare(@Path("postId")id:Int) : Call<ShareTravellerResponse>


}