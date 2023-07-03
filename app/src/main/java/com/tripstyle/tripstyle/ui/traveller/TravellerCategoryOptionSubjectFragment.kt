package com.tripstyle.tripstyle.ui.traveller

import androidx.fragment.app.activityViewModels
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.MainActivity
import com.tripstyle.tripstyle.databinding.FragmentTravellerCategoryOptionSubjectBinding

class TravellerCategoryOptionSubjectFragment : BaseFragment<FragmentTravellerCategoryOptionSubjectBinding>(R.layout.fragment_traveller_category_option_subject) {

    private val viewModel by activityViewModels<TravellerWriteViewModel>()

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("")
    }

    override fun initDataBinding() {
        super.initDataBinding()
        binding.tvSubjectCategorySelected.setOnClickListener {
            navController.navigate(R.id.action_categoryOptionSubjectFragment_to_categoryOptionSubjectSelectFragment)
        }

        // 사용자가 선택한 카테고리 주제
        viewModel.categorySubjectLiveData.observe(viewLifecycleOwner){
            if(!it.isNullOrBlank())
                binding.tvSubjectCategorySelected.text = it
        }

    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }

}