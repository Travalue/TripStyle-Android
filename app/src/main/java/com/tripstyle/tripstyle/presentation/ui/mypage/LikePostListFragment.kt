package com.tripstyle.tripstyle.presentation.ui.mypage

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.tripstyle.tripstyle.MainActivity
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.data.model.dto.TrailerItem
import com.tripstyle.tripstyle.data.model.dto.TrailerResponse
import com.tripstyle.tripstyle.data.source.remote.TravelService
import com.tripstyle.tripstyle.databinding.FragmentLikePostListBinding
import com.tripstyle.tripstyle.di.AppClient
import com.tripstyle.tripstyle.presentation.ui.trailer.TrailerViewPagerAdapter
import com.tripstyle.tripstyle.util.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LikePostListFragment : BaseFragment<FragmentLikePostListBinding>(R.layout.fragment_like_post_list) {

    lateinit var adapter : TrailerViewPagerAdapter

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).hideToolbar(false)
    }

    override fun initDataBinding() {
        super.initDataBinding()

        initRecyclerView()

    }


    private fun initRecyclerView(){
        adapter = TrailerViewPagerAdapter(requireContext(), Constant.TYPE_RECYCLER)
        binding.rvLikePostList.adapter = adapter // 어댑터 생성
        binding.rvLikePostList.layoutManager = LinearLayoutManager(context)
        binding.rvLikePostList.setHasFixedSize(true)
    }


    override fun initAfterBinding() {
        super.initAfterBinding()

        requestShrareTraveller()
    }
//    adapter.setData(list as ArrayList<TrailerItem>)
//    adapter.notifyDataSetChanged()


    private fun requestShrareTraveller() {

    }

}