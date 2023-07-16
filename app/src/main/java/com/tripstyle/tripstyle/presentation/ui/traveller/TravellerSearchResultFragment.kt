package com.tripstyle.tripstyle.presentation.ui.traveller

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.data.model.dto.TravellerSearchResponse
import com.tripstyle.tripstyle.data.model.dto.TravellerSearchResult
import com.tripstyle.tripstyle.data.source.remote.TravelService
import com.tripstyle.tripstyle.databinding.FragmentTravellerSearchResultBinding
import com.tripstyle.tripstyle.di.AppClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TravellerSearchResultFragment : BaseFragment<FragmentTravellerSearchResultBinding>(R.layout.fragment_traveller_search_result) {
    lateinit var travellerSearchResultAdapter : TravellerSearchResultRecyclerViewAdapter
    override fun initStartView() {
        super.initStartView()
//        (activity as MainActivity).setToolbarTitle("visible")

        //recyclerView adapter
        travellerSearchResultAdapter = TravellerSearchResultRecyclerViewAdapter(context)

        binding.trailerSearchResult.adapter = travellerSearchResultAdapter
        binding.trailerSearchResult.layoutManager = GridLayoutManager(context,3)
    }

    override fun initDataBinding() {
        super.initDataBinding()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        val bundle = arguments
        if (bundle != null) {
            val searchText = bundle.getString("searchText", "")
            if(!searchText.isNullOrBlank()){
                searchTraveller(searchText)
            }
        }
    }



    private fun searchTraveller(keyword: String) {
        val service = AppClient.retrofit?.create(TravelService::class.java)

        service?.searchTraveller(keyword)?.enqueue(object : Callback<TravellerSearchResponse> {
            override fun onResponse(
                call: Call<TravellerSearchResponse>,
                response: Response<TravellerSearchResponse>
            ) {
                if (response.isSuccessful) {
                    val searchTravellerResults = response.body()?.data

                    travellerSearchResultAdapter.setData(searchTravellerResults as ArrayList<TravellerSearchResult>)
                    travellerSearchResultAdapter.notifyDataSetChanged()
                } else {
                    Log.e("Server error","Server error, CODE: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TravellerSearchResponse>, t: Throwable) {
                Log.e("Network error","Server connect failed")
            }
        })
    }


}