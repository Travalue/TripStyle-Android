package com.tripstyle.tripstyle.ui.traveller

import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.MainActivity
import com.tripstyle.tripstyle.databinding.FragmentTravellerCategoryOptionSubjectBinding

class TravellerCategoryOptionSubjectFragment : BaseFragment<FragmentTravellerCategoryOptionSubjectBinding>(R.layout.fragment_traveller_category_option_subject) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("")
    }

    override fun initDataBinding() {
        super.initDataBinding()
        binding.tvSubjectCategorySelected.setOnClickListener {
            navController.navigate(R.id.action_categoryOptionSubjectFragment_to_categoryOptionSubjectSelectFragment)
        }

    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }

}