package com.tripstyle.tripstyle.network

import com.tripstyle.tripstyle.model.NicknameRequestModel
import com.tripstyle.tripstyle.model.NicknameResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @GET("/v1/user/check")
    fun checkNickname(@Query("nickname") nickname: String): Call<NicknameResponseModel>

    @POST("/v1/user/nickname")
    fun postNickname(@Body nicknameRequestModel: NicknameRequestModel, @Body userId: Long )

}