package com.tripstyle.tripstyle.presentation.ui.info.info

import android.util.Log
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentLoginBinding
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient

import com.kakao.sdk.common.util.Utility
import com.tripstyle.tripstyle.MainActivity
import com.tripstyle.tripstyle.data.model.dto.LoginRequestModel
import com.tripstyle.tripstyle.data.model.dto.LoginResponseModel
import com.tripstyle.tripstyle.di.AppClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body


class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    // 이메일 로그인 콜백
    private val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("mihye", "로그인 실패 $error")
        } else if (token != null) {
            Log.i("mihye", "로그인 성공 ${token.accessToken}")
        }
    }

    private fun login(@Body loginRequestData: LoginRequestModel){
        val resultData: Call<LoginResponseModel> = AppClient.loginService.loginRequest(loginRequestData)
        resultData.enqueue(object : Callback<LoginResponseModel> {
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



    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("none")


        context?.let { KakaoSdk.init(it, getString(R.string.native_key)) }
        val keyHash = context?.let { Utility.getKeyHash(it) }
        Log.i("mihye","keyHash ${keyHash}")
    }

    override fun initDataBinding() {
        super.initDataBinding()


    }



    override fun initAfterBinding() {
        super.initAfterBinding()

        //kakao 로그인
        binding.btnLoginKakao.setOnClickListener {
//            navController.navigate(R.id.action_loginFragment_to_registerFragment)

            // 토큰 확인
            if (AuthApiClient.instance.hasToken()) {
                Log.i("mihye","token ok")
                UserApiClient.instance.accessTokenInfo { _, error ->
                    if (error != null) {
                        if (error is KakaoSdkError && error.isInvalidTokenError()) {
                            //로그인 필요
                            // 카카오톡 설치 확인
                            if (context?.let { it1 -> UserApiClient.instance.isKakaoTalkLoginAvailable(it1) } == true) {
                                // 카카오톡 로그인
                                context?.let { it1 ->
                                    UserApiClient.instance.loginWithKakaoTalk(it1) { token, error ->
                                        // 로그인 실패 부분
                                        if (error != null) {
                                            Log.e("mihye", "로그인 실패 $error")
                                            // 사용자가 취소
                                            if (error is ClientError && error.reason == ClientErrorCause.Cancelled ) {
                                                return@loginWithKakaoTalk
                                            }
                                            // 다른 오류
                                            else {
                                                context?.let { it1 -> UserApiClient.instance.loginWithKakaoAccount(it1, callback = mCallback) } // 카카오 이메일 로그인
                                            }
                                        }
                                        // 로그인 성공 부분
                                        else if (token != null) {
                                            Log.i("mihye", "로그인 성공 ${token.accessToken}")
                                        }
                                    }
                                }
                            } else {
                                context?.let { it1 -> UserApiClient.instance.loginWithKakaoAccount(it1, callback = mCallback) } // 카카오 이메일 로그인
                            }
                        }
                        else {
                            //기타 에러
                        }
                    }
                    else {
                        //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                        UserApiClient.instance.me { user, error ->
                            if (error != null) {
                                Log.e("mihye", "사용자 정보 요청 실패 $error")
                            } else if (user != null) {
                                Log.i("mihye", "사용자 정보 요청 성공 : $user")
                                UserApiClient.instance.me { user, error ->
                                    if (error != null) {
                                        Log.e("mihye", "사용자 정보 요청 실패 $error")
                                    } else if (user != null) {
                                        Log.i("mihye", "사용자 정보 요청 성공 : $user")

                                        // 서버 통신

                                    }
                                }

                            }
                        }
                    }
                }
            }
            else {
                Log.i("mihye"," token no")
                //로그인 필요
                // 카카오톡 설치 확인
                if (context?.let { it1 -> UserApiClient.instance.isKakaoTalkLoginAvailable(it1) } == true) {
                    // 카카오톡 로그인
                    context?.let { it1 ->
                        UserApiClient.instance.loginWithKakaoTalk(it1) { token, error ->
                            // 로그인 실패 부분
                            if (error != null) {
                                Log.e("mihye", "로그인 실패 $error")
                                // 사용자가 취소
                                if (error is ClientError && error.reason == ClientErrorCause.Cancelled ) {
                                    return@loginWithKakaoTalk
                                }
                                // 다른 오류
                                else {
                                    context?.let { it1 -> UserApiClient.instance.loginWithKakaoAccount(it1, callback = mCallback) } // 카카오 이메일 로그인
                                }
                            }
                            // 로그인 성공 부분
                            else if (token != null) {
                                Log.i("mihye", "로그인 성공 ${token.accessToken}")
                                UserApiClient.instance.me { user, error ->
                                    if (error != null) {
                                        Log.e("mihye", "사용자 정보 요청 실패 $error")
                                    } else if (user != null) {
                                        Log.i("mihye", "사용자 정보 요청 성공 : $user")

                                        // 서버 통신


                                    }
                                }

                            }
                        }
                    }
                } else {
                    context?.let { it1 -> UserApiClient.instance.loginWithKakaoAccount(it1, callback = mCallback) } // 카카오 이메일 로그인
                }
            }


        }
    }
}