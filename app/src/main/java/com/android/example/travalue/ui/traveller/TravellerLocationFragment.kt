package com.android.example.travalue.ui.traveller

import androidx.recyclerview.widget.GridLayoutManager
import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentTrailerSearchBinding
import com.android.example.travalue.databinding.FragmentTravellerLocationBinding

class TravellerLocationFragment : BaseFragment<FragmentTravellerLocationBinding>(R.layout.fragment_traveller_location) {
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("일정/장소 첨부")
    }

    override fun initDataBinding() {
        super.initDataBinding()

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