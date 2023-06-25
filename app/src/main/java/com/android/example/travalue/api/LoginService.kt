package com.android.example.travalue.api

import com.android.example.travalue.model.LoginRequestModel
import com.android.example.travalue.model.LoginResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginService {

    @POST("/v1/auth/login")
    fun loginRequest(@Body loginRequestData: LoginRequestModel): Call<LoginResponseModel>

}