package com.android.example.travalue.ui.mypage

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentMyPageMainBinding
import com.android.example.travalue.ui.trailer.TrailerViewPagerAdapter


class MyPageFragment  : BaseFragment<FragmentMyPageMainBinding>(R.layout.fragment_my_page_main) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("none")
    }

    override fun initDataBinding() {
        super.initDataBinding()

        binding.categoryCardList.adapter = CategoryAdapter(getCategoryImg()) // 어댑터 생성
        val gridLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(context,2)
        binding.categoryCardList.layoutManager = gridLayoutManager
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

        binding.btnToTravelList.setOnClickListener{
            navController.navigate(R.id.action_myPageFragment_to_myTravelListFragment)
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