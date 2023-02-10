package com.android.example.travalue.ui.trailer

import androidx.navigation.NavDirections
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentTrailerBinding
import kotlin.math.abs
import kotlin.math.max

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
//            navController.navigate(R.id.action_trailerFragment_to_categoryDialogFragment)

            val action = TrailerFragmentDirections.actionTrailerFragmentToCategoryDialogFragment(binding.tvTrailer.text.toString())
            navController.navigate(action)
        }

    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }

    private fun initVerticalCardView(data : ArrayList<Int>){
        val adapter = TrailerViewPagerAdapter(data)
        adapter.setListener(object : onActionListener{
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

        val MIN_SCALE = 0.85f //애니메이션 동작 시 최소 크기는 원본의 85% 축소
        val MIN_ALPHA = 0.5f

        val pageMarginPy = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val offsetPy = resources.getDimensionPixelOffset(R.dimen.offset)

        binding.tpTrailerCard.setPageTransformer { page, position ->
            val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - abs(position))

            page.apply {
                when{
                    position < -1 -> { // [-Infinity,-1)
                        alpha = 0f
                        scaleX = scaleFactor
                        scaleY = scaleFactor
                    }
                    position <= 0 -> { // [-1,0]
                        alpha = 1-position
                        translationY = 1500 * -position
                        scaleX = scaleFactor
                        scaleY = scaleFactor
                    }
                    position <= 1 ->{ // (0,1]
                        alpha = 1f
                        scaleX = scaleFactor
                        scaleY = 1f

                        val viewPager = page.parent.parent as ViewPager2
                        val offset = position * -(2 * offsetPy+pageMarginPy)
                        if(viewPager.orientation == ViewPager2.ORIENTATION_VERTICAL){
                            page.translationY = offset
                        }else{
                            page.translationX = offset
                        }
                    }
                    else -> { // (1,+Infinity]
                        alpha = 0f
                        scaleX = scaleFactor
                        scaleY = 1f
                    }
                }
            }
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
}