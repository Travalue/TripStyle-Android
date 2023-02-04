package com.android.example.travalue.ui.mypage

import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentMytravelListBinding

class MyTravelListFragment : BaseFragment<FragmentMytravelListBinding>(R.layout.fragment_mytravel_list) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("나의 여행지 리스트")
    }

    override fun initDataBinding() {
        super.initDataBinding()


    }


    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}