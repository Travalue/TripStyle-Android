package com.tripstyle.tripstyle.data.model.dto

data class TravellerSearchResponse(
    val code: Int,
    val message: String,
    val data: List<TravellerSearchResult>
)

data class TravellerSearchResult(
    val thumbnail: String,
    val travellerId: Int
)
