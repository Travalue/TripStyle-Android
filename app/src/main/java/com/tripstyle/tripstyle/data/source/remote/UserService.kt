package com.tripstyle.tripstyle.data.source.remote

import com.tripstyle.tripstyle.BuildConfig
import com.tripstyle.tripstyle.data.model.dto.NicknameRequestModel
import com.tripstyle.tripstyle.data.model.dto.NicknameResponseModel
import com.tripstyle.tripstyle.data.model.dto.UserInfoModel
import com.tripstyle.tripstyle.data.model.dto.UserPageResponse
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @GET("/v1/user/check")
    fun checkNickname(@Query("nickname") nickname: String): Call<NicknameResponseModel>

    @Headers("Authorization: ${BuildConfig.TOKEN}")
    @PUT("/v1/user/nickname")
    fun postNickname(@Body nicknameRequestModel: NicknameRequestModel): Call<Void>

    @Headers("Authorization: ${BuildConfig.TOKEN}")
    @GET("/v1/user/{pageOwnerId}")
    fun getUserInfo(@Path("pageOwnerId") pageOwnerId:Int, @Query("pageOwnerUserId") pageOwnerUserId: Int, @Query("userId") userId: Int) : Call<UserPageResponse>
}