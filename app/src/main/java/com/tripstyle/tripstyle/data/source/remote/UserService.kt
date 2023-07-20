package com.tripstyle.tripstyle.data.source.remote

import com.tripstyle.tripstyle.BuildConfig
import com.tripstyle.tripstyle.data.model.dto.*
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    // 닉네임 중복 확인
    @GET("/user/check")
    fun checkNickname(@Query("nickname") nickname: String): Call<NicknameResponseModel>

    // 닉네임 등록
    @Headers("Authorization: ${BuildConfig.TOKEN}")
    @PUT("/user/nickname")
    fun postNickname(@Body nicknameRequestModel: NicknameRequestModel): Call<Void>

    // 유저 페이지 조회
    @Headers("Authorization: ${BuildConfig.TOKEN}")
    @GET("/user/{pageOwnerId}")
    fun getUserInfo(@Path("pageOwnerId") pageOwnerId:Int, @Query("pageOwnerUserId") pageOwnerUserId: Int, @Query("userId") userId: Int) : Call<UserPageResponse>

    // 마이페이지 수정
    @Headers("Authorization: ${BuildConfig.TOKEN}","Content-Type: multipart/form-data")
    @PATCH("/user")
    fun updateMypage(@Body updateProfileRequest: UpdateUserProfileRequestModel) :Call<UpdateUserProfileResponseModel>
}