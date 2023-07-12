package com.tripstyle.tripstyle.presentation.ui.trailer

import android.util.Log
import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentTrailerBinding
import com.tripstyle.tripstyle.data.model.dto.TrailerItem
import com.tripstyle.tripstyle.data.model.dto.TrailerResponse
import com.tripstyle.tripstyle.di.AppClient
import com.tripstyle.tripstyle.data.source.remote.TravelService
import com.tripstyle.tripstyle.util.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.abs

class TrailerFragment : BaseFragment<FragmentTrailerBinding>(R.layout.fragment_trailer) {

    lateinit var adapter : TrailerViewPagerAdapter
    private val args by navArgs<TrailerFragmentArgs>()

    override fun initStartView() {
        super.initStartView()
    }

    override fun initDataBinding() {
        super.initDataBinding()

        //세로 viewpager 생성
        initVerticalCardView()

    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        requestTrailerList()
    }

    private fun requestTrailerList(){
        val service = AppClient.retrofit?.create(TravelService::class.java)

        service?.getTrailerList()?.enqueue(object : Callback<TrailerResponse>{
            override fun onResponse(
                call: Call<TrailerResponse>,
                response: Response<TrailerResponse>
            ) {
                val trailerList = response.body()?.data
                Log.d("data",trailerList.toString())
                adapter.setData(trailerList as ArrayList<TrailerItem>)
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<TrailerResponse>, t: Throwable) {

            }

        })
    }

    private fun initVerticalCardView(){
        adapter = TrailerViewPagerAdapter(requireContext(),Constant.TYPE_PAGER)
        adapter.setListener(object : onActionListener {
            override fun onMoveDetailPage(id:Int): NavDirections {
                return TrailerFragmentDirections.actionTrailerFragmentToTrailerDetailFragment(id)
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