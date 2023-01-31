package com.android.example.travalue.ui.mypage

import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentEditProfileBinding

class EditProfileFragment  : BaseFragment<FragmentEditProfileBinding>(R.layout.fragment_edit_profile) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("visibleComplete")
    }

    override fun initDataBinding() {
        super.initDataBinding()

    }


    override fun initAfterBinding() {
        super.initAfterBinding()


    }
}