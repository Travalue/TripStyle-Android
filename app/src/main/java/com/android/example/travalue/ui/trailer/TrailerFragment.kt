package com.android.example.travalue.ui.trailer

import androidx.viewpager2.widget.ViewPager2
import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentTrailerBinding

class TrailerFragment : BaseFragment<FragmentTrailerBinding>(R.layout.fragment_trailer) {
    lateinit var viewPager_aespa: ViewPager2

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("none")
    }

    override fun initDataBinding() {
        super.initDataBinding()

        binding.tpTrailerCard.adapter = TrailerViewPagerAdapter(getCardList()) // 어댑터 생성
        binding.tpTrailerCard.orientation = ViewPager2.ORIENTATION_VERTICAL // 방향을 가로로

        // category 이동
        binding.hambugerbar.setOnClickListener {
            navController.navigate(R.id.action_trailerFragment_to_categoryFragment)
        }
    }

    // 뷰 페이저에 들어갈 아이템
    private fun getCardList(): ArrayList<Int> {
        return arrayListOf<Int>(
            R.drawable.card_img_example,
            R.drawable.card_img_example,
            R.drawable.card_img_example,
            R.drawable.card_img_example)
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}