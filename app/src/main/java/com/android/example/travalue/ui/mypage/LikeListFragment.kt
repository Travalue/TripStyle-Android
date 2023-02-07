package com.android.example.travalue.ui.mypage

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentLikeListBinding
import com.android.example.travalue.databinding.FragmentMytravelListBinding

class LikeListFragment : BaseFragment<FragmentLikeListBinding>(R.layout.fragment_like_list) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).hideToolbar(false)
    }

    override fun initDataBinding() {
        super.initDataBinding()

//        binding.likeList.adapter = CategoryAdapter(getLikeList()) // 어댑터 생성
        //linearlayout
        //binding.likeList.layoutManager =

    }


    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}