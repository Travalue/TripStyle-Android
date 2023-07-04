package com.tripstyle.tripstyle.ui.traveller

import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.MainActivity
import com.tripstyle.tripstyle.databinding.FragmentTravellerCategoryOptionBinding

class TravellerCategoryOptionFragment : BaseFragment<FragmentTravellerCategoryOptionBinding>(R.layout.fragment_traveller_category_option) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("")
    }

    override fun initDataBinding() {
        super.initDataBinding()
        binding.tvCategoryAdd.setOnClickListener {
            navController.navigate(R.id.action_categoryOptionFragment_to_categoryOptionSubjectFragment)
        }
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }

}