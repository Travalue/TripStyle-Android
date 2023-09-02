package com.tripstyle.tripstyle.data.model.dto

import android.net.Uri
import java.net.URL

data class UserPageResponse(
    val code: Int,
    val message: String,
    val data: UserInfoModel
)

data class UserInfoModel(
    val isMe : Boolean,
    val nickname: String,
    val profileImage : String,
    val description: String,
    val travelCount: Int,
    val travelList : ArrayList<MyTripModel>,
    val sharedTravelCount: Int,
    val sharedTravel : ArrayList<SharedTravelData>,
    val travelLikeCount: Int
)

data class SharedTravelData(
    val categoryId : Int,
    val title: String,
    val subject: String,
    val thumbnail:String,
    val count: Int
)