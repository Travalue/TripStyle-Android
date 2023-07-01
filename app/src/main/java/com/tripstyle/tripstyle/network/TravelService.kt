package com.tripstyle.tripstyle.network

import com.tripstyle.tripstyle.model.TrailerResponseModel
import com.tripstyle.tripstyle.model.TravelDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TravelService {

    //Trailer 전체조회
    @GET("/post/trailer")
    fun getTrailerList() : Call<TrailerResponseModel>

    // Trailer, Travler 상세조회
    @GET("/post/{id}")
    fun getTravelPost(@Path("id")id:Int) : Call<TravelDetailResponse>

}