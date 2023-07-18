package com.tripstyle.tripstyle.presentation.ui.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tripstyle.tripstyle.data.model.dto.UserInfoModel
import com.tripstyle.tripstyle.data.model.dto.UserPageResponse
import com.tripstyle.tripstyle.di.AppClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    private var _userInfo = MutableLiveData<UserInfoModel>()

    val userInfo : LiveData<UserInfoModel>
            get() = _userInfo

    fun setUserInfo(userViewModel:UserInfoModel) {
            _userInfo = MutableLiveData(userViewModel)
    }

    fun getProfileImage(): String {
        return userInfo.value!!.profileImage
    }

    fun getUserNickname(): String {
        return userInfo.value!!.nickname
    }

    fun getTravelList():ArrayList<ArrayList<String>>{
        return userInfo.value!!.travelList
    }

    fun getDescription() : String{
        return userInfo.value!!.description
    }



}