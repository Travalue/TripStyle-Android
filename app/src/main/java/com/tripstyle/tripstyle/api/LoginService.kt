package com.tripstyle.tripstyle.api

import com.tripstyle.tripstyle.model.LoginRequestModel
import com.tripstyle.tripstyle.model.LoginResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {


    @POST("login")
    fun loginRequest(@Body loginRequestData: LoginRequestModel): Call<LoginResponseModel>

}