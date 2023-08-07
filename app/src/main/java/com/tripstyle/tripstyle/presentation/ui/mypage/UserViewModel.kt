package com.tripstyle.tripstyle.presentation.ui.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tripstyle.tripstyle.data.model.dto.MyTripModel
import com.tripstyle.tripstyle.data.model.dto.UserInfoModel
import com.tripstyle.tripstyle.data.model.dto.UserPageResponse
import com.tripstyle.tripstyle.di.AppClient
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