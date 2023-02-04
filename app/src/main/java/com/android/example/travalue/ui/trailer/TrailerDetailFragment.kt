package com.android.example.travalue.ui.trailer

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentTrailerDetailBinding
import com.android.example.travalue.util.ScheduleAdapter
import com.naver.maps.map.MapFragment
import com.naver.maps.map.OnMapReadyCallback

class TrailerDetailFragment : BaseFragment<FragmentTrailerDetailBinding>(R.layout.fragment_trailer_detail) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("trailer")
        //initMapView()
        animationScheduleView()
        initScheduleView()
    }

    override fun initDataBinding() {
        super.initDataBinding()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
    }

    private fun initMapView(){
        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment,it).commit()
            }

        val onMapReadyCallback = OnMapReadyCallback {

        }

        mapFragment.getMapAsync(onMapReadyCallback)
    }
    private fun animationScheduleView(){
        binding.layoutScheduleTitle.setOnClickListener {
            if(binding.layoutScheduleDetail.visibility == View.VISIBLE){
                binding.layoutScheduleDetail.visibility = View.GONE
                binding.scheduleButton.animate().apply {
                    duration = 300
                    rotation(0f)
                }
            }else{
                val viewHeight = binding.layoutScheduleDetail.height
                val scrollY = binding.scrollView.scrollY

                binding.layoutScheduleDetail.visibility = View.VISIBLE
                binding.scheduleButton.animate().apply {
                    duration = 300
                    rotation(180f)
                }
                binding.scrollView.post {
                    binding.scrollView.smoothScrollTo(0,scrollY+viewHeight)
                }

            }
        }
    }

    private fun initScheduleView(){
        val adapter = ScheduleAdapter(getScheduleList())

        binding.rvSchedule.addItemDecoration(adapter.ItemDecorator(-80))
        binding.rvSchedule.adapter = ScheduleAdapter(getScheduleList())
        binding.rvSchedule.layoutManager = LinearLayoutManager(context)
    }

    //mockup data
    private fun getScheduleList(): ArrayList<String> {
        return arrayListOf(
            "순천 국가정원",
            "순천 국가정원",
            "순천 국가정원",
            "순천 국가정원")
    }


}