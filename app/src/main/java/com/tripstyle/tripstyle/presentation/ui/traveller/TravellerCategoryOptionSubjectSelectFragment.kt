package com.tripstyle.tripstyle.presentation.ui.traveller

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
import com.tripstyle.tripstyle.databinding.FragmentTravellerCategoryOptionSubjectSelectBinding

class TravellerCategoryOptionSubjectSelectFragment : BaseFragment<FragmentTravellerCategoryOptionSubjectSelectBinding>(R.layout.fragment_traveller_category_option_subject_select) {

    private val viewModel by activityViewModels<TravellerWriteViewModel>()
    private lateinit var menuTextView: TextView

    override fun initStartView() {
        super.initStartView()

        initMenu()
    }

    override fun initDataBinding() {
        super.initDataBinding()

        initRadioButton()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }

    // RadioButton 초기 상태 설정
    private fun initRadioButton(){
        when(viewModel.categorySubjectLiveData.value){
            "식도락" -> binding.buttonEpicurism.isChecked = true
            "액티비티" -> binding.buttonActivity.isChecked = true
            "휴양" -> binding.buttonRest.isChecked = true
            "패키지" -> binding.buttonPackage.isChecked = true
            else -> binding.buttonEpicurism.isChecked = true
        }
    }

    // 현재 선택된 RadioButton에 따라 ViewModel을 업데이트하고 이전 화면으로 이동
    private fun saveStatusAndBackToPreviousScreen(){
        when(binding.radiogroupCategorySubject.checkedRadioButtonId){
            R.id.button_epicurism -> viewModel.updateCategorySubject(binding.buttonEpicurism.text.toString())
            R.id.button_activity -> viewModel.updateCategorySubject(binding.buttonActivity.text.toString())
            R.id.button_rest -> viewModel.updateCategorySubject(binding.buttonRest.text.toString())
            R.id.button_package -> viewModel.updateCategorySubject(binding.buttonPackage.text.toString())
        }
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
                            saveStatusAndBackToPreviousScreen()
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