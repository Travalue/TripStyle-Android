package com.tripstyle.tripstyle.di

import com.google.gson.GsonBuilder
import com.tripstyle.tripstyle.BuildConfig
import com.tripstyle.tripstyle.data.source.remote.LoginService
import com.tripstyle.tripstyle.data.source.remote.TravelService
import com.tripstyle.tripstyle.data.source.remote.UserService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object AppClient {
    const val LOCATION_API = "https://openapi.naver.com"

    var gson = GsonBuilder().setLenient().create()

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        // 다른 인터셉터 또는 설정들을 추가할 수 있습니다.
        .build()

    val locationRetrofit = Retrofit.Builder()
        .baseUrl(LOCATION_API)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("${BuildConfig.SERVER_API}")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val loginService: LoginService = retrofit.create(LoginService::class.java)
    val userService: UserService = retrofit.create(UserService::class.java)
}