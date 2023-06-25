package com.android.example.travalue.ui.traveller

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentTrailerSearchBinding
import com.android.example.travalue.databinding.FragmentTravellerLocationBinding
import com.android.example.travalue.network.MapClient
import com.android.example.travalue.network.MapService
import com.android.example.travalue.network.res.ItemData
import com.android.example.travalue.network.res.SearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TravellerLocationFragment : BaseFragment<FragmentTravellerLocationBinding>(R.layout.fragment_traveller_location) {

    var result = emptyList<String>()
    lateinit var adapter : TravellerLocationRecyclerViewAdapter

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("일정/장소 첨부")
    }

    override fun initDataBinding() {
        super.initDataBinding()

        //장소 리스트 adapter
        adapter = TravellerLocationRecyclerViewAdapter(context)
        adapter.setListener(object :onSelectedLocationListener{
            override fun selectLocation(id: Int) {
                // 선택한 주소의 x,y 값 api call

            }
        })
        binding.rvLocationList.adapter = adapter
        binding.rvLocationList.layoutManager = LinearLayoutManager(context)
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
        getLocationList("제주 국제공항")
    }

    fun getLocationList(query : String){
        val service = MapClient.locationRetrofit?.create(MapService::class.java)
            service?.getMapSerachResult(query)?.enqueue(object : Callback<SearchResult>{
                override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                    val items = response.body()?.items
                    adapter.setData(items as ArrayList<ItemData>)
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })


    }
}