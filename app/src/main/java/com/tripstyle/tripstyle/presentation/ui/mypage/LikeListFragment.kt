package com.tripstyle.tripstyle.presentation.ui.mypage

import androidx.recyclerview.widget.LinearLayoutManager
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentLikeListBinding
import com.tripstyle.tripstyle.MainActivity

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