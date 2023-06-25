package com.android.example.travalue.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MapClient {
    const val LOCATION_API = "https://openapi.naver.com"

    var gson = GsonBuilder().setLenient().create()

    val locationRetrofit = Retrofit.Builder()
        .baseUrl(LOCATION_API)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

}