package com.tripstyle.tripstyle.network

import com.tripstyle.tripstyle.model.TrailerResponseModel
import retrofit2.Call
import retrofit2.http.GET

interface TravelService {

    //Trailer 전체조회
    @GET("/post/trailer")
    fun getTrailerList() : Call<TrailerResponseModel>
}