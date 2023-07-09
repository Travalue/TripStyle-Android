package com.tripstyle.tripstyle.data.source.remote

import com.tripstyle.tripstyle.data.model.dto.NicknameRequestModel
import com.tripstyle.tripstyle.data.model.dto.NicknameResponseModel
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @GET("/v1/user/check")
    fun checkNickname(@Query("nickname") nickname: String): Call<NicknameResponseModel>

    @PUT("/v1/user/nickname")
    fun postNickname(@Header("Authorization") authorization: String, @Body nicknameRequestModel: NicknameRequestModel): Call<Void>

}