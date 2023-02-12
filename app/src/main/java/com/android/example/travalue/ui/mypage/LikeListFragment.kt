package com.android.example.travalue.ui.mypage

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentLikeListBinding

class LikeListFragment : BaseFragment<FragmentLikeListBinding>(R.layout.fragment_like_list) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).hideToolbar(false)
    }

    override fun initDataBinding() {
        super.initDataBinding()

        binding.likeList.adapter = LikeListAdapter(getLikeList()) // 어댑터 생성
        binding.likeList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

    }


    override fun initAfterBinding() {
        super.initAfterBinding()

    }

    private fun getLikeList(): ArrayList<String> {
        return arrayListOf<String>(
            "순천 국가정원",
            "순천 국가정원",
            "순천 국가정원",
            "순천 국가정원",
            "순천 국가정원"
        )
    }
}