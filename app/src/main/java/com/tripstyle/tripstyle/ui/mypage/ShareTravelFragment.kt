package com.tripstyle.tripstyle.ui.mypage

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentShareTravelBinding
import com.tripstyle.tripstyle.MainActivity


class ShareTravelFragment : BaseFragment<FragmentShareTravelBinding>(R.layout.fragment_share_travel) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).hideToolbar(false)
    }

    override fun initDataBinding() {
        super.initDataBinding()

        binding.rvCategory.adapter = CategoryAdapter(getCategoryList()) // 어댑터 생성
        val gridLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(context,2)
        binding.rvCategory.layoutManager = gridLayoutManager

        moveDetailPage()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
    }

    private fun moveDetailPage(){
        binding.btnCategoryAll.setOnClickListener {
            val type = "전체 보기"
            val action = ShareTravelFragmentDirections.actionShareTravelFragmentToShareTravelDetailFragment(type)
            navController.navigate(action)
        }

    }

    private fun getCategoryList() : ArrayList<Int> {
        return arrayListOf<Int>(
            R.drawable.ex_img1,
            R.drawable.ex_img1,
            R.drawable.ex_img1,
            R.drawable.ex_img1)
    }

}