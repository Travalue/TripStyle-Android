package com.tripstyle.tripstyle.network

import com.tripstyle.tripstyle.model.NicknameRequestModel
import com.tripstyle.tripstyle.model.NicknameResponseModel
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @GET("/v1/user/check")
    fun checkNickname(@Query("nickname") nickname: String): Call<NicknameResponseModel>

    @PUT("/v1/user/nickname")
    fun postNickname(@Header("Authorization") authorization: String, @Body nicknameRequestModel: NicknameRequestModel): Call<Void>

}