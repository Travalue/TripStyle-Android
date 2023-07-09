package com.tripstyle.tripstyle.presentation.ui.info.info

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentRegisterBinding
import com.tripstyle.tripstyle.MainActivity
import com.tripstyle.tripstyle.data.model.dto.NicknameRequestModel
import com.tripstyle.tripstyle.data.model.dto.NicknameResponseModel
import com.tripstyle.tripstyle.di.AppClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class RegisterFragment: BaseFragment<FragmentRegisterBinding>(R.layout.fragment_register) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).hideBottomNav(true)
        (activity as MainActivity).hideToolbar(true)
    }

    override fun initDataBinding() {
        super.initDataBinding()


        // 허용 문자 필터
//        binding.etNickname.filters = arrayOf(filterNickname)

        // 닉네임 입력 감지 리스너
        binding.etNickname.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val searchString = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // TODO("not implemented")
                // To change body of created functions use File | Settings | File Templates.
            }

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val nickName = binding.etNickname.text


                // 공백 미포함 특수문자 체크
                val pattern = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]")
                if (pattern.matcher(nickName).find()) {
                    // 특수문자 포함 시
                    binding.tvInfo4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_circle_red_24,0,0,0)
                    binding.tvInfo4.setTextColor(ContextCompat.getColor(context!!, R.color.red))
                    binding.etNickname.setBackgroundResource(R.drawable.edittext_rounded_corner_rectangle_red)

                    binding.btnCheck.setTextColor(ContextCompat.getColor(context!!, R.color.gray_959595))
                    binding.btnCheck.isEnabled=false
                }else{
                    // 특수문자 비포함 시
                    binding.tvInfo4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_circle_gray_24,0,0,0)
                    binding.tvInfo4.setTextColor(ContextCompat.getColor(context!!, R.color.gray_959595))
                    binding.etNickname.setBackgroundResource(R.drawable.edittext_rounded_corner_rectangle)

                    // 중복확인 버튼 활성화
                    if(nickName.isNotEmpty()){
                        binding.btnCheck.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                        binding.btnCheck.isEnabled=true

                    }else{
                        binding.btnCheck.setTextColor(ContextCompat.getColor(context!!, R.color.gray_959595))
                        binding.btnCheck.isEnabled=false
                    }

                }
            }
        })
    }

    @SuppressLint("ResourceAsColor")
    override fun initAfterBinding() {
        super.initAfterBinding()

        binding.btnBack.setOnClickListener {
            navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnRegister.setOnClickListener {
            val nicknameRequestModel = NicknameRequestModel(binding.etNickname.toString())
            val resultData: Call<Void> = AppClient.userService.postNickname("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE2ODYwMzc0MTksImV4cCI6MTcxNzU3MzQxOX0.g7B5CNPCoENBpYopVvoHY3GhP6sL8-xC4j3aFkmWOPs",nicknameRequestModel)
            resultData.enqueue(object : Callback<Void> {
                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    if (response.isSuccessful) {
                        navController.navigate(R.id.action_registerFragment_to_registerOkFragment)
                        Log.d("[postNickname]", "로그인성공_${response.code()}")
                    } else {
                        Log.d("[postNickname]", "실패코드_${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    t.printStackTrace()
                    Log.d("[postNickname]","통신 실패")
                }
            })
        }

        binding.btnCheck.setOnClickListener {
            val nickname = binding.etNickname.text.toString()
            val resultData: Call<NicknameResponseModel> = AppClient.userService.checkNickname(nickname)
            resultData.enqueue(object : Callback<NicknameResponseModel> {
                override fun onResponse(
                    call: Call<NicknameResponseModel>,
                    response: Response<NicknameResponseModel>
                ) {
                    if (response.isSuccessful) {
                        val result: NicknameResponseModel = response.body()!!
                        if(result.isDuplicate == false){
                            Log.d("[checkNickname]", "서버응답 : $result")
                            //회원가입 버튼 활성화
                            binding.btnRegister.isEnabled=true
                            binding.btnRegister.setTextColor(ContextCompat.getColor(context!!, R.color.white))

                            binding.tvIsDupliate.visibility = View.VISIBLE
                            binding.tvIsDupliate.text = "사용 가능한 별명입니다."
                            binding.tvIsDupliate.setTextColor(R.color.primaryColor)
                        }else{
                            Log.d("[checkNickname]", "서버응답 : $result")
                            binding.btnRegister.isEnabled=false
                            binding.btnRegister.setTextColor(ContextCompat.getColor(context!!, R.color.gray_959595))

                            binding.tvIsDupliate.visibility = View.VISIBLE
                            binding.tvIsDupliate.text = "사용 불가능한 별명입니다."
                            binding.tvIsDupliate.setTextColor(Color.RED)
                        }
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
    }
}