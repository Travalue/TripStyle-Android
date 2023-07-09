package com.tripstyle.tripstyle.data.source.remote

import com.tripstyle.tripstyle.data.model.dto.LoginRequestModel
import com.tripstyle.tripstyle.data.model.dto.LoginResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("v1/auth/login")
    fun loginRequest(@Body loginRequestData: LoginRequestModel): Call<LoginResponseModel>

}