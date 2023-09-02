package com.tripstyle.tripstyle.data.source.remote


import com.tripstyle.tripstyle.BuildConfig
import com.tripstyle.tripstyle.data.model.dto.BaseResponseModel
import com.tripstyle.tripstyle.data.model.dto.LikePostResponseModel
import com.tripstyle.tripstyle.data.model.dto.FavoriteResponse
import com.tripstyle.tripstyle.data.model.dto.TrailerResponse
import com.tripstyle.tripstyle.data.model.dto.TravelDetailResponse
import com.tripstyle.tripstyle.data.model.dto.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface TravelService {

    //Trailer 전체조회
    @GET("/post/trailer")
    fun getTrailerList() : Call<TrailerResponse>

    // Trailer, Travler 상세조회
    @Headers("Authorization: ${BuildConfig.TOKEN}")
    @GET("/post/{id}")
    fun getTravelPost(@Path("id")id:Int) : Call<TravelDetailResponse>

    //게시물 좋아요
    @Headers("Authorization: ${BuildConfig.TOKEN}")
    @POST("/post/{postId}/like")
    fun getLikePost(@Path("postId")id:Int) : Call<FavoriteResponse>

    //게시물 좋아요
    @Headers("Authorization: ${BuildConfig.TOKEN}")
    @DELETE("/post/{postId}/unlike")
    fun getUnlikePost(@Path("postId")id:Int) : Call<FavoriteResponse>

    // 공유중인 traveller 전체조회
    @Headers("Authorization: ${BuildConfig.TOKEN}")
    @GET("/post/traveller/share/{userId}")
    fun getTravellerShare(@Path("userId")id:Int) : Call<TrailerResponse>


    //좋아요한 게시물 전체 조회
    @Headers("Authorization: ${BuildConfig.TOKEN}")
    @GET("/post/liked")
    fun getLikePostList() : Call<LikePostResponseModel>

    // 지금 핫한 트레블러 조회
    @GET("/post/traveller/hot")
    fun getHotTravellerList() : Call<HotTravellerResponse>

    // 카테고리 추가
    @Headers("Authorization: ${BuildConfig.TOKEN}")
    @Multipart
    @POST("/category")
    fun addCategory(
        @Part thumbnail: MultipartBody.Part,
        @Part("subject") subject: RequestBody,
        @Part("title") title: RequestBody,
        @Part("region") region: RequestBody,
        @Part("userId") userId: RequestBody
    ): Call<CategoryAddResponse>

    // 카테고리 조회
    @Headers("Authorization: ${BuildConfig.TOKEN}")
    @GET("/category")
    fun getCategoryList(@Query("userId") userId: Int): Call<CategoryReadResponse>


    // 트레블러 검색
    @GET("/post/traveller/search")
    fun searchTraveller(@Query("keyword") keyword: String): Call<TravellerSearchResponse>

}