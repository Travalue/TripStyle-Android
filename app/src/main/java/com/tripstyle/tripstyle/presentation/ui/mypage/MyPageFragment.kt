package com.tripstyle.tripstyle.presentation.ui.mypage

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentMyPageMainBinding
import com.tripstyle.tripstyle.MainActivity


class MyPageFragment  : BaseFragment<FragmentMyPageMainBinding>(R.layout.fragment_my_page_main) {

    private var addPlace :ArrayList<String> =arrayListOf("미국")

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("none")

    }

    override fun initDataBinding() {
        super.initDataBinding()

        if(addPlace.size > 0){
            binding.tvNoDestination.text=""
        }else{
            binding.tvNoDestination.text="나의 여행지를 추가해보세요."
        }


        // 나의 여행지 리스트 어댑터
        binding.placeList.adapter = MyTravelListAdapter(addPlace,false) // 어댑터 생성
        val gridLayoutManager1: RecyclerView.LayoutManager = GridLayoutManager(context,4)
        binding.placeList.layoutManager = gridLayoutManager1

        // 공유중인 여행지 어댑터
        binding.categoryCardList.adapter = CategoryAdapter(getCategoryImg()) // 어댑터 생성
        val gridLayoutManager2: RecyclerView.LayoutManager = GridLayoutManager(context,2)
        binding.categoryCardList.layoutManager = gridLayoutManager2

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

        //공유중인 여행지 UI로 이동
        binding.btnDestinationDetail3.setOnClickListener {
            navController.navigate(R.id.action_myPageFragment_to_shareTravelFragment)
        }

        binding.btnToTravelList.setOnClickListener{
            navController.navigate(R.id.action_myPageFragment_to_myTravelListFragment)
        }

        binding.btnToLikeList.setOnClickListener{
            navController.navigate(R.id.action_myPageFragment_to_likeListFragment)
        }
    }

    private fun getCategoryImg(): ArrayList<Int> {
        return arrayListOf<Int>(
            R.drawable.ex_img1,
            R.drawable.ex_img1,
            R.drawable.ex_img1,
            R.drawable.ex_img1)
    }

}