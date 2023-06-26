package com.tripstyle.tripstyle.apiManager

import android.content.Context
import android.util.Log
import com.tripstyle.tripstyle.model.NicknameRequestModel
import com.tripstyle.tripstyle.model.NicknameResponseModel
import com.tripstyle.tripstyle.api.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Query

class UserApiManager {
    private var retrofit: Retrofit? = null
    private var retrofitService: UserService? = null

    companion object {  // DCL 적용한 싱글톤 구현
        var instance: UserApiManager? = null
        fun getInstance(context: Context?): UserApiManager? {
            if (instance == null) {
                @Synchronized
                if (instance == null)
                    instance = UserApiManager()
            }
            return instance
        }
    }

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("http://13.125.137.16:8080/v1/user/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitService = retrofit?.create(UserService::class.java)
    }

    // 닉네임 중복체크
    fun checkNickname(@Query("nickname") nickname: String){
        val resultData: Call<NicknameResponseModel>? = retrofitService?.checkNickname(nickname)
        resultData?.enqueue(object : Callback<NicknameResponseModel> {
            override fun onResponse(
                call: Call<NicknameResponseModel>,
                response: Response<NicknameResponseModel>
            ) {
                if (response.isSuccessful) {
                    val result: NicknameResponseModel = response.body()!!
                    Log.d("[checkNickname]", "서버응답 : $result")
                } else {
                    Log.d("[checkNickname]", "실패코드_${response.code()}")
                }
            }

            override fun onFailure(call: Call<NicknameResponseModel>, t: Throwable) {
                t.printStackTrace()
                Log.d("[checkNickname]","통신 실패")
            }
        })
    }

    // 닉네임 등록/수정
    fun postNickname(@Body nicknameRequestModel: NicknameRequestModel, @Body userId: Long ){

    }


}