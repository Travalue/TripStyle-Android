package com.tripstyle.tripstyle.ui.traveller

import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentTravellerLocationBinding
import com.tripstyle.tripstyle.MainActivity

class TravellerCategoryOptionFragment : BaseFragment<FragmentTravellerLocationBinding>(R.layout.fragment_traveller_category_option) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("")
    }

    override fun initDataBinding() {
        super.initDataBinding()

    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }

}