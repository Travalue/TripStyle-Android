package com.android.example.travalue.ui.info.info

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import androidx.browser.customtabs.CustomTabsClient.getPackageName
import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentLoginBinding
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import java.security.MessageDigest

import com.kakao.sdk.common.util.Utility
import java.security.NoSuchAlgorithmException


class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    // 이메일 로그인 콜백
    private val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("mihye", "로그인 실패 $error")
        } else if (token != null) {
            Log.i("mihye", "로그인 성공 ${token.accessToken}")
        }
    }



    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("none")


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
                                            print("토큰 : " + token.accessToken)
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
                                println("이메일 주소 : " + user.kakaoAccount?.email)
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
                                // 토큰 정보 보기
                                UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                                    if (error != null) {
                                        Log.e("mihye", "토큰 정보 보기 실패", error)
                                    }
                                    else if (tokenInfo != null) {
                                        Log.i("mihye", "토큰 정보 보기 성공" +
                                                "\n회원번호: ${tokenInfo.id}" +
                                                "\n만료시간: ${tokenInfo.expiresIn} 초")
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