package com.android.example.travalue.ui.traveller

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentTrailerSearchBinding
import com.android.example.travalue.databinding.FragmentTravellerLocationBinding

class TravellerLocationFragment : BaseFragment<FragmentTravellerLocationBinding>(R.layout.fragment_traveller_location) {

    var result = emptyList<String>()
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("일정/장소 첨부")
    }

    override fun initDataBinding() {
        super.initDataBinding()

        //장소 리스트 adapter
        result = arrayListOf("dte","tets","Test") //mockup 데이터
        binding.rvLocationList.adapter = TravellerLocationRecyclerViewAdapter(context,result)
        binding.rvLocationList.layoutManager = LinearLayoutManager(context)
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}