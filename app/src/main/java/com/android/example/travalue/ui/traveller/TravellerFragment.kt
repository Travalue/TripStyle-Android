package com.android.example.travalue.ui.traveller

import androidx.viewpager2.widget.ViewPager2
import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentTravellerBinding
import com.android.example.travalue.ui.trailer.TrailerFragmentDirections
import com.android.example.travalue.ui.trailer.TrailerViewPagerAdapter

class TravellerFragment : BaseFragment<FragmentTravellerBinding>(R.layout.fragment_traveller) {
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("none")
    }

    override fun initDataBinding() {
        super.initDataBinding()

        // category 이동
        binding.hambugerbar.setOnClickListener {
//            navController.navigate(R.id.action_travellerFragment_to_categoryDialogFragment)

            val action = TravellerFragmentDirections.actionTravellerFragmentToCategoryDialogFragment(binding.tvTraveller.text.toString())
            navController.navigate(action)
        }
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}