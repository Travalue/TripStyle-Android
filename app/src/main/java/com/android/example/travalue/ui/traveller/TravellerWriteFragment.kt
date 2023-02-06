package com.android.example.travalue.ui.traveller

import androidx.recyclerview.widget.GridLayoutManager
import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentTrailerSearchBinding

class TravellerWriteFragment : BaseFragment<FragmentTrailerSearchBinding>(R.layout.fragment_trailer_search) {
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("visible")
    }

    override fun initDataBinding() {
        super.initDataBinding()

        //recyclerView adapter
//        binding.trailerSearchResult.adapter = TrailerSearchRecyclerViewAdapter(context)
//        binding.trailerSearchResult.layoutManager = GridLayoutManager(context,3)

        // category 이동
//        binding.hambugerbar.setOnClickListener {
//            navController.navigate(R.id.action_travellerFragment_to_categoryDialogFragment)

//            val action = TravellerFragmentDirections.actionTravellerFragmentToCategoryDialogFragment(binding.tvTraveller.text.toString())
//            navController.navigate(action)
//        }
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}