package com.android.example.travalue.ui.traveller

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentTravellerBinding

class TravellerFragment : BaseFragment<FragmentTravellerBinding>(R.layout.fragment_traveller) {
    private val viewModel by viewModels<TravellerSearchViewModel>()


    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("none")

        //recyclerView adapter
        binding.recyclerView.adapter = TravellerSearchRecyclerViewAdapter(viewModel, context)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.recyclerView2.adapter = TravellerSearchRecyclerViewAdapter2(viewModel, context)
        binding.recyclerView2.layoutManager = LinearLayoutManager(context)
    }

    override fun initDataBinding() {
        super.initDataBinding()


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
            Log.e("t","observer called")
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }

        viewModel.overseasCitiesListData.observe(this){
            binding.recyclerView2.adapter?.notifyDataSetChanged()
        }

        binding.etSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                Log.e("t","afterTextChanged called")
                val text = binding.etSearch.text

                if(text.isNotEmpty()){
                    viewModel.deleteDomesticCities()
                    viewModel.deleteOverseasCities()

                    binding.contraint2.visibility=View.GONE

                    domesticCityList.forEach{
                        if(it.contains(text)) {
                            viewModel.addDomesticCities(Item(it,it))

                            binding.tvDomesticSearch.visibility=View.VISIBLE
                            binding.recyclerView.visibility= View.VISIBLE
                        }
                    }

                    overseasCityList.forEach {
                        if(it.contains(text)) {
                            viewModel.addOverseasCities(Item(it,it))

                            binding.tvOverseasSearch.visibility=View.VISIBLE
                            binding.recyclerView2.visibility= View.VISIBLE
                        }
                    }
                }
                else{
                    viewModel.deleteDomesticCities()
                    binding.tvDomesticSearch.visibility=View.GONE
                    binding.recyclerView.visibility= View.GONE

                    viewModel.deleteOverseasCities()
                    binding.tvOverseasSearch.visibility=View.GONE
                    binding.recyclerView2.visibility= View.GONE

                    binding.contraint2.visibility=View.VISIBLE
                }

//                viewModel.domesticCities.forEach {
//                    System.out.println(it.cities)
//                }

            }


        })






        // category 이동
        binding.hambugerbar.setOnClickListener{
            navController.navigate(R.id.action_travellerFragment_to_categoryDialogFragment)
        }


        /*

        // 임시
        binding.ivJeju.setOnClickListener {
            navController.navigate(R.id.action_travellerFragment_to_TrailerSearchFragment)
        }

         */

        binding.fab.setOnClickListener {
            navController.navigate(R.id.action_travellerFragment_to_TravellerWriteFragment)
        }






    }

    override fun initAfterBinding() {
        super.initAfterBinding()


    }

}