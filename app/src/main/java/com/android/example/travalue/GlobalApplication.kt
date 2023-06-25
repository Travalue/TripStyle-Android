package com.android.example.travalue

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        KakaoSdk.init(this, getString(R.string.native_key))
    }
}