package com.android.example.travalue.network

import com.android.example.travalue.BuildConfig
import com.android.example.travalue.network.res.MapResult
import com.android.example.travalue.network.res.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

import retrofit2.http.Query

interface MapService {

    // openapi 지도 검색
    @Headers(
        "X-Naver-Client-Id: ${BuildConfig.LOCATION_CLIENT_ID}",
        "X-Naver-Client-Secret: ${BuildConfig.LOCATION_CLIENT_SECRET}"
    )
    @GET("/v1/search/local")
    fun getMapSerachResult(
        @Query("query") locationName:String) : Call<SearchResult>

    // 지도 좌표 반환
    @GET("/map-geocode/v2/geocode")
    fun getLocationInfo(
        @Header("X-NCP-APIGW-API-KEY-ID") id: String?,
        @Header("X-NCP-APIGW-API-KEY") secretKey: String?,
        @Query("query") locationName:String) : Call<MapResult>
}