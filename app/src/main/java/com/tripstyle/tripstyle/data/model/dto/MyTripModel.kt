package com.tripstyle.tripstyle.data.model.dto

data class MyTripModelResponse(
    val code: Int,
    val message: String,
    val data: ArrayList<MyTripModel>,
)
data class MyTripModel(
    val id: Int,
    val emoji: String,
    val travelTitle: String
)

data class MyTripDeleteResponse(
    val code: Int,
    val message: String,
    val data: String
)