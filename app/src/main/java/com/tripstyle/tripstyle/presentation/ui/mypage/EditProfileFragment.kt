package com.tripstyle.tripstyle.presentation.ui.mypage

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentEditProfileBinding
import com.tripstyle.tripstyle.MainActivity
import com.tripstyle.tripstyle.data.model.dto.*
import com.tripstyle.tripstyle.di.AppClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class EditProfileFragment  : BaseFragment<FragmentEditProfileBinding>(R.layout.fragment_edit_profile) {

    private val requestActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK && it.data?.data != null) { //갤러리 캡쳐 결과값
                val selectedImageUri = it?.data?.data!!
                binding.ivProfileImg.setImageURI(selectedImageUri)
            }
        }

    override fun initStartView() {
        super.initStartView()
    }

    override fun initDataBinding() {
        super.initDataBinding()
    }


    override fun initAfterBinding() {
        super.initAfterBinding()

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

                    binding.btnCheckNickname.setTextColor(ContextCompat.getColor(context!!, R.color.gray_959595))
                    binding.btnCheckNickname.isEnabled=false
                }else{
                    // 특수문자 비포함 시
                    binding.tvNicknameInfo2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_circle_gray_24,0,0,0)
                    binding.tvNicknameInfo2.setTextColor(ContextCompat.getColor(context!!, R.color.gray))
                    binding.etEditedNickname.setBackgroundResource(R.drawable.edittext_rounded_corner_rectangle)

                    // 중복확인 버튼 활성화
                    if(nickName.isNotEmpty()){
                        binding.btnCheckNickname.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                        binding.btnCheckNickname.isEnabled=true

                    }else{
                        binding.btnCheckNickname.setTextColor(ContextCompat.getColor(context!!, R.color.gray_959595))
                        binding.btnCheckNickname.isEnabled=false
                    }

                }
            }
        })

        // 프로필편집
        binding.btnEditProfileImg.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            intent.action = Intent.ACTION_PICK
            requestActivity.launch(intent)
        }

        // 중복확인 버튼
        binding.btnCheckNickname.setOnClickListener {
            val nickname = binding.etEditedNickname.text.toString()
            val resultData: Call<NicknameResponseModel> = AppClient.userService.checkNickname(nickname)
            resultData.enqueue(object : Callback<NicknameResponseModel> {
                @SuppressLint("ResourceAsColor")
                override fun onResponse(
                    call: Call<NicknameResponseModel>,
                    response: Response<NicknameResponseModel>
                ) {
                    if (response.isSuccessful) {
                        val result: NicknameResponseModel = response.body()!!
                        if(result.isDuplicate == false){
                            Log.d("[mypage-checkNickname]", "서버응답 : $result")
                            //수정 버튼 활성화

                            binding.tvCheckNickname.visibility = View.VISIBLE
                            binding.tvCheckNickname.text = "사용 가능한 별명입니다."
                            binding.tvCheckNickname.setTextColor(R.color.primaryColor)
                        }else{
                            Log.d("[mypage-checkNickname]", "서버응답 : $result")
                            //수정 버튼 비활성화

                            binding.tvCheckNickname.visibility = View.VISIBLE
                            binding.tvCheckNickname.text = "사용 불가능한 별명입니다."
                            binding.tvCheckNickname.setTextColor(Color.RED)
                        }
                    } else {
                        Log.d("[mypage-checkNickname]", "실패코드_${response.code()}")
                    }
                }

                override fun onFailure(call: Call<NicknameResponseModel>, t: Throwable) {
                    t.printStackTrace()
                    Log.d("[mypage-checkNickname]","통신 실패")
                }
            })
        }

        // 수정버튼
        binding.btnUpdate.setOnClickListener {
            val newUserProfile  = UpdateUserProfileRequestModel(binding.etEditedNickname.text.toString(),binding.etEditDescription.text.toString())

            val resultData: Call<UpdateUserProfileResponseModel> = AppClient.userService.updateMypage(newUserProfile)
            resultData.enqueue(object : Callback<UpdateUserProfileResponseModel> {
                override fun onResponse(
                    call: Call<UpdateUserProfileResponseModel>,
                    response: Response<UpdateUserProfileResponseModel>
                ) {
                    if (response.isSuccessful) {
                        val result: UpdateUserProfileResponseModel = response.body()!!
                        Log.d("[updateProfile]", result.toString())

                    } else {
                        Log.d("[updateProfile]", "실패코드_${response.code()}")
                    }
                }
                override fun onFailure(call: Call<UpdateUserProfileResponseModel>, t: Throwable) {
                    t.printStackTrace()
                    Log.d("[updateProfile]","통신 실패")
                }
            })

        }
    }
}