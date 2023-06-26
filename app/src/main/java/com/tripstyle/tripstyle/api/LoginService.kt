package com.tripstyle.tripstyle.api

import com.android.example.travalue.model.LoginRequestModel
import com.android.example.travalue.model.LoginResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {


    @POST("login")
    fun loginRequest(@Body loginRequestData: LoginRequestModel): Call<LoginResponseModel>

}