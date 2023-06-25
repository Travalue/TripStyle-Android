package com.android.example.travalue.network.res

data class SearchResult(
    val display: Int,
    val lastBuildDate: String,
    val start: Int,
    val total: Int,
    val items: List<ItemData>,
)

data class ItemData(
    val address: String,
    val category: String,
    val description: String,
    val link: String,
    var mapx: String,
    var mapy: String,
    val roadAddress: String,
    val telephone: String,
    val title: String
)