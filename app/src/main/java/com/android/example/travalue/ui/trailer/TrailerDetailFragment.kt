package com.android.example.travalue.ui.trailer

import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentTrailerDetailBinding
import com.naver.maps.map.MapFragment
import com.naver.maps.map.OnMapReadyCallback

class TrailerDetailFragment : BaseFragment<FragmentTrailerDetailBinding>(R.layout.fragment_trailer_detail) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("trailer")
        initMapView()

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



}