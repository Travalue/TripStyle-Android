package com.tripstyle.tripstyle.presentation.ui.mypage

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentShareTravelDetailBinding
import com.tripstyle.tripstyle.MainActivity
import com.tripstyle.tripstyle.data.model.dto.BaseResponseModel
import com.tripstyle.tripstyle.data.model.dto.TrailerItem
import com.tripstyle.tripstyle.data.model.dto.TrailerResponse
import com.tripstyle.tripstyle.data.source.remote.TravelService
import com.tripstyle.tripstyle.di.AppClient
import com.tripstyle.tripstyle.presentation.ui.trailer.TrailerFragmentDirections
import com.tripstyle.tripstyle.presentation.ui.trailer.TrailerViewPagerAdapter
import com.tripstyle.tripstyle.presentation.ui.trailer.onActionListener
import com.tripstyle.tripstyle.util.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShareTravelDetailFragment : BaseFragment<FragmentShareTravelDetailBinding>(R.layout.fragment_share_travel_detail) {

    val args: ShareTravelDetailFragmentArgs by navArgs()
    lateinit var adapter : TrailerViewPagerAdapter

    override fun initStartView() {
        super.initStartView()

        val mainActivity = activity as MainActivity
        mainActivity.setTitle(args.type)
        if(args.type != "전체 보기") initMenu()
    }

    override fun initDataBinding() {
        super.initDataBinding()

        initRecyclerView()
    }

    private fun initRecyclerView(){
        adapter = TrailerViewPagerAdapter(requireContext(),Constant.TYPE_RECYCLER)
        binding.rvCategoryList.adapter = adapter // 어댑터 생성
        binding.rvCategoryList.layoutManager = LinearLayoutManager(context)
        binding.rvCategoryList.setHasFixedSize(true)
    }

    private fun initMenu(){
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_category_edit, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_edit_button -> {
                        navController.navigate(R.id.action_shareTravelDetailFragment_to_categoryEditFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        requestShrareTraveller()
    }


    private fun requestShrareTraveller(){
        val service = AppClient.retrofit?.create(TravelService::class.java)
        // TODO : user id 하드코딩 / 로그인 연결 후 변경하기
        service?.getTravellerShare(1)?.enqueue(object : Callback<TrailerResponse> {
            override fun onResponse(
                call: Call<TrailerResponse>,
                response: Response<TrailerResponse>
            ) {
                val list = response.body()?.data
                Log.d("data",list.toString())
                adapter.setData(list as ArrayList<TrailerItem>)
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<TrailerResponse>, t: Throwable) {
                Log.d("data","네트워크 연결 실패 : ${t}")
            }

        })
    }
    private fun getCardList(): ArrayList<Int> {
        return arrayListOf<Int>(
            R.drawable.card_img_example,
            R.drawable.card_img_example,
            R.drawable.card_img_example,
            R.drawable.card_img_example)
    }
}