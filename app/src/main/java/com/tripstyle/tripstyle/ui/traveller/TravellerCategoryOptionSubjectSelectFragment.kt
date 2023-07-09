package com.tripstyle.tripstyle.ui.traveller

import androidx.fragment.app.activityViewModels
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.MainActivity
import com.tripstyle.tripstyle.databinding.FragmentTravellerCategoryOptionSubjectSelectBinding

class TravellerCategoryOptionSubjectSelectFragment : BaseFragment<FragmentTravellerCategoryOptionSubjectSelectBinding>(R.layout.fragment_traveller_category_option_subject_select) {

    private val viewModel by activityViewModels<TravellerWriteViewModel>()


    override fun initStartView() {
        super.initStartView()
//        (activity as MainActivity).setToolbarTitle("")
    }

    override fun initDataBinding() {
        super.initDataBinding()

        binding.radiogroupCategorySubject.setOnCheckedChangeListener{ group, checkedId ->
            when(checkedId){
                R.id.button_epicurism -> backToPreviousScreen(binding.buttonEpicurism.text.toString())
                R.id.button_activity -> backToPreviousScreen(binding.buttonActivity.text.toString())
                R.id.button_rest -> backToPreviousScreen(binding.buttonRest.text.toString())
                R.id.button_package -> backToPreviousScreen(binding.buttonPackage.text.toString())
            }
        }

    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }

    private fun backToPreviousScreen(subject: String){
        viewModel.updateCategorySubject(subject)
        navController.popBackStack()
    }

}