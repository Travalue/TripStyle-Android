package com.tripstyle.tripstyle.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tripstyle.tripstyle.model.LoginRequestModel
import com.tripstyle.tripstyle.model.LoginResponseModel
import com.tripstyle.tripstyle.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel  : ViewModel(){

    private val loginRepo = LoginRepository()

    private var _loginResponseLiveData:MutableLiveData<LoginResponseModel> = MutableLiveData()
    val loginResponseLiveData:LiveData<LoginResponseModel>
        get() = _loginResponseLiveData


    fun loginRequest(loginRequestModel: LoginRequestModel) {
        viewModelScope.launch {
            _loginResponseLiveData = loginRepo.loginRequest(loginRequestModel)
        }
    }
}