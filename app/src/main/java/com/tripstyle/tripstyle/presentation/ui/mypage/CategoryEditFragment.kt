package com.tripstyle.tripstyle.presentation.ui.mypage

import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.tripstyle.tripstyle.dialog.CategoryDeleteDialog
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentCategoryEditBinding


class CategoryEditFragment :  BaseFragment<FragmentCategoryEditBinding>(R.layout.fragment_category_edit) {

    override fun initStartView() {
        super.initStartView()

        initMenu()
        initCategorySpinner()
        clickButtonEvent()
    }

    override fun initDataBinding() {
        super.initDataBinding()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
    }

    // 메뉴 초기화
    private fun initMenu(){
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_category_detail, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_confrim_button -> {
                        Toast.makeText(context,"수정 되었습니다",Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    //spinner data setup
    private fun initCategorySpinner(){
        val data = resources.getStringArray(R.array.Category)
        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line,data)
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                Log.d("selected item",data[position])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //
            }

        }
    }

    //카테고리 삭제 버튼 이벤트
    private fun clickButtonEvent(){
        binding.btnCategoryDelete.setOnClickListener {
            CategoryDeleteDialog().show(parentFragmentManager,"categoryDelete")
        }
    }

}