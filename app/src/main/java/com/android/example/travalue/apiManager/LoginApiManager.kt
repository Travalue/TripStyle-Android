package com.android.example.travalue.apiManager

import android.content.Context
import android.util.Log
import com.android.example.travalue.api.LoginService
import com.android.example.travalue.model.LoginRequestModel
import com.android.example.travalue.model.LoginResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body

class LoginApiManager {
    private var retrofit: Retrofit? = null
    private var retrofitService: LoginService? = null

    companion object {  // DCL 적용한 싱글톤 구현
        var instance: LoginApiManager? = null
        fun getInstance(context: Context?): LoginApiManager? {
            if (instance == null) {
                @Synchronized
                if (instance == null)
                    instance = LoginApiManager()
            }
            return instance
        }
    }

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("http://13.125.137.16:8080/v1/auth/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitService = retrofit?.create(LoginService::class.java)
    }

    fun loginRequest(@Body loginRequestData: LoginRequestModel){
        val resultData: Call<LoginResponseModel>? = retrofitService?.loginRequest(loginRequestData)
        resultData?.enqueue(object : Callback<LoginResponseModel> {
            override fun onResponse(
                call: Call<LoginResponseModel>,
                response: Response<LoginResponseModel>
            ) {
                if (response.isSuccessful) {
                    val result: LoginResponseModel = response.body()!!
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
    }


}