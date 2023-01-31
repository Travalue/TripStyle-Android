package com.android.example.travalue.ui.mypage

import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentMyPageMainBinding

class MyPageFragment  : BaseFragment<FragmentMyPageMainBinding>(R.layout.fragment_my_page_main) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("none")
    }

    override fun initDataBinding() {
        super.initDataBinding()

    }


    override fun initAfterBinding() {
        super.initAfterBinding()

        binding.btnCategory.setOnClickListener {
            val action = MyPageFragmentDirections.actionMyPageFragmentToCategoryDialogFragment(binding.tvMyPage.text.toString())
            navController.navigate(action)
        }

        binding.btnEditProfile.setOnClickListener {
            navController.navigate(R.id.action_myPageFragment_to_editProfileFragment)
        }



    }
}