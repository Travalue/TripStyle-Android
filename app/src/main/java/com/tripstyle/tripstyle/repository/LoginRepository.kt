package com.tripstyle.tripstyle.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tripstyle.tripstyle.api.RetrofitClient
import com.tripstyle.tripstyle.model.LoginRequestModel
import com.tripstyle.tripstyle.model.LoginResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body

class LoginRepository {

    fun loginRequest(@Body loginRequestData: LoginRequestModel): MutableLiveData<LoginResponseModel> {
        val resultData: Call<LoginResponseModel> = RetrofitClient.loginService.loginRequest(loginRequestData)
        val mutableData = MutableLiveData<LoginResponseModel>()

        resultData.enqueue(object : Callback<LoginResponseModel> {
            override fun onResponse(
                call: Call<LoginResponseModel>,
                response: Response<LoginResponseModel>
            ) {
                if (response.isSuccessful) {
                    val result: LoginResponseModel = response.body()!!
                    mutableData.value = result
                    Log.d("[loignRequest]", "서버응답 : $result")
                } else {
                    Log.d("[loignRequest]", "실패코드_${response.code()}")
                }
            }

            override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                t.printStackTrace()
                Log.d("[loignRequest]","통신 실패")
            }
        })

        return mutableData
    }
}