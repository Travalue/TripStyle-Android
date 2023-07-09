package com.tripstyle.tripstyle.presentation.ui.traveller

import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentTravellerWriteDumpedBinding
import com.tripstyle.tripstyle.MainActivity

class TravellerWriteFragmentDUMPED : BaseFragment<FragmentTravellerWriteDumpedBinding>(R.layout.fragment_traveller_write_dumped) {
    override fun initStartView() {
        super.initStartView()
//        (activity as MainActivity).setToolbarTitle("글 작성하기")
    }

    override fun initDataBinding() {
        super.initDataBinding()

        // category 이동
//        binding.hambugerbar.setOnClickListener {
//            navController.navigate(R.id.action_travellerFragment_to_categoryDialogFragment)

//            val action = TravellerFragmentDirections.actionTravellerFragmentToCategoryDialogFragment(binding.tvTraveller.text.toString())
//            navController.navigate(action)
//        }

        binding.tvAddSchedule.setOnClickListener {
            navController.navigate(R.id.action_travellerWriteFragment_to_TravellerLocationFragment)
        }
        binding.ivCalendar.setOnClickListener {
            navController.navigate(R.id.action_travellerWriteFragment_to_TravellerLocationFragment)
        }
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}