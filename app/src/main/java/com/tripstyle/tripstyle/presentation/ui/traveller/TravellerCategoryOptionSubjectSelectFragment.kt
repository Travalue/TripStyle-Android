package com.tripstyle.tripstyle.presentation.ui.traveller

import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.MainActivity
import com.tripstyle.tripstyle.databinding.FragmentTravellerCategoryOptionSubjectSelectBinding

class TravellerCategoryOptionSubjectSelectFragment : BaseFragment<FragmentTravellerCategoryOptionSubjectSelectBinding>(R.layout.fragment_traveller_category_option_subject_select) {

    private val viewModel by activityViewModels<TravellerWriteViewModel>()

    private lateinit var menuTextView: TextView

    override fun initStartView() {
        super.initStartView()
//        (activity as MainActivity).setToolbarTitle("")
        initMenu()
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

    private fun initMenu(){
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_traveller_save, menu)

                // TextView 스타일 변경
                val menuItem = menu.findItem(R.id.menu_traveller_btn_save)
                val actionView = LayoutInflater.from(context).inflate(R.layout.traveller_menu_style, null)
                menuTextView = actionView.findViewById<TextView>(R.id.tv_menu_text_style_true)

                menuTextView.text = menuItem.title
                menuItem.actionView = actionView

                menuTextView.setOnClickListener {
                    when (menuItem.itemId) {
                        R.id.menu_traveller_btn_save -> {
                            Log.e("","Category Subject Save Button Clicked")
                        }
                    }
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

}