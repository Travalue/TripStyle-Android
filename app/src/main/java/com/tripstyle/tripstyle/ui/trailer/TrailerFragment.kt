package com.tripstyle.tripstyle.ui.trailer

import android.view.View
import androidx.navigation.NavDirections
import androidx.viewpager2.widget.ViewPager2
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentTrailerBinding
import com.tripstyle.tripstyle.MainActivity
import kotlin.math.abs

class TrailerFragment : BaseFragment<FragmentTrailerBinding>(R.layout.fragment_trailer) {

    override fun initStartView() {
        super.initStartView()
        //(activity as MainActivity).setToolbarTitle("none")
        (activity as MainActivity).hideToolbar(true)
    }

    override fun initDataBinding() {
        super.initDataBinding()

        //세로 viewpager 생성
        initVerticalCardView(getCardList())

        // category 이동
        binding.hambugerbar.setOnClickListener {
            val action = TrailerFragmentDirections.actionTrailerFragmentToCategoryDialogFragment(binding.tvTrailer.text.toString())
            navController.navigate(action)
        }

    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }

    private fun initVerticalCardView(data : ArrayList<Int>){
        val adapter = TrailerViewPagerAdapter(data)
        adapter.setListener(object : onActionListener {
            override fun onMoveDetailPage(): NavDirections {
                return TrailerFragmentDirections.actionTrailerFragmentToTrailerDetailFragment()
            }

        })
        binding.tpTrailerCard.adapter = adapter // 어댑터 생성
        binding.tpTrailerCard.orientation = ViewPager2.ORIENTATION_VERTICAL // 세로방향

        with(binding.tpTrailerCard){
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 1
        }

        binding.tpTrailerCard.setPageTransformer(SwipeTransformer())
    }

    // 뷰 페이저에 들어갈 아이템
    private fun getCardList(): ArrayList<Int> {
        return arrayListOf<Int>(
            R.drawable.card_img_example,
            R.drawable.card_img_example,
            R.drawable.card_img_example,
            R.drawable.card_img_example)
    }

    inner class SwipeTransformer : ViewPager2.PageTransformer{
        val MIN_SCALE = 0.85f
        val MIN_ALPHA = 0.5f

        val screenHeight = resources.displayMetrics.heightPixels //폰의 높이를 가져옴
        val pageMarginPy = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val offsetPy = resources.getDimensionPixelOffset(R.dimen.offset)
        val itemHeight = resources.getDimensionPixelOffset(R.dimen.pageHeight)
        val pageHeight = screenHeight - 2*(offsetPy+pageMarginPy) - offsetPy

        override fun transformPage(page: View, position: Float) {
            val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - abs(position))

            page.apply {
                when{
                    position < -1 -> {
                        alpha = 0f
                        scaleX = scaleFactor
                        scaleY = scaleFactor
                    }
                    position <= 0 -> {
                        alpha = (MIN_ALPHA + (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                        translationY = pageHeight * -position
                        scaleX = scaleFactor
                        scaleY = scaleFactor
                    }
                    position <= 1 ->{
                        alpha = 1f
                        scaleX = 1f
                        scaleY = scaleFactor

                        val offset = binding.tpTrailerCard.height - itemHeight - (2*offsetPy)
                        page.translationY = offset * -position
                    }
                    else -> {
                        alpha = 0f
                        scaleX = scaleFactor
                        scaleY = 1f
                    }
                }
            }
        }

    }
}