package com.android.example.travalue.info

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.content.ContextCompat
import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentRegisterBinding
import java.util.regex.Pattern


class RegisterFragment: BaseFragment<FragmentRegisterBinding>(R.layout.fragment_register) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("register")
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

                    binding.btnCheck.setTextColor(ContextCompat.getColor(context!!, R.color.drakGray))
                    binding.btnCheck.isEnabled=false
                }else{
                    // 특수문자 비포함 시
                    binding.tvInfo4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_circle_gray_24,0,0,0)
                    binding.tvInfo4.setTextColor(ContextCompat.getColor(context!!, R.color.gray))
                    binding.etNickname.setBackgroundResource(R.drawable.edittext_rounded_corner_rectangle)

                    // 중복확인 버튼 활성화
                    if(nickName.isNotEmpty()){
                        binding.btnCheck.setTextColor(ContextCompat.getColor(context!!, R.color.black))
                        binding.btnCheck.isEnabled=true

                        //회원가입 버튼 활성화 (추후 중복검사 후 활성화로 변경해야함)
                        binding.btnRegister.isEnabled=true
                    }else{
                        binding.btnCheck.setTextColor(ContextCompat.getColor(context!!, R.color.drakGray))
                        binding.btnCheck.isEnabled=false
                    }

                }
            }
        })
    }

    override fun initAfterBinding() {
        super.initAfterBinding()


        binding.btnRegister.setOnClickListener {
            navController.navigate(R.id.action_registerFragment_to_registerOkFragment)
        }
    }
}