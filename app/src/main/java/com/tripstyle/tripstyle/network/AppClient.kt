package com.tripstyle.tripstyle.network

import com.google.gson.GsonBuilder
import com.tripstyle.tripstyle.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppClient {
    const val LOCATION_API = "https://openapi.naver.com"

    var gson = GsonBuilder().setLenient().create()

    val locationRetrofit = Retrofit.Builder()
        .baseUrl(LOCATION_API)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("${BuildConfig.SERVER_API}")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

}