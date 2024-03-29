package com.tripstyle.tripstyle.data.source.remote

import com.tripstyle.tripstyle.BuildConfig
import com.tripstyle.tripstyle.data.model.dto.SearchResult
import retrofit2.Call
import retrofit2.http.GET
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
        @Query("query") locationName:String,
        @Query("display") count:String ="5"
    ) : Call<SearchResult>

}