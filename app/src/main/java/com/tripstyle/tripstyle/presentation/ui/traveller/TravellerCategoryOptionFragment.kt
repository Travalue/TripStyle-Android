package com.tripstyle.tripstyle.presentation.ui.traveller

import android.view.View
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.MainActivity
import com.tripstyle.tripstyle.databinding.FragmentTravellerCategoryOptionBinding

class TravellerCategoryOptionFragment : BaseFragment<FragmentTravellerCategoryOptionBinding>(R.layout.fragment_traveller_category_option) {

    override fun initStartView() {
        super.initStartView()
//        (activity as MainActivity).setToolbarTitle("")
    }

    override fun initDataBinding() {
        super.initDataBinding()
        binding.tvCategoryAdd.setOnClickListener {
            navController.navigate(R.id.action_categoryOptionFragment_to_categoryOptionSubjectFragment)
        }

        binding.radioGroupOpenStatus.setOnCheckedChangeListener{ group, checkedId ->
            when(checkedId){
                R.id.button_public -> changeOpenStatusTextView(true)
                R.id.button_private -> changeOpenStatusTextView(false)
            }
        }
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }

    private fun changeOpenStatusTextView(isPublic: Boolean){
        if(isPublic) {
            binding.tvOpenStatusPrivate.visibility = View.INVISIBLE
            binding.tvOpenStatusPublic.visibility = View.VISIBLE
        }
        else{
            binding.tvOpenStatusPublic.visibility = View.INVISIBLE
            binding.tvOpenStatusPrivate.visibility = View.VISIBLE
        }
    }

}