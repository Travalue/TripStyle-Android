package com.tripstyle.tripstyle.presentation.ui.traveller

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.data.model.dto.HotTravellerItem
import com.tripstyle.tripstyle.databinding.FragmentTravellerBinding
import com.tripstyle.tripstyle.data.model.dto.HotTravellerResponse
import com.tripstyle.tripstyle.data.source.remote.TravelService
import com.tripstyle.tripstyle.di.AppClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TravellerFragment : BaseFragment<FragmentTravellerBinding>(R.layout.fragment_traveller) {
    private val viewModel by viewModels<TravellerSearchViewModel>()
    lateinit var hotTravellerAdapter : TravellerHotAdapter


    override fun initStartView() {
        super.initStartView()

        initAdapter()
    }

    override fun initDataBinding() {
        super.initDataBinding()

        initFirstSetting()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        requestHotTravellerList()
    }

    private fun initAdapter() {
        // 지금 핫한 트레블러 adapter
        hotTravellerAdapter = TravellerHotAdapter(context)

        // 검색 adapter
        val searchDomesticAdapter = TravellerSearchDomesticAdapter(viewModel, context)
        val searchOverseasAdapter = TravellerSearchOverseasAdapter(viewModel, context)


        searchDomesticAdapter.setOnItemClickListener(object:
            TravellerSearchDomesticAdapter.OnItemClickListener {
            override fun onItemClick(pos: Int,city: String, searchText: String) {
                //parameter에 있는 searchText는 활용 못함
                //여기서 검색 결과 화면으로 검색어만 넘겨주고, 검색 결과 화면에서 검색 수행
                val bundle = Bundle()
                bundle.putString("searchText",binding.etSearch.toString())
                navController.navigate(R.id.action_travellerFragment_to_travellerSearchResultFragment,bundle)
            }
        })

        searchOverseasAdapter.setOnItemClickListener(object:
            TravellerSearchOverseasAdapter.OnItemClickListener {
            override fun onItemClick(pos: Int,city: String, searchText: String) {
                val bundle = Bundle()
                bundle.putString("searchText",binding.etSearch.toString())
                navController.navigate(R.id.action_travellerFragment_to_travellerSearchResultFragment,bundle)
            }
        })

        // 검색쪽 recyclerView adapter
        binding.recyclerView.adapter = searchDomesticAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.recyclerView2.adapter = searchOverseasAdapter
        binding.recyclerView2.layoutManager = LinearLayoutManager(context)


        // 지금 핫한 트레블러 adapter
        binding.rvHotTraveller.adapter = hotTravellerAdapter
        binding.rvHotTraveller.layoutManager = LinearLayoutManager(context)
    }

    private fun initFirstSetting() {
        // search 테스트용 임시 데이터
        val domesticCityList = ArrayList<String>()
        domesticCityList.add("서울")
        domesticCityList.add("부산")
        domesticCityList.add("제주")

        val overseasCityList = ArrayList<String>()
        overseasCityList.add("아시아")
        overseasCityList.add("유럽")
        overseasCityList.add("아메리카")
        overseasCityList.add("아프리카")


        viewModel.domesticCitiesListData.observe(this){
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }

        viewModel.overseasCitiesListData.observe(this){
            binding.recyclerView2.adapter?.notifyDataSetChanged()
        }


        //트레블러 탭에서 여행지 검색
        binding.etSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val text = binding.etSearch.text

                binding.tvNoResult.visibility=View.GONE
                binding.tvNoResult2.visibility=View.GONE

                if(text.isNotEmpty()){
                    viewModel.deleteDomesticCities()
                    viewModel.deleteOverseasCities()

                    binding.contraint2.visibility=View.GONE
                    binding.fab.visibility=View.GONE

                    binding.line3.visibility = View.VISIBLE

                    domesticCityList.forEach{
                        if(it.contains(text)) {
                            viewModel.addDomesticCities(Item(it,it))

//                            binding.tvDomesticSearch.visibility=View.VISIBLE
                            binding.recyclerView.visibility= View.VISIBLE
                        }
                    }

                    overseasCityList.forEach {
                        if(it.contains(text)) {
                            viewModel.addOverseasCities(Item(it,it))

//                            binding.tvOverseasSearch.visibility=View.VISIBLE
                            binding.recyclerView2.visibility= View.VISIBLE
                        }
                    }

                    if(viewModel.domesticCities.isEmpty() && viewModel.overseasCities.isEmpty()){
                        binding.tvNoResult.visibility=View.VISIBLE
                        binding.tvNoResult2.visibility=View.VISIBLE
                    }
                }
                else{

                    viewModel.deleteDomesticCities()
//                    binding.tvDomesticSearch.visibility=View.GONE
                    binding.recyclerView.visibility= View.INVISIBLE

                    viewModel.deleteOverseasCities()
//                    binding.tvOverseasSearch.visibility=View.GONE
                    binding.recyclerView2.visibility= View.INVISIBLE

                    binding.line3.visibility=View.INVISIBLE

                    binding.contraint2.visibility=View.VISIBLE
                    binding.fab.visibility=View.VISIBLE
                }

                if(viewModel.domesticCities.isEmpty()){
//                    binding.tvDomesticSearch.visibility=View.GONE
                    binding.recyclerView.visibility= View.INVISIBLE

                }
                if(viewModel.overseasCities.isEmpty()){
//                    binding.tvOverseasSearch.visibility=View.GONE
                    binding.recyclerView2.visibility= View.INVISIBLE

                }

            }

        })


        binding.fab.setOnClickListener {
            navController.navigate(R.id.action_travellerFragment_to_TravellerWriteFragment)
        }

    }

    private fun requestHotTravellerList(){
        val service = AppClient.retrofit?.create(TravelService::class.java)

        service?.getHotTravellerList()?.enqueue(object : Callback<HotTravellerResponse> {
            override fun onResponse(
                call: Call<HotTravellerResponse>,
                response: Response<HotTravellerResponse>
            ) {
                val hotTravellerList = response.body()?.data
//                Log.e("HotTravellerData","Hot Traveller Data: ${hotTravellerList.toString()}")
                hotTravellerAdapter.setData(hotTravellerList as ArrayList<HotTravellerItem>)
                hotTravellerAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<HotTravellerResponse>, t: Throwable) {
                Log.e("HotTravellerResponse","requestHotTravellerList API CALL FAILED")
            }
        })
    }

}