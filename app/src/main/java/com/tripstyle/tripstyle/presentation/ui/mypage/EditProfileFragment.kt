package com.tripstyle.tripstyle.presentation.ui.mypage

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentEditProfileBinding
import com.tripstyle.tripstyle.MainActivity
import java.util.regex.Pattern

class EditProfileFragment  : BaseFragment<FragmentEditProfileBinding>(R.layout.fragment_edit_profile) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("프로필 편집")
    }

    override fun initDataBinding() {
        super.initDataBinding()

        // 닉네임 입력 감지 리스너
        binding.etEditedNickname.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val searchString = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // TODO("not implemented")
                // To change body of created functions use File | Settings | File Templates.
            }

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val nickName = binding.etEditedNickname.text


                // 공백 미포함 특수문자 체크
                val pattern = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]")
                if (pattern.matcher(nickName).find()) {
                    // 특수문자 포함 시
                    binding.tvNicknameInfo2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_circle_red_24,0,0,0)
                    binding.tvNicknameInfo2.setTextColor(ContextCompat.getColor(context!!, R.color.red))
                    binding.etEditedNickname.setBackgroundResource(R.drawable.edittext_rounded_corner_rectangle_red)

                    binding.btnCheckNickname.setTextColor(ContextCompat.getColor(context!!, R.color.drakGray))
                    binding.btnCheckNickname.isEnabled=false
                }else{
                    // 특수문자 비포함 시
                    binding.tvNicknameInfo2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_circle_gray_24,0,0,0)
                    binding.tvNicknameInfo2.setTextColor(ContextCompat.getColor(context!!, R.color.gray))
                    binding.etEditedNickname.setBackgroundResource(R.drawable.edittext_rounded_corner_rectangle)

                    // 중복확인 버튼 활성화
                    if(nickName.isNotEmpty()){
                        binding.btnCheckNickname.setTextColor(ContextCompat.getColor(context!!, R.color.black))
                        binding.btnCheckNickname.isEnabled=true

                    }else{
                        binding.btnCheckNickname.setTextColor(ContextCompat.getColor(context!!, R.color.drakGray))
                        binding.btnCheckNickname.isEnabled=false
                    }

                }
            }
        })
    }


    override fun initAfterBinding() {
        super.initAfterBinding()




    }
}