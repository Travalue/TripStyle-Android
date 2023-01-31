package com.android.example.travalue.ui.trailer

import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentTrailerBinding

class TrailerDetailFragment : BaseFragment<FragmentTrailerBinding>(R.layout.fragment_trailer_detail) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("trailer")
    }

    override fun initDataBinding() {
        super.initDataBinding()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
    }

}