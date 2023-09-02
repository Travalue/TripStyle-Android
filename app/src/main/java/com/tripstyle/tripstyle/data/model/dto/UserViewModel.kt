package com.tripstyle.tripstyle.data.model.dto

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tripstyle.tripstyle.data.model.dto.MyTripModel
import com.tripstyle.tripstyle.data.model.dto.UserInfoModel
import com.tripstyle.tripstyle.data.model.dto.UserPageResponse
import com.tripstyle.tripstyle.di.AppClient
import com.tripstyle.tripstyle.presentation.ui.mypage.MyTravelListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    private var _userInfo = MutableLiveData<UserInfoModel>()
    private var _myTripList = MutableLiveData<ArrayList<MyTripModel>>()

    val userInfo : LiveData<UserInfoModel>
            get() = _userInfo

    val myTripList : LiveData<ArrayList<MyTripModel>>
        get() = _myTripList

    fun setUserInfo(userViewModel:UserInfoModel) {
            _userInfo = MutableLiveData(userViewModel)
    }

    fun getTripList() {
        val resultData: Call<MyTripModelResponse> = AppClient.userService.getMyTrip()
        resultData.enqueue(object : Callback<MyTripModelResponse> {
            override fun onResponse(
                call: Call<MyTripModelResponse>,
                response: Response<MyTripModelResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("[getMyTrip]", response.body().toString())
                    _myTripList.value = response.body()?.data // MutableLiveData의 value 속성을 업데이트

                } else {
                    Log.d("[getMyTrip]", "실패코드_${response.code()}")
                }
            }

            override fun onFailure(call: Call<MyTripModelResponse>, t: Throwable) {
                t.printStackTrace()
                Log.d("[getMyTrip]","통신 실패")
            }
        })
    }


    fun getProfileImage(): String {
        return userInfo.value!!.profileImage
    }

    fun getUserNickname(): String {
        return userInfo.value!!.nickname
    }

    fun setMyTripList(mytripList : ArrayList<MyTripModel>){
        _myTripList = MutableLiveData(mytripList)
    }


    fun getDescription() : String{
        return userInfo.value!!.description
    }



}