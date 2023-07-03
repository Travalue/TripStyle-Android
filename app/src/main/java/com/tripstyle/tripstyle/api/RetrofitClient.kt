package com.tripstyle.tripstyle.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// singleton(메모리를 하나만 씀)
object RetrofitClient {
    // retrofit client 선언

    // BASE_URL을 private const 변수로 저장
    private const val BASE_URL = "http://13.125.137.16:8080/"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val loginService: LoginService = retrofit.create(LoginService::class.java)

}